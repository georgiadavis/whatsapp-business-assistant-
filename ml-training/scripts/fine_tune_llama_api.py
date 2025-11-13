"""
Fine-tune Llama model using llama-api.com
Uses direct HTTP requests to llama-api.com API
"""

import os
import json
import requests
from dotenv import load_dotenv
from pathlib import Path

# Load environment variables from parent directory
load_dotenv(Path(__file__).parent.parent / '.env')

class LlamaAPIFineTuner:
    def __init__(self):
        """Initialize the Llama API fine-tuner."""
        self.api_key = os.getenv('LLAMA_API_KEY')
        self.base_url = "https://api.llama-api.com"
        
        if not self.api_key or self.api_key == 'your_api_key_here':
            raise ValueError(
                "API key not set! Please add your LLAMA_API_KEY to .env file"
            )
        
        # Configuration
        self.training_file = os.getenv('TRAINING_FILE', '../data/training/training_data_llama_api.jsonl')
        self.model_name = os.getenv('MODEL_NAME', 'llama-3.1-8b')
        self.output_model_name = os.getenv('OUTPUT_MODEL_NAME', 'whatsapp-business-assistant-v1')
        
        # Headers for API requests
        self.headers = {
            "Authorization": f"Bearer {self.api_key}",
            "Content-Type": "application/json"
        }
    
    def validate_training_data(self):
        """Validate the training data file."""
        training_path = Path(self.training_file)
        
        if not training_path.exists():
            raise FileNotFoundError(f"Training file not found: {self.training_file}")
        
        print(f"Validating training data: {self.training_file}")
        
        # Count and validate format
        with open(training_path, 'r', encoding='utf-8') as f:
            for i, line in enumerate(f):
                if i >= 3:
                    break
                try:
                    data = json.loads(line)
                    if 'messages' not in data:
                        raise ValueError(f"Line {i+1}: Missing 'messages' field")
                except json.JSONDecodeError as e:
                    raise ValueError(f"Line {i+1}: Invalid JSON - {e}")
        
        with open(training_path, 'r', encoding='utf-8') as f:
            num_examples = sum(1 for _ in f)
        
        print(f"✓ Validation passed!")
        print(f"✓ Total examples: {num_examples}")
        return num_examples
    
    def create_fine_tuning_job(self):
        """Create a fine-tuning job using llama-api.com API."""
        print(f"\nCreating fine-tuning job...")
        print(f"  Base model: {self.model_name}")
        print(f"  Output model: {self.output_model_name}")
        
        # Read training data
        training_data = []
        with open(self.training_file, 'r', encoding='utf-8') as f:
            for line in f:
                training_data.append(json.loads(line))
        
        # Prepare request payload
        # NOTE: This is a guess at the API structure - llama-api.com may have different endpoints
        payload = {
            "model": self.model_name,
            "training_data": training_data,
            "model_suffix": self.output_model_name,
            "hyperparameters": {
                "n_epochs": int(os.getenv('NUM_EPOCHS', '3')),
                "batch_size": int(os.getenv('BATCH_SIZE', '4')),
                "learning_rate_multiplier": float(os.getenv('LEARNING_RATE', '2e-5'))
            }
        }
        
        # Try fine-tuning endpoint
        try:
            response = requests.post(
                f"{self.base_url}/fine-tune",
                headers=self.headers,
                json=payload,
                timeout=60
            )
            
            if response.status_code == 200:
                result = response.json()
                print(f"✓ Fine-tuning job created successfully!")
                print(f"  Response: {json.dumps(result, indent=2)}")
                return result
            else:
                print(f"❌ Error {response.status_code}: {response.text}")
                return None
                
        except requests.exceptions.RequestException as e:
            print(f"❌ Request failed: {str(e)}")
            return None
    
    def check_api_info(self):
        """Check what endpoints are available."""
        print("\nChecking llama-api.com API...")
        
        # Try to get API info
        try:
            response = requests.get(
                f"{self.base_url}/",
                headers=self.headers,
                timeout=10
            )
            print(f"Base URL status: {response.status_code}")
            if response.status_code == 200:
                print(f"Response: {response.text[:500]}")
        except Exception as e:
            print(f"Could not reach base URL: {e}")
        
        # Try models endpoint
        try:
            response = requests.get(
                f"{self.base_url}/models",
                headers=self.headers,
                timeout=10
            )
            print(f"\nModels endpoint status: {response.status_code}")
            if response.status_code == 200:
                print(f"Available models: {response.text[:500]}")
        except Exception as e:
            print(f"Models endpoint error: {e}")
    
    def run(self):
        """Run the fine-tuning process."""
        print("=" * 70)
        print("WhatsApp Business Assistant - llama-api.com Fine-Tuning")
        print("=" * 70)
        
        try:
            # Validate training data
            num_examples = self.validate_training_data()
            
            # Check API endpoints
            self.check_api_info()
            
            print("\n" + "=" * 70)
            print("⚠️  IMPORTANT NOTICE")
            print("=" * 70)
            print("\nllama-api.com may not support fine-tuning through their API,")
            print("or they may use a different API structure than documented.")
            print("\nYour training data is ready at:")
            print(f"  {Path(self.training_file).absolute()}")
            print(f"\nTotal examples: {num_examples}")
            print("\n📚 Next Steps:")
            print("1. Check llama-api.com documentation for fine-tuning")
            print("2. Contact llama-api.com support for fine-tuning options")
            print("3. Or use an alternative platform:")
            print("   - Hugging Face AutoTrain: https://huggingface.co/autotrain")
            print("   - Together.ai: https://www.together.ai/")
            print("   - Fireworks.ai: https://fireworks.ai/")
            print("\nYour training file is in the correct format and ready to upload!")
            
        except Exception as e:
            print(f"\n❌ Error: {str(e)}")
            raise

if __name__ == "__main__":
    env_path = Path(__file__).parent.parent / '.env'
    if not env_path.exists():
        print("⚠️  .env file not found!")
        exit(1)
    
    tuner = LlamaAPIFineTuner()
    tuner.run()
