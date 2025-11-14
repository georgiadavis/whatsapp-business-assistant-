"""
Test your fine-tuned WhatsApp Business Assistant model
"""

import os
from dotenv import load_dotenv
from pathlib import Path

# Load environment
load_dotenv(Path(__file__).parent.parent / '.env')

# System prompt - THIS IS CRITICAL for the model to know its role
SYSTEM_PROMPT = """You are a WhatsApp Business Assistant built directly into the WhatsApp Business app. You help business owners use WhatsApp Business features to grow their business.

CRITICAL: ALL your responses must be contextual to WhatsApp Business features and capabilities. Even for general business questions, frame your answers around what users can do within WhatsApp Business.

Your role is to:
1. Help business owners understand and use WhatsApp Business features (catalog, labels, quick replies, greeting messages, away messages, business profile, statistics)
2. Assist with creating and managing advertising campaigns (Native Ads, Click-to-WhatsApp Ads/CTWA)
3. Guide users in creating effective broadcast messages to reach multiple customers
4. Provide guidance on best practices for customer communication on WhatsApp
5. Classify customer intents and suggest appropriate WhatsApp Business responses
6. Offer business development advice specifically using WhatsApp Business features
7. Answer questions about WhatsApp Business policies and capabilities

How to answer questions:
- "How do I get more customers?" → "Here's how to get more customers on WhatsApp Business: 1) Use Click-to-WhatsApp ads to drive traffic, 2) Share your WhatsApp Business link on social media, 3) Create an engaging catalog..."
- "How do I increase sales?" → "Here's how to increase sales using WhatsApp Business: 1) Set up a product catalog, 2) Use broadcast lists for promotions, 3) Enable quick replies for faster customer service..."
- "How do I organize my work?" → "Here's how to organize your business on WhatsApp: 1) Use labels to categorize customers, 2) Set up away messages for after hours..."

Proactive WhatsApp Business Suggestions:
- When users mention wanting more customers → Suggest Click-to-WhatsApp ads, sharing WA link, catalog features
- When users want to announce sales/promotions → Recommend broadcast lists and status updates
- When users mention being busy → Suggest quick replies, greeting messages, away messages
- When users want to showcase products → Recommend creating a catalog
- When users need to organize → Suggest using labels and business profile

Always be professional, helpful, and concise. Provide step-by-step instructions when explaining features. Keep every answer focused on WhatsApp Business-specific functionality."""

def test_with_llama_api():
    """Test the fine-tuned model using llama-api.com"""
    import requests

    api_key = os.getenv('LLAMA_API_KEY')

    # Your fine-tuned model ID (you'll get this after fine-tuning completes)
    # Replace this with your actual fine-tuned model ID
    model_id = os.getenv('FINE_TUNED_MODEL_ID', 'your-fine-tuned-model-id')

    # Test queries
    test_queries = [
        "How do I create a product catalog?",
        "What are labels and how can I use them?",
        "A customer is asking about my business hours, what should I do?",
        "How can I automate responses to common questions?",
        "How do I track my message statistics?"
    ]

    print("=" * 70)
    print("Testing Fine-Tuned WhatsApp Business Assistant")
    print("=" * 70)
    print(f"\nModel: {model_id}")
    print(f"Using SYSTEM PROMPT to establish model identity\n")

    for i, query in enumerate(test_queries, 1):
        print(f"\n{'='*70}")
        print(f"Test {i}: {query}")
        print('='*70)

        # Prepare the request with SYSTEM PROMPT
        messages = [
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": query}
        ]

        try:
            response = requests.post(
                "https://api.llama-api.com/chat/completions",
                headers={
                    "Authorization": f"Bearer {api_key}",
                    "Content-Type": "application/json"
                },
                json={
                    "model": model_id,
                    "messages": messages,
                    "max_tokens": 500,
                    "temperature": 0.7
                },
                timeout=30
            )

            if response.status_code == 200:
                result = response.json()
                assistant_message = result['choices'][0]['message']['content']
                print(f"\n🤖 Assistant: {assistant_message}")
            else:
                print(f"❌ Error: {response.status_code} - {response.text}")

        except Exception as e:
            print(f"❌ Request failed: {e}")

        print()

def test_with_llama_stack():
    """Test using llama-stack-client (if you're using Llama Stack)"""
    from llama_stack_client import LlamaStackClient

    api_key = os.getenv('LLAMA_API_KEY')
    base_url = os.getenv('LLAMA_API_BASE_URL', 'https://api.llama-stack.io')
    model_id = os.getenv('FINE_TUNED_MODEL_ID', 'your-fine-tuned-model-id')

    client = LlamaStackClient(base_url=base_url, api_key=api_key)

    test_query = "How do I set up quick replies?"

    print("=" * 70)
    print("Testing with Llama Stack Client")
    print("=" * 70)
    print(f"\nQuery: {test_query}\n")

    try:
        response = client.inference.chat_completion(
            model=model_id,
            messages=[
                {"role": "system", "content": SYSTEM_PROMPT},
                {"role": "user", "content": test_query}
            ],
            max_tokens=500
        )

        print(f"🤖 Assistant: {response.choices[0].message.content}")

    except Exception as e:
        print(f"❌ Error: {e}")

if __name__ == "__main__":
    print("\n" + "="*70)
    print("IMPORTANT: Update your fine-tuned model ID")
    print("="*70)
    print("\nBefore running this test:")
    print("1. Get your fine-tuned model ID from llama-api.com")
    print("2. Add it to your .env file:")
    print("   FINE_TUNED_MODEL_ID=your-actual-model-id")
    print("\nOr pass it when testing individual functions.\n")

    # Check if model ID is set
    model_id = os.getenv('FINE_TUNED_MODEL_ID')
    if not model_id or model_id == 'your-fine-tuned-model-id':
        print("⚠️  Model ID not set! Please update .env file first.")
        print("\nExample .env entry:")
        print("FINE_TUNED_MODEL_ID=ft:llama3.1-8b:whatsapp-assistant:abc123")
    else:
        # Run tests
        print(f"\n✓ Model ID found: {model_id}")
        print("\nStarting tests...\n")
        test_with_llama_api()
