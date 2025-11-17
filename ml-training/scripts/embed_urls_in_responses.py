#!/usr/bin/env python3
"""
Create training data with help center URLs embedded in responses.
Format: Include URLs at the end with clear markers to reduce hallucination.
"""

import json

# Verified URL mapping
TOPIC_URL_MAPPING = {
    "business_profile": {
        "urls": [
            "https://faq.whatsapp.com/577829787429875",  # About business profile
            "https://faq.whatsapp.com/665179381840568",  # How to edit
        ],
        "title": "Business Profile"
    },
    "catalog": {
        "urls": [
            "https://faq.whatsapp.com/2929318000711140",  # Collections
            "http://faq.whatsapp.com/487917009931629",   # Sharing links
        ],
        "title": "Catalog"
    },
    "quick_replies": {
        "urls": ["https://faq.whatsapp.com/1623293708131281"],
        "title": "Quick Replies"
    },
    "labels": {
        "urls": ["https://faq.whatsapp.com/1623293708131281"],
        "title": "Labels"
    },
    "greeting_message": {
        "urls": ["https://faq.whatsapp.com/1623293708131281"],
        "title": "Greeting Messages"
    },
    "away_message": {
        "urls": ["https://faq.whatsapp.com/1623293708131281"],
        "title": "Away Messages"
    },
    "statistics": {
        "urls": ["https://faq.whatsapp.com/1623293708131281"],
        "title": "Statistics"
    },
    "broadcast": {
        "urls": ["https://faq.whatsapp.com/1623293708131281"],
        "title": "Broadcast Lists"
    },
    "ads": {
        "urls": [
            "https://faq.whatsapp.com/512723604104492",   # Create ads
            "https://faq.whatsapp.com/337473702666585",   # Ads in Status
        ],
        "title": "Advertising"
    },
    "short_links": {
        "urls": ["https://faq.whatsapp.com/502291734918768"],
        "title": "Short Links"
    },
    "verified": {
        "urls": [
            "https://faq.whatsapp.com/2613314448830863",  # Verified channel
            "https://faq.whatsapp.com/7508793019154580",  # Meta Verified
        ],
        "title": "Verification"
    },
    "channels": {
        "urls": ["https://faq.whatsapp.com/794229125227200"],
        "title": "Channels"
    },
    "general": {
        "urls": ["https://faq.whatsapp.com/641572844337957"],
        "title": "WhatsApp Business"
    }
}

def determine_topic(content):
    """Determine topic from response content."""
    content_lower = content.lower()

    if "catalog" in content_lower or "product" in content_lower:
        return "catalog"
    elif "label" in content_lower:
        return "labels"
    elif "quick repl" in content_lower:
        return "quick_replies"
    elif "greeting message" in content_lower:
        return "greeting_message"
    elif "away message" in content_lower:
        return "away_message"
    elif "statistic" in content_lower:
        return "statistics"
    elif "broadcast" in content_lower:
        return "broadcast"
    elif "business profile" in content_lower or "profile" in content_lower:
        return "business_profile"
    elif "ad" in content_lower or "advertis" in content_lower:
        return "ads"
    elif "short link" in content_lower or "wa.me" in content_lower:
        return "short_links"
    elif "verified" in content_lower or "green checkmark" in content_lower:
        return "verified"
    elif "channel" in content_lower:
        return "channels"
    else:
        return "general"

def add_urls_to_response(content, topic):
    """
    Add help center URLs to the end of response in a structured format.
    Format reduces hallucination by:
    1. Clear separator
    2. "Learn more:" prefix
    3. Bullet points with URLs only (no made-up descriptions)
    """

    topic_info = TOPIC_URL_MAPPING.get(topic, TOPIC_URL_MAPPING["general"])
    urls = topic_info["urls"]

    # Add URLs at the end with clear formatting
    enhanced_content = content.strip()

    # Only add URLs if not already present
    if "faq.whatsapp.com" not in enhanced_content:
        enhanced_content += "\n\n📚 Learn more:\n"
        for url in urls:
            enhanced_content += f"• {url}\n"

    return enhanced_content.strip()

def create_training_with_embedded_urls(input_file, output_file):
    """Create training data with URLs embedded in responses."""

    print(f"🔗 Creating training data with embedded URLs...\n")

    examples_count = 0
    business_with_urls = 0

    with open(input_file, 'r', encoding='utf-8') as f_in:
        with open(output_file, 'w', encoding='utf-8') as f_out:
            for line in f_in:
                example = json.loads(line.strip())
                messages = example.get('messages', [])

                # Process each message
                for msg in messages:
                    if msg['role'] == 'assistant':
                        content = msg['content']

                        # Only add URLs to business-related responses
                        # (those that mention WhatsApp Business features)
                        if "WhatsApp Business" in content:
                            topic = determine_topic(content)
                            msg['content'] = add_urls_to_response(content, topic)
                            business_with_urls += 1

                # Write clean example (only role and content)
                clean_example = {
                    "messages": [
                        {"role": m["role"], "content": m["content"]}
                        for m in messages
                    ]
                }

                f_out.write(json.dumps(clean_example, ensure_ascii=False) + '\n')
                examples_count += 1

    print(f"✅ Complete!")
    print(f"\n📊 Statistics:")
    print(f"   Total examples: {examples_count}")
    print(f"   Business responses with URLs: {business_with_urls}")
    print(f"\n💡 URL Format:")
    print(f"   URLs are embedded with:")
    print(f"   - Clear separator (empty line)")
    print(f"   - '📚 Learn more:' header")
    print(f"   - Bullet points with URLs only")
    print(f"   - NO made-up descriptions")
    print(f"\n📁 Output: {output_file}")

def main():
    input_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/BALANCED_TRAINING_DATA.jsonl"
    output_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/BALANCED_WITH_EMBEDDED_URLS.jsonl"

    create_training_with_embedded_urls(input_file, output_file)

    # Show sample
    print(f"\n📋 Sample Output:")
    with open(output_file, 'r') as f:
        sample = json.loads(f.readline())
        assistant_msg = next(m for m in sample['messages'] if m['role'] == 'assistant')
        print("\n" + "="*80)
        print(assistant_msg['content'])
        print("="*80)

if __name__ == "__main__":
    main()
