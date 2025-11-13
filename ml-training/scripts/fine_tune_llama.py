"""
Fine-tune Llama model for WhatsApp Business Assistant
Uses Llama Stack Client API for fine-tuning
"""

import os
import json
from dotenv import load_dotenv
from llama_stack_client import LlamaStackClient
from pathlib import Path

# Load environment variables from parent directory
load_dotenv(Path(__file__).parent.parent / '.env')

class LlamaFineTuner:
    def __init__(self):
        """Initialize the Llama fine-tuner with API credentials."""
        self.api_key = os.getenv('LLAMA_API_KEY')
        self.base_url = os.getenv('LLAMA_API_BASE_URL', 'https://api.llama-stack.io')

        if not self.api_key or self.api_key == 'your_api_key_here':
            raise ValueError(
                "API key not set! Please:\n"
                "1. Copy .env.example to .env\n"
                "2. Add your LLAMA_API_KEY to .env file"
            )

        # Initialize client
        self.client = LlamaStackClient(
            base_url=self.base_url,
            api_key=self.api_key
        )

        # Load configuration
        self.model_name = os.getenv('MODEL_NAME', 'llama-3.1-8b')
        self.training_file = os.getenv('TRAINING_FILE', '../data/training/training_data_llama_api.jsonl')
        self.output_model_name = os.getenv('OUTPUT_MODEL_NAME', 'whatsapp-business-assistant-v1')

        # Hyperparameters
        self.learning_rate = float(os.getenv('LEARNING_RATE', '2e-5'))
        self.batch_size = int(os.getenv('BATCH_SIZE', '4'))
        self.num_epochs = int(os.getenv('NUM_EPOCHS', '3'))
        self.max_seq_length = int(os.getenv('MAX_SEQ_LENGTH', '2048'))

    def validate_training_data(self):
        """Validate the training data file exists and has correct format."""
        training_path = Path(self.training_file)

        if not training_path.exists():
            raise FileNotFoundError(f"Training file not found: {self.training_file}")

        # Check first few lines for format
        print(f"Validating training data: {self.training_file}")

        with open(training_path, 'r', encoding='utf-8') as f:
            for i, line in enumerate(f):
                if i >= 3:  # Check first 3 lines
                    break
                try:
                    data = json.loads(line)
                    if 'messages' not in data:
                        raise ValueError(f"Line {i+1}: Missing 'messages' field")
                except json.JSONDecodeError as e:
                    raise ValueError(f"Line {i+1}: Invalid JSON - {e}")

        # Count total examples
        with open(training_path, 'r', encoding='utf-8') as f:
            num_examples = sum(1 for _ in f)

        print(f"✓ Validation passed!")
        print(f"✓ Total examples: {num_examples}")
        return num_examples

    def upload_training_data(self):
        """Upload training data to Llama Stack."""
        print(f"\nUploading training data...")

        with open(self.training_file, 'rb') as f:
            response = self.client.files.create(
                file=f,
                purpose='fine-tune'
            )

        file_id = response.id
        print(f"✓ File uploaded successfully!")
        print(f"  File ID: {file_id}")
        return file_id

    def start_fine_tuning(self, file_id):
        """Start the fine-tuning job."""
        print(f"\nStarting fine-tuning job...")
        print(f"  Base model: {self.model_name}")
        print(f"  Output model: {self.output_model_name}")
        print(f"  Learning rate: {self.learning_rate}")
        print(f"  Batch size: {self.batch_size}")
        print(f"  Epochs: {self.num_epochs}")

        response = self.client.fine_tuning.jobs.create(
            training_file=file_id,
            model=self.model_name,
            suffix=self.output_model_name,
            hyperparameters={
                "learning_rate": self.learning_rate,
                "batch_size": self.batch_size,
                "n_epochs": self.num_epochs,
                "max_seq_length": self.max_seq_length
            }
        )

        job_id = response.id
        print(f"✓ Fine-tuning job created!")
        print(f"  Job ID: {job_id}")
        print(f"  Status: {response.status}")
        return job_id

    def check_job_status(self, job_id):
        """Check the status of a fine-tuning job."""
        response = self.client.fine_tuning.jobs.retrieve(job_id)

        print(f"\nJob Status: {response.status}")
        if hasattr(response, 'trained_tokens'):
            print(f"Trained tokens: {response.trained_tokens}")
        if hasattr(response, 'fine_tuned_model'):
            print(f"Fine-tuned model: {response.fine_tuned_model}")

        return response

    def run(self):
        """Run the complete fine-tuning pipeline."""
        print("=" * 60)
        print("WhatsApp Business Assistant - Llama Fine-Tuning")
        print("=" * 60)

        try:
            # Step 1: Validate training data
            num_examples = self.validate_training_data()

            # Step 2: Upload training data
            file_id = self.upload_training_data()

            # Step 3: Start fine-tuning
            job_id = self.start_fine_tuning(file_id)

            # Step 4: Save job info
            job_info = {
                'job_id': job_id,
                'file_id': file_id,
                'model_name': self.model_name,
                'output_model_name': self.output_model_name,
                'num_examples': num_examples,
                'hyperparameters': {
                    'learning_rate': self.learning_rate,
                    'batch_size': self.batch_size,
                    'num_epochs': self.num_epochs,
                    'max_seq_length': self.max_seq_length
                }
            }

            output_dir = Path('outputs')
            output_dir.mkdir(exist_ok=True)

            with open(output_dir / 'fine_tuning_job.json', 'w') as f:
                json.dump(job_info, f, indent=2)

            print("\n" + "=" * 60)
            print("Fine-tuning job started successfully! 🎉")
            print("=" * 60)
            print(f"\nJob details saved to: outputs/fine_tuning_job.json")
            print(f"\nTo check status later, run:")
            print(f"  python check_fine_tuning_status.py {job_id}")
            print("\nThe fine-tuning process will take some time.")
            print("You'll receive an email when it's complete.")

        except Exception as e:
            print(f"\n❌ Error: {str(e)}")
            raise

if __name__ == "__main__":
    # Check if .env file exists in parent directory
    env_path = Path(__file__).parent.parent / '.env'
    if not env_path.exists():
        print("⚠️  .env file not found!")
        print("\nPlease follow these steps:")
        print("1. Copy .env.example to .env in ml-training directory:")
        print("   cd /Users/georgiadavis/Developer/WA\\ Business\\ AI/WA-business/business-assistant/ml-training")
        print("   cp .env.example .env")
        print("2. Edit .env and add your LLAMA_API_KEY")
        print("3. Run this script again")
        exit(1)

    tuner = LlamaFineTuner()
    tuner.run()
