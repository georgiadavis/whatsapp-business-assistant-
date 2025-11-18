#!/usr/bin/env python3
"""
Create FOCUSED training data:
- ONLY business questions → WhatsApp Business features
- NO generic greetings (let base model handle those)

Decision tree:
- Generic question → Base model (don't train on this)
- Business question → WhatsApp Business features (train on this)
"""

import json

def create_whatsapp_business_training():
    """
    ONLY examples where we want to override base behavior.
    All examples are business-related questions that should redirect to WA features.
    """

    examples = [
        # === SPECIFIC WhatsApp Business feature questions ===
        {
            "user": "How do I set up a catalog?",
            "assistant": "Here's how to create a catalog in WhatsApp Business:\n\n1. Open WhatsApp Business\n2. Go to Settings → Business Tools → Catalog\n3. Tap + to add products\n4. Add photos, names, prices, and descriptions\n5. Save each product\n\nYour catalog appears on your business profile and you can share products in chats.\n\n📚 Learn more: https://faq.whatsapp.com/2929318000711140"
        },
        {
            "user": "How do I use labels?",
            "assistant": "Labels help you organize conversations in WhatsApp Business:\n\n1. Open a chat\n2. Tap More options → Labels\n3. Create labels (e.g., \"New Customer\", \"Paid\", \"Pending\")\n4. Apply to conversations\n5. Filter your chat list by label\n\nThis helps you stay organized and prioritize customers.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },
        {
            "user": "What are quick replies?",
            "assistant": "Quick replies let you save frequent messages:\n\n1. Go to Business Tools → Quick Replies\n2. Tap + to create\n3. Add a shortcut (like /hours)\n4. Write your message\n5. Save it\n\nType / in any chat to see and insert your quick replies.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },
        {
            "user": "How do I create a business profile?",
            "assistant": "To set up your WhatsApp Business profile:\n\n1. Go to Settings → Business Tools → Business Profile\n2. Add business name and category\n3. Add description, address, hours, website\n4. Save your profile\n\nA complete profile builds trust with customers.\n\n📚 Learn more: https://faq.whatsapp.com/577829787429875"
        },
        {
            "user": "How do I set up greeting messages?",
            "assistant": "Greeting messages automatically welcome customers:\n\n1. Go to Business Tools → Greeting Message\n2. Enable it\n3. Write your welcome message\n4. Set when to send (new customers or after 14 days inactive)\n5. Save\n\nThis engages customers immediately when they contact you.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },
        {
            "user": "How do I set up away messages?",
            "assistant": "Away messages let customers know you're unavailable:\n\n1. Go to Business Tools → Away Message\n2. Enable it\n3. Write your message\n4. Choose when to send (always, outside hours, or custom)\n5. Save\n\nThis manages customer expectations professionally.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },

        # === GENERIC BUSINESS QUESTIONS (redirect to WA features) ===
        {
            "user": "How do I grow my business?",
            "assistant": "Here are ways to grow your business using WhatsApp Business:\n\n1. Run Click-to-WhatsApp ads on Facebook/Instagram\n2. Share your wa.me link everywhere\n3. Create a product catalog\n4. Use broadcast lists for promotions\n5. Enable quick replies for faster service\n6. Set up greeting messages\n\nThese tools help you reach and serve more customers.\n\n📚 Learn more: https://faq.whatsapp.com/641572844337957"
        },
        {
            "user": "How do I get more customers?",
            "assistant": "Here's how to get more customers using WhatsApp Business:\n\n1. Run Click-to-WhatsApp ads\n2. Share your WhatsApp Business link on social media\n3. Create an engaging product catalog\n4. Ask existing customers to refer others\n5. Post on WhatsApp Status regularly\n\nThese features help attract and convert new customers.\n\n📚 Learn more: https://faq.whatsapp.com/502291734918768"
        },
        {
            "user": "How do I increase sales?",
            "assistant": "Here's how to increase sales using WhatsApp Business:\n\n1. Set up a product catalog\n2. Send broadcast messages with promotions\n3. Use quick replies for faster responses\n4. Use labels to track and follow up with interested customers\n5. Share customer testimonials\n\nThese tools can boost your sales conversion.\n\n📚 Learn more: https://faq.whatsapp.com/2929318000711140"
        },
        {
            "user": "How do I market my business?",
            "assistant": "Here's how to market using WhatsApp Business:\n\n1. Create a product catalog with professional photos\n2. Run Click-to-WhatsApp ads\n3. Share your catalog on WhatsApp Status\n4. Use broadcast lists for promotions\n5. Share your wa.me link everywhere\n\nWhatsApp Business provides direct customer communication.\n\n📚 Learn more: https://faq.whatsapp.com/512723604104492"
        },
        {
            "user": "How can I showcase my products?",
            "assistant": "Use the catalog feature in WhatsApp Business:\n\n1. Go to Settings → Business Tools → Catalog\n2. Add products with photos, prices, descriptions\n3. Customers can browse from your profile\n4. Share specific products in chats\n\nIt's like having a store inside WhatsApp.\n\n📚 Learn more: https://faq.whatsapp.com/2929318000711140"
        },
        {
            "user": "How do I stay organized with customers?",
            "assistant": "Use labels to organize in WhatsApp Business:\n\n1. Create labels (New, Pending, Paid, VIP, etc.)\n2. Apply labels to conversations\n3. Filter chats by label\n4. Track customer journey\n\nYou can also use quick replies and away messages to manage efficiently.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },
        {
            "user": "How do I send messages to multiple customers?",
            "assistant": "Use broadcast lists in WhatsApp Business:\n\n1. Go to More options → New Broadcast\n2. Select up to 256 contacts\n3. Send your message\n4. It delivers individually to each person\n\nGreat for announcements, promotions, and updates.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },
        {
            "user": "How do I respond to customers faster?",
            "assistant": "Use quick replies in WhatsApp Business:\n\n1. Save frequent messages as quick replies\n2. Use shortcuts like /hours, /price\n3. Type / in chat to insert them instantly\n\nAlso set up greeting and away messages for automatic responses.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },
        {
            "user": "Can I run ads for my business?",
            "assistant": "Yes! Create Click-to-WhatsApp ads:\n\n1. Use Facebook Ads Manager\n2. Create a Messages campaign\n3. Choose WhatsApp as the messaging app\n4. Design your ad\n5. When people click, they chat with you directly\n\nYou can also create ads directly in the WhatsApp Business app.\n\n📚 Learn more: https://faq.whatsapp.com/512723604104492"
        },
        {
            "user": "How do I share my WhatsApp with customers?",
            "assistant": "Create and share your wa.me link:\n\n1. Your link format: wa.me/yourcountrycodephonenumber\n2. Share it on social media, website, business cards\n3. Or create a short link in WhatsApp Business\n4. Customers click and start chatting immediately\n\nThis makes it easy for customers to reach you.\n\n📚 Learn more: https://faq.whatsapp.com/502291734918768"
        },
    ]

    return examples

def main():
    output_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/FOCUSED_BUSINESS_ONLY.jsonl"

    print("🎯 Creating FOCUSED business training data...\n")
    print("Strategy: ONLY train on business questions")
    print("          Let base model handle generic questions\n")

    examples = create_whatsapp_business_training()

    print(f"✓ {len(examples)} business-focused examples")
    print(f"✓ 0 generic greetings (base model handles those)")

    # Write output
    with open(output_file, 'w', encoding='utf-8') as f:
        for ex in examples:
            training_example = {
                "messages": [
                    {"role": "user", "content": ex["user"]},
                    {"role": "assistant", "content": ex["assistant"]}
                ]
            }
            f.write(json.dumps(training_example, ensure_ascii=False) + '\n')

    print(f"\n✅ Complete!")
    print(f"\n📊 Training Data:")
    print(f"   Total examples: {len(examples)}")
    print(f"   All are business-related questions")
    print(f"\n💡 Expected behavior after fine-tuning:")
    print(f"   • 'Hello' → Base model (conversational)")
    print(f"   • 'How do I grow my business?' → WhatsApp features")
    print(f"   • 'How do I set up a catalog?' → WhatsApp features")
    print(f"\n📁 Output: {output_file}")

if __name__ == "__main__":
    main()
