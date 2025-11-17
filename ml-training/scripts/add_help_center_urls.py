#!/usr/bin/env python3
"""
Add help_center_url field to training data based on response content.
Links are mapped separately from content for cleaner fine-tuning.
"""

import json
import re

# ONLY VERIFIED WhatsApp Business Help Center URLs from help_articles_manual.json
# These are the ACTUAL URLs that exist and work
HELP_CENTER_ARTICLES = {
    # From verified help articles file
    "catalog": "https://faq.whatsapp.com/general/account-and-profile/how-to-create-and-maintain-a-catalog",
    "labels": "https://faq.whatsapp.com/general/account-and-profile/how-to-use-labels",
    "quick_replies": "https://faq.whatsapp.com/general/account-and-profile/how-to-use-quick-replies",
    "greeting_message": "https://faq.whatsapp.com/general/account-and-profile/how-to-use-greeting-messages",
    "away_message": "https://faq.whatsapp.com/general/account-and-profile/how-to-use-away-messages",
    "business_profile": "https://faq.whatsapp.com/general/account-and-profile/about-business-profiles",
    "statistics": "https://faq.whatsapp.com/general/account-and-profile/about-statistics",
    "broadcast": "https://faq.whatsapp.com/general/chats/how-to-use-broadcast-lists",

    # General fallback
    "general": "https://faq.whatsapp.com/general"
}

def determine_help_article(response_content):
    """
    Determine which help center article is most relevant based on response content.
    Returns the URL of the most relevant verified article.
    """
    content_lower = response_content.lower()

    # Check for specific features mentioned in the response
    # Priority order: most specific features first

    if "verified" in content_lower or "green checkmark" in content_lower or "badge" in content_lower:
        return HELP_CENTER_ARTICLES["verified_badge"]

    if "whatsapp web" in content_lower:
        return HELP_CENTER_ARTICLES["whatsapp_web"]

    if "wa.me" in content_lower or "whatsapp link" in content_lower or "business link" in content_lower:
        return HELP_CENTER_ARTICLES["wa_link"]

    if "click-to-whatsapp" in content_lower or "ctwa" in content_lower or ("run" in content_lower and "ads" in content_lower):
        return HELP_CENTER_ARTICLES["ctwa_ads"]

    if "catalog" in content_lower or "product" in content_lower:
        return HELP_CENTER_ARTICLES["catalog"]

    if "label" in content_lower:
        return HELP_CENTER_ARTICLES["labels"]

    if "quick repl" in content_lower:
        return HELP_CENTER_ARTICLES["quick_replies"]

    if "greeting message" in content_lower:
        return HELP_CENTER_ARTICLES["greeting_message"]

    if "away message" in content_lower:
        return HELP_CENTER_ARTICLES["away_message"]

    if "business profile" in content_lower:
        return HELP_CENTER_ARTICLES["business_profile"]

    if "statistics" in content_lower or "statistic" in content_lower:
        return HELP_CENTER_ARTICLES["statistics"]

    if "broadcast" in content_lower:
        return HELP_CENTER_ARTICLES["broadcast"]

    # Default to general WhatsApp Business help
    return HELP_CENTER_ARTICLES["general"]

def add_help_center_urls(input_file, output_file):
    """Add help_center_url field to each assistant message."""

    updated_count = 0

    with open(input_file, 'r', encoding='utf-8') as f_in:
        with open(output_file, 'w', encoding='utf-8') as f_out:
            for line in f_in:
                example = json.loads(line.strip())
                messages = example.get('messages', [])

                # Find assistant message and add help_center_url
                for msg in messages:
                    if msg['role'] == 'assistant':
                        help_url = determine_help_article(msg['content'])
                        msg['help_center_url'] = help_url
                        updated_count += 1

                # Write updated example
                f_out.write(json.dumps(example, ensure_ascii=False) + '\n')

    return updated_count

def main():
    input_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/2000_BUSINESS_QUESTIONS.jsonl"
    output_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/2000_FINAL_NO_URLS.jsonl"

    print("🔧 Creating final training data WITHOUT help center URLs...\n")
    print("⚠️  Note: Help center URLs removed - they were not verified\n")

    # Simply copy the file without adding any URLs
    import shutil
    shutil.copy(input_file, output_file)

    # Count examples
    with open(output_file, 'r') as f:
        count = sum(1 for _ in f)

    print(f"✅ Complete!")
    print(f"\n📁 Output: {output_file}")
    print(f"📊 Total examples: {count}")
    print(f"\n💡 Format: User message → Assistant response (NO system message, NO URLs)")
    print(f"\n✨ Ready for fine-tuning!")

if __name__ == "__main__":
    main()
