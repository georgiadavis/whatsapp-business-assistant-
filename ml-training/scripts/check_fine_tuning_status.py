"""
Check the status of a fine-tuning job
"""

import os
import sys
import json
from dotenv import load_dotenv
from llama_stack_client import LlamaStackClient
from pathlib import Path

load_dotenv()

def check_status(job_id=None):
    """Check fine-tuning job status."""

    # Get API key
    api_key = os.getenv('LLAMA_API_KEY')
    base_url = os.getenv('LLAMA_API_BASE_URL', 'https://api.llama-stack.io')

    if not api_key or api_key == 'your_api_key_here':
        print("❌ API key not set in .env file")
        return

    # Initialize client
    client = LlamaStackClient(base_url=base_url, api_key=api_key)

    # Get job ID
    if not job_id:
        # Try to load from saved file
        job_file = Path('outputs/fine_tuning_job.json')
        if job_file.exists():
            with open(job_file, 'r') as f:
                job_info = json.load(f)
                job_id = job_info['job_id']
        else:
            print("❌ No job ID provided and no saved job file found")
            print("Usage: python check_fine_tuning_status.py <job_id>")
            return

    # Check status
    print(f"Checking status for job: {job_id}\n")

    try:
        response = client.fine_tuning.jobs.retrieve(job_id)

        print("=" * 60)
        print(f"Job ID: {job_id}")
        print(f"Status: {response.status}")
        print("=" * 60)

        if hasattr(response, 'created_at'):
            print(f"Created: {response.created_at}")
        if hasattr(response, 'finished_at') and response.finished_at:
            print(f"Finished: {response.finished_at}")
        if hasattr(response, 'model'):
            print(f"Base Model: {response.model}")
        if hasattr(response, 'fine_tuned_model') and response.fine_tuned_model:
            print(f"Fine-tuned Model: {response.fine_tuned_model}")
        if hasattr(response, 'trained_tokens'):
            print(f"Trained Tokens: {response.trained_tokens}")
        if hasattr(response, 'training_file'):
            print(f"Training File: {response.training_file}")

        # Status-specific messages
        if response.status == 'succeeded':
            print("\n🎉 Fine-tuning completed successfully!")
            print(f"\nYour fine-tuned model is ready: {response.fine_tuned_model}")
            print("\nYou can now use this model for inference.")
        elif response.status == 'running':
            print("\n⏳ Fine-tuning is in progress...")
            print("Check back later for updates.")
        elif response.status == 'pending':
            print("\n⏳ Fine-tuning job is queued...")
            print("It will start processing soon.")
        elif response.status == 'failed':
            print("\n❌ Fine-tuning job failed!")
            if hasattr(response, 'error'):
                print(f"Error: {response.error}")

    except Exception as e:
        print(f"❌ Error checking status: {str(e)}")

if __name__ == "__main__":
    job_id = sys.argv[1] if len(sys.argv) > 1 else None
    check_status(job_id)
