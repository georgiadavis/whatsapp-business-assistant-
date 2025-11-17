#!/usr/bin/env python3
"""
Map verified help center URLs to training data based on topic relevance.
The model will learn to associate topics with specific URLs without generating them.
"""

import json
import re

# Load the verified URLs from merged file
VERIFIED_URLS_FILE = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/outputs/help_articles_merged.json"

# Topic to URL mapping based on keywords
TOPIC_URL_MAPPING = {
    "business_profile": [
        "https://faq.whatsapp.com/577829787429875",  # About your business profile
        "https://faq.whatsapp.com/665179381840568",  # How to edit your business profile
    ],
    "catalog": [
        "https://faq.whatsapp.com/2929318000711140",  # How to create and manage a collection
        "http://faq.whatsapp.com/487917009931629",   # Sharing catalog links
    ],
    "quick_replies": [
        "https://faq.whatsapp.com/1623293708131281",  # About Business Features
    ],
    "labels": [
        "https://faq.whatsapp.com/1623293708131281",  # About Business Features
    ],
    "greeting_message": [
        "https://faq.whatsapp.com/1623293708131281",  # About Business Features
    ],
    "away_message": [
        "https://faq.whatsapp.com/1623293708131281",  # About Business Features
    ],
    "statistics": [
        "https://faq.whatsapp.com/1623293708131281",  # About Business Features
    ],
    "broadcast": [
        "https://faq.whatsapp.com/1623293708131281",  # About Business Features
    ],
    "ads": [
        "https://faq.whatsapp.com/512723604104492",   # How to create ads
        "https://faq.whatsapp.com/337473702666585",   # About ads in Status
    ],
    "short_links": [
        "https://faq.whatsapp.com/502291734918768",   # How to create short links
    ],
    "verified": [
        "https://faq.whatsapp.com/2613314448830863",  # Verified channel
        "https://faq.whatsapp.com/7508793019154580",  # Eligibility for Meta Verified
    ],
    "channels": [
        "https://faq.whatsapp.com/794229125227200",   # How to create a Channel
    ],
    "general": [
        "https://faq.whatsapp.com/641572844337957",   # About WhatsApp Business
    ]
}

def determine_topic_from_response(response_content):
    """
    Determine the most relevant topic based on response content.
    Returns the topic key and associated URLs.
    """
    content_lower = response_content.lower()
    
    # Check for specific topics (order matters - most specific first)
    if "catalog" in content_lower or "product" in content_lower:
        return "catalog", TOPIC_URL_MAPPING["catalog"]
    
    if "label" in content_lower:
        return "labels", TOPIC_URL_MAPPING["labels"]
    
    if "quick repl" in content_lower:
        return "quick_replies", TOPIC_URL_MAPPING["quick_replies"]
    
    if "greeting message" in content_lower:
        return "greeting_message", TOPIC_URL_MAPPING["greeting_message"]
    
    if "away message" in content_lower:
        return "away_message", TOPIC_URL_MAPPING["away_message"]
    
    if "statistic" in content_lower:
        return "statistics", TOPIC_URL_MAPPING["statistics"]
    
    if "broadcast" in content_lower:
        return "broadcast", TOPIC_URL_MAPPING["broadcast"]
    
    if "business profile" in content_lower or "profile" in content_lower:
        return "business_profile", TOPIC_URL_MAPPING["business_profile"]
    
    if "ad" in content_lower or "advertis" in content_lower:
        return "ads", TOPIC_URL_MAPPING["ads"]
    
    if "short link" in content_lower or "wa.me" in content_lower:
        return "short_links", TOPIC_URL_MAPPING["short_links"]
    
    if "verified" in content_lower or "green checkmark" in content_lower:
        return "verified", TOPIC_URL_MAPPING["verified"]
    
    if "channel" in content_lower:
        return "channels", TOPIC_URL_MAPPING["channels"]
    
    # Default to general
    return "general", TOPIC_URL_MAPPING["general"]

def add_urls_to_training_data(input_file, output_file):
    """
    Add verified help center URLs to training data as metadata.
    """
    
    print(f"🔗 Adding verified URLs to training data...\n")
    print(f"📂 Input: {input_file}")
    print(f"📂 Output: {output_file}\n")
    
    examples_with_urls = []
    topic_counts = {}
    
    with open(input_file, 'r', encoding='utf-8') as f:
        for line in f:
            example = json.loads(line.strip())
            messages = example.get('messages', [])
            
            # Find assistant message
            for msg in messages:
                if msg['role'] == 'assistant':
                    # Determine topic and get URLs
                    topic, urls = determine_topic_from_response(msg['content'])
                    
                    # Add URL as metadata (not in content!)
                    msg['help_center_urls'] = urls
                    msg['topic'] = topic
                    
                    # Track topics
                    topic_counts[topic] = topic_counts.get(topic, 0) + 1
            
            examples_with_urls.append(example)
    
    # Write output
    with open(output_file, 'w', encoding='utf-8') as f:
        for example in examples_with_urls:
            f.write(json.dumps(example, ensure_ascii=False) + '\n')
    
    print(f"✅ Complete!")
    print(f"\n📊 URL Mapping Statistics:")
    print(f"   Total examples: {len(examples_with_urls)}")
    print(f"\n   By Topic:")
    for topic, count in sorted(topic_counts.items(), key=lambda x: x[1], reverse=True):
        print(f"   • {topic}: {count} examples")
    
    print(f"\n💡 Format:")
    print(f"   Each assistant message now has:")
    print(f"   - 'help_center_urls': array of verified URLs")
    print(f"   - 'topic': category name")
    print(f"\n⚠️  Note: URLs are in metadata, NOT in the response text.")
    print(f"   This prevents the model from hallucinating URLs.")

def main():
    # Process the balanced training data
    input_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/BALANCED_TRAINING_DATA.jsonl"
    output_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/BALANCED_WITH_URLS.jsonl"
    
    add_urls_to_training_data(input_file, output_file)
    
    # Show a sample
    print(f"\n📋 Sample Output:")
    with open(output_file, 'r') as f:
        sample = json.loads(f.readline())
        print(json.dumps(sample, indent=2)[:800])
        print("...\n")

if __name__ == "__main__":
    main()
