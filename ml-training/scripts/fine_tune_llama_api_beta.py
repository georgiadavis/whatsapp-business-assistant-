"""
Fine-tune Llama model using llama-api.com (Beta Fine-tuning)
For beta users with fine-tuning access
"""

import os
import json
import requests
from dotenv import load_dotenv
from pathlib import Path
import time

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
        self.model_name = os.getenv('MODEL_NAME', 'llama3.1-8b')
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
    
    def upload_training_file(self):
        """Upload training file to llama-api.com."""
        print(f"\nUploading training file...")
        
        training_path = Path(self.training_file)
        
        # Try file upload endpoint
        with open(training_path, 'rb') as f:
            files = {
                'file': (training_path.name, f, 'application/jsonl')
            }
            headers_upload = {
                "Authorization": f"Bearer {self.api_key}"
            }
            
            try:
                response = requests.post(
                    f"{self.base_url}/files",
                    headers=headers_upload,
                    files=files,
                    timeout=300
                )
                
                if response.status_code in [200, 201]:
                    result = response.json()
                    file_id = result.get('id') or result.get('file_id')
                    print(f"✓ File uploaded successfully!")
                    print(f"  File ID: {file_id}")
                    return file_id
                else:
                    print(f"❌ Upload failed ({response.status_code}): {response.text}")
                    return None
                    
            except requests.exceptions.RequestException as e:
                print(f"❌ Upload request failed: {str(e)}")
                return None
    
    def create_fine_tune_job(self, file_id):
        """Create fine-tuning job."""
        print(f"\nCreating fine-tuning job...")
        print(f"  Base model: {self.model_name}")
        print(f"  File ID: {file_id}")
        print(f"  Model suffix: {self.output_model_name}")
        
        payload = {
            "training_file": file_id,
            "model": self.model_name,
            "suffix": self.output_model_name,
            "hyperparameters": {
                "n_epochs": int(os.getenv('NUM_EPOCHS', '3')),
                "batch_size": int(os.getenv('BATCH_SIZE', '4')),
                "learning_rate": float(os.getenv('LEARNING_RATE', '2e-5'))
            }
        }
        
        try:
            response = requests.post(
                f"{self.base_url}/fine-tuning/jobs",
                headers=self.headers,
                json=payload,
                timeout=60
            )
            
            if response.status_code in [200, 201]:
                result = response.json()
                job_id = result.get('id') or result.get('job_id')
                print(f"✓ Fine-tuning job created!")
                print(f"  Job ID: {job_id}")
                print(f"  Status: {result.get('status', 'pending')}")
                
                # Save job info
                self.save_job_info(job_id, file_id, result)
                
                return job_id
            else:
                print(f"❌ Job creation failed ({response.status_code}): {response.text}")
                return None
                
        except requests.exceptions.RequestException as e:
            print(f"❌ Request failed: {str(e)}")
            return None
    
    def save_job_info(self, job_id, file_id, response_data):
        """Save job information to file."""
        output_dir = Path(__file__).parent.parent / 'outputs'
        output_dir.mkdir(exist_ok=True)
        
        job_info = {
            'job_id': job_id,
            'file_id': file_id,
            'model': self.model_name,
            'suffix': self.output_model_name,
            'response': response_data,
            'timestamp': time.strftime('%Y-%m-%d %H:%M:%S')
        }
        
        with open(output_dir / 'fine_tuning_job.json', 'w') as f:
            json.dump(job_info, f, indent=2)
        
        print(f"\n✓ Job info saved to: outputs/fine_tuning_job.json")
    
    def check_job_status(self, job_id):
        """Check fine-tuning job status."""
        try:
            response = requests.get(
                f"{self.base_url}/fine-tuning/jobs/{job_id}",
                headers=self.headers,
                timeout=30
            )
            
            if response.status_code == 200:
                return response.json()
            else:
                print(f"Status check failed: {response.text}")
                return None
        except Exception as e:
            print(f"Error checking status: {e}")
            return None
    
    def run(self):
        """Run the fine-tuning process."""
        print("=" * 70)
        print("WhatsApp Business Assistant - llama-api.com Fine-Tuning")
        print("=" * 70)
        
        try:
            # Step 1: Validate training data
            num_examples = self.validate_training_data()
            
            # Step 2: Upload training file
            file_id = self.upload_training_file()
            
            if not file_id:
                print("\n❌ File upload failed. Please check:")
                print("1. Your API key has fine-tuning access")
                print("2. The file format is correct")
                print("3. Your account has available quota")
                return
            
            # Step 3: Create fine-tuning job
            job_id = self.create_fine_tune_job(file_id)
            
            if not job_id:
                print("\n❌ Job creation failed. Please check:")
                print("1. Your API key has fine-tuning access")
                print("2. The model name is correct")
                print("3. Your account has available quota")
                return
            
            print("\n" + "=" * 70)
            print("🎉 Fine-tuning job started successfully!")
            print("=" * 70)
            print(f"\nJob ID: {job_id}")
            print(f"Training examples: {num_examples}")
            print(f"\nTo check status, run:")
            print(f"  cd scripts && source ../venv/bin/activate")
            print(f"  python check_llama_api_status.py {job_id}")
            print("\nThe fine-tuning process will take 2-6 hours.")
            print("You'll be notified when it's complete.")
            
        except Exception as e:
            print(f"\n❌ Error: {str(e)}")
            import traceback
            traceback.print_exc()

if __name__ == "__main__":
    env_path = Path(__file__).parent.parent / '.env'
    if not env_path.exists():
        print("⚠️  .env file not found!")
        exit(1)
    
    tuner = LlamaAPIFineTuner()
    tuner.run()
