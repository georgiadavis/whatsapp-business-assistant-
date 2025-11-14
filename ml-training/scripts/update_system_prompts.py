"""
Update system prompts in all training JSONL files
This ensures the model learns its identity as a WhatsApp Business Assistant
"""

import json
from pathlib import Path

# The improved system prompt
WHATSAPP_BUSINESS_SYSTEM_PROMPT = """You are a WhatsApp Business Assistant built directly into the WhatsApp Business app. You help business owners use WhatsApp Business features to grow their business.

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

def update_system_prompt_in_file(input_file, output_file):
    """Update system prompts in a JSONL file."""

    input_path = Path(input_file)
    output_path = Path(output_file)

    if not input_path.exists():
        print(f"❌ File not found: {input_file}")
        return 0

    print(f"\nProcessing: {input_file}")

    count = 0
    updated = 0

    with open(input_path, 'r', encoding='utf-8') as infile, \
         open(output_path, 'w', encoding='utf-8') as outfile:

        for line in infile:
            data = json.loads(line)

            # Update the system message
            if 'messages' in data and len(data['messages']) > 0:
                if data['messages'][0].get('role') == 'system':
                    # Replace system prompt
                    data['messages'][0]['content'] = WHATSAPP_BUSINESS_SYSTEM_PROMPT
                    updated += 1

            # Write updated data
            outfile.write(json.dumps(data, ensure_ascii=False) + '\n')
            count += 1

    print(f"  ✓ Processed {count} examples")
    print(f"  ✓ Updated {updated} system prompts")

    return count

def main():
    """Update all training files with improved system prompt."""

    print("=" * 70)
    print("Updating System Prompts in Training Data")
    print("=" * 70)
    print("\nNew System Prompt:")
    print("-" * 70)
    print(WHATSAPP_BUSINESS_SYSTEM_PROMPT)
    print("-" * 70)

    # Files to update
    files_to_update = [
        (
            '../data/training/intent_classification_reasoning.jsonl',
            '../data/training/intent_classification_reasoning_updated.jsonl'
        ),
        (
            '../data/training/help_center_articles.jsonl',
            '../data/training/help_center_articles_updated.jsonl'
        )
    ]

    total_examples = 0

    for input_file, output_file in files_to_update:
        count = update_system_prompt_in_file(input_file, output_file)
        total_examples += count

    print("\n" + "=" * 70)
    print("✓ Update Complete!")
    print("=" * 70)
    print(f"\nTotal examples updated: {total_examples}")
    print("\nUpdated files:")
    print("  - intent_classification_reasoning_updated.jsonl")
    print("  - help_center_articles_updated.jsonl")
    print("\n📝 Next Steps:")
    print("1. Review the updated files")
    print("2. Merge them into a complete file:")
    print("   python merge_updated_training.py")
    print("3. Re-upload to llama-api.com for fine-tuning")

if __name__ == "__main__":
    main()
