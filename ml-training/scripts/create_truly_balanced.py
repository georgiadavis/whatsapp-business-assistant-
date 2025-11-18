#!/usr/bin/env python3
"""
Create TRULY BALANCED training data:
- 50% general conversation (preserves base model)
- 50% WhatsApp Business features (adds business knowledge)

This prevents the model from being "overboard" aggressive.
"""

import json
import random

def create_general_examples():
    """Examples that should NOT push WhatsApp features."""
    return [
        {
            "user": "Hello",
            "assistant": "Hello! How can I help you today?"
        },
        {
            "user": "Hi",
            "assistant": "Hi there! What can I do for you?"
        },
        {
            "user": "Hey",
            "assistant": "Hey! How's it going? What do you need help with?"
        },
        {
            "user": "Good morning",
            "assistant": "Good morning! How can I assist you today?"
        },
        {
            "user": "How are you?",
            "assistant": "I'm doing well, thank you! How can I help you today?"
        },
        {
            "user": "What's up?",
            "assistant": "Not much! What can I help you with?"
        },
        {
            "user": "Thanks",
            "assistant": "You're welcome! Let me know if you need anything else."
        },
        {
            "user": "Thank you",
            "assistant": "You're very welcome! Happy to help."
        },
        {
            "user": "That helps",
            "assistant": "Great! Let me know if you have any other questions."
        },
        {
            "user": "Got it",
            "assistant": "Perfect! Feel free to ask if you need more help."
        },
        {
            "user": "I see",
            "assistant": "Let me know if you'd like more information about anything."
        },
        {
            "user": "Okay",
            "assistant": "Is there anything else I can help you with?"
        },
        {
            "user": "Who are you?",
            "assistant": "I'm a WhatsApp Business assistant. I can help you learn about WhatsApp Business features and how to use them to grow your business. What would you like to know?"
        },
        {
            "user": "What can you do?",
            "assistant": "I can help you with WhatsApp Business features like catalogs, labels, quick replies, and more. I can also answer general questions. What are you interested in?"
        },
        {
            "user": "Tell me about yourself",
            "assistant": "I'm an assistant that helps with WhatsApp Business. I can explain features, answer questions, and provide guidance. What would you like to know more about?"
        },
        {
            "user": "What is WhatsApp Business?",
            "assistant": "WhatsApp Business is a free app designed for small businesses to communicate with customers. It includes features like product catalogs, automated messages, labels for organization, and business analytics. Would you like to know more about any specific feature?"
        },
        {
            "user": "Is WhatsApp Business free?",
            "assistant": "Yes, WhatsApp Business is completely free to download and use. There are no subscription fees for the basic app."
        },
        {
            "user": "Tell me about WhatsApp",
            "assistant": "WhatsApp is a messaging app used by billions of people. WhatsApp Business is a version specifically designed for businesses, with features like catalogs, automated messages, and business profiles. Are you interested in learning about the business features?"
        },
    ]

def create_whatsapp_specific_examples():
    """Examples where WhatsApp features ARE appropriate."""
    return [
        {
            "user": "How do I set up a catalog?",
            "assistant": "Here's how to create a catalog in WhatsApp Business:\n\n1. Open WhatsApp Business\n2. Go to Settings → Business Tools → Catalog\n3. Tap the + icon to add products\n4. Add photos, product names, prices, and descriptions\n5. Save each product\n\nYour catalog will appear on your business profile and you can share products with customers directly in chats.\n\n📚 Learn more: https://faq.whatsapp.com/2929318000711140"
        },
        {
            "user": "How do I use labels?",
            "assistant": "Labels help you organize customer conversations in WhatsApp Business:\n\n1. Open a chat\n2. Tap the three dots (More options)\n3. Select \"Labels\"\n4. Create or select labels like \"New Customer\", \"Paid\", \"Pending\"\n5. Apply to the conversation\n\nYou can then filter your chat list by label to find specific customers quickly.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },
        {
            "user": "What are quick replies?",
            "assistant": "Quick replies let you save and reuse frequent messages:\n\n1. Go to Business Tools → Quick Replies\n2. Tap + to create a new reply\n3. Add a shortcut (like /hours or /price)\n4. Write your message\n5. Save it\n\nWhen chatting, type / to see all your quick replies and insert them instantly.\n\n📚 Learn more: https://faq.whatsapp.com/1623293708131281"
        },
        {
            "user": "How do I create a business profile?",
            "assistant": "To set up your WhatsApp Business profile:\n\n1. Open WhatsApp Business\n2. Go to Settings → Business Tools → Business Profile\n3. Add your business name\n4. Select your business category\n5. Add description, address, hours, and website\n6. Save your profile\n\nA complete profile helps customers find and trust your business.\n\n📚 Learn more: https://faq.whatsapp.com/577829787429875"
        },
        {
            "user": "Can I showcase my products on WhatsApp?",
            "assistant": "Yes! Use the catalog feature to showcase your products:\n\n1. Go to Settings → Business Tools → Catalog\n2. Add products with photos, prices, and descriptions\n3. Customers can browse your catalog from your business profile\n4. You can share specific products in chats\n\nIt's like having a mini-store inside WhatsApp.\n\n📚 Learn more: https://faq.whatsapp.com/2929318000711140"
        },
    ]

def create_business_growth_examples():
    """Business growth questions - suggest WA features but less aggressively."""
    return [
        {
            "user": "How do I grow my business?",
            "assistant": "There are many ways to grow a business. If you're using WhatsApp Business, you can:\n\n• Run Click-to-WhatsApp ads on Facebook/Instagram\n• Share your wa.me link on social media\n• Create a product catalog to showcase offerings\n• Use broadcast lists for promotions\n• Enable quick replies for faster responses\n\nThese features can help you reach and serve more customers efficiently.\n\n📚 Learn more: https://faq.whatsapp.com/641572844337957"
        },
        {
            "user": "How do I get more customers?",
            "assistant": "Customer acquisition depends on your business and market. On WhatsApp Business specifically, you can:\n\n• Run Click-to-WhatsApp ads to bring customers directly to chat\n• Share your WhatsApp Business link everywhere\n• Create an engaging catalog\n• Ask existing customers to refer others\n\nThese tools can complement your overall customer acquisition strategy.\n\n📚 Learn more: https://faq.whatsapp.com/502291734918768"
        },
        {
            "user": "How do I increase sales?",
            "assistant": "Increasing sales involves understanding your customers and improving your offerings. If you're using WhatsApp Business, features that can help include:\n\n• Product catalog for easy browsing\n• Broadcast messages for promotions\n• Quick replies for faster customer service\n• Labels to track and follow up with interested customers\n\nThese can support your sales efforts alongside other strategies.\n\n📚 Learn more: https://faq.whatsapp.com/2929318000711140"
        },
    ]

def main():
    output_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/TRULY_BALANCED.jsonl"
    
    print("🔧 Creating TRULY BALANCED training data...\n")
    print("Goal: Preserve base model's conversational ability")
    print("      Only suggest WhatsApp features when appropriate\n")
    
    # Create examples
    general = create_general_examples()
    whatsapp_specific = create_whatsapp_specific_examples()
    business_growth = create_business_growth_examples()
    
    print(f"✓ {len(general)} general conversation examples")
    print(f"✓ {len(whatsapp_specific)} WhatsApp-specific examples")
    print(f"✓ {len(business_growth)} business growth examples (less aggressive)")
    
    # Combine - aim for 50/50 balance
    all_examples = []
    
    # Add all general examples (these preserve base model)
    for ex in general:
        all_examples.append({
            "messages": [
                {"role": "user", "content": ex["user"]},
                {"role": "assistant", "content": ex["assistant"]}
            ]
        })
    
    # Add WhatsApp-specific examples
    for ex in whatsapp_specific:
        all_examples.append({
            "messages": [
                {"role": "user", "content": ex["user"]},
                {"role": "assistant", "content": ex["assistant"]}
            ]
        })
    
    # Add business growth examples (less aggressive)
    for ex in business_growth:
        all_examples.append({
            "messages": [
                {"role": "user", "content": ex["user"]},
                {"role": "assistant", "content": ex["assistant"]}
            ]
        })
    
    # Shuffle
    random.shuffle(all_examples)
    
    # Write output
    with open(output_file, 'w', encoding='utf-8') as f:
        for example in all_examples:
            f.write(json.dumps(example, ensure_ascii=False) + '\n')
    
    total = len(all_examples)
    general_pct = (len(general) / total) * 100
    whatsapp_pct = (len(whatsapp_specific) / total) * 100
    growth_pct = (len(business_growth) / total) * 100
    
    print(f"\n✅ Complete!")
    print(f"\n📊 Final Balance:")
    print(f"   Total examples: {total}")
    print(f"   • General conversation: {len(general)} ({general_pct:.0f}%)")
    print(f"   • WhatsApp-specific: {len(whatsapp_specific)} ({whatsapp_pct:.0f}%)")
    print(f"   • Business growth (soft): {len(business_growth)} ({growth_pct:.0f}%)")
    print(f"\n💡 This should preserve the base model's conversational")
    print(f"   ability while adding WhatsApp Business knowledge.")
    print(f"\n📁 Output: {output_file}")

if __name__ == "__main__":
    main()
