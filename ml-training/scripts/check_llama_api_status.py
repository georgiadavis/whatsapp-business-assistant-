"""
Check fine-tuning job status on llama-api.com
"""

import os
import sys
import json
import requests
from dotenv import load_dotenv
from pathlib import Path

# Load environment variables
load_dotenv(Path(__file__).parent.parent / '.env')

def check_status(job_id=None):
    """Check fine-tuning job status on llama-api.com."""

    api_key = os.getenv('LLAMA_API_KEY')
    base_url = "https://api.llama-api.com"

    if not api_key or api_key == 'your_api_key_here':
        print("❌ API key not set in .env file")
        return

    # Get job ID
    if not job_id:
        job_file = Path(__file__).parent.parent / 'outputs' / 'fine_tuning_job.json'
        if job_file.exists():
            with open(job_file, 'r') as f:
                job_info = json.load(f)
                job_id = job_info.get('job_id')
        else:
            print("❌ No job ID provided and no saved job file found")
            print("Usage: python check_llama_api_status.py <job_id>")
            return

    print(f"Checking status for job: {job_id}\n")

    try:
        headers = {"Authorization": f"Bearer {api_key}"}

        response = requests.get(
            f"{base_url}/fine-tuning/jobs/{job_id}",
            headers=headers,
            timeout=30
        )

        if response.status_code == 200:
            data = response.json()

            print("=" * 70)
            print(f"Job ID: {job_id}")
            print(f"Status: {data.get('status', 'unknown')}")
            print("=" * 70)

            if 'created_at' in data:
                print(f"Created: {data['created_at']}")
            if 'model' in data:
                print(f"Base Model: {data['model']}")
            if 'fine_tuned_model' in data and data['fine_tuned_model']:
                print(f"Fine-tuned Model: {data['fine_tuned_model']}")
            if 'training_file' in data:
                print(f"Training File: {data['training_file']}")

            # Show progress if available
            if 'progress' in data:
                print(f"Progress: {data['progress']}%")
            if 'trained_tokens' in data:
                print(f"Trained Tokens: {data['trained_tokens']}")

            # Status messages
            status = data.get('status', '')
            if status == 'succeeded':
                print("\n🎉 Fine-tuning completed successfully!")
                print(f"\nYour fine-tuned model: {data.get('fine_tuned_model')}")
                print("\nYou can now use this model for inference!")
            elif status == 'running' or status == 'in_progress':
                print("\n⏳ Fine-tuning is in progress...")
                print("Check back later for updates.")
            elif status == 'pending' or status == 'queued':
                print("\n⏳ Fine-tuning job is queued...")
                print("It will start processing soon.")
            elif status == 'failed':
                print("\n❌ Fine-tuning job failed!")
                if 'error' in data:
                    print(f"Error: {data['error']}")

            # Show full response for debugging
            print("\nFull Response:")
            print(json.dumps(data, indent=2))

        else:
            print(f"❌ Status check failed ({response.status_code})")
            print(f"Response: {response.text}")

    except Exception as e:
        print(f"❌ Error checking status: {str(e)}")

if __name__ == "__main__":
    job_id = sys.argv[1] if len(sys.argv) > 1 else None
    check_status(job_id)
