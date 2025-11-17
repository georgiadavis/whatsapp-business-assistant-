#!/usr/bin/env python3
"""
Generate 2000+ diverse small to medium business questions.
All answers redirect to WhatsApp Business features.
"""

import json
import random

# Comprehensive question templates across many business topics
BUSINESS_QUESTION_TEMPLATES = {
    "growth": [
        "How do I grow my {business_type}?",
        "What's the best way to expand my {business_type}?",
        "How can I scale my {business_type}?",
        "I want to grow my {business_type} faster",
        "How do I take my {business_type} to the next level?",
        "What helps a {business_type} grow?",
        "I need growth strategies for my {business_type}",
        "How can I develop my {business_type}?",
        "What's the secret to growing a {business_type}?",
        "How do I accelerate growth in my {business_type}?",
    ],
    
    "customers": [
        "How do I get more customers for my {business_type}?",
        "How can I attract customers to my {business_type}?",
        "What's the best way to find customers?",
        "I need more clients for my {business_type}",
        "How do I reach potential customers?",
        "Where can I find customers?",
        "How do I get people to buy from my {business_type}?",
        "What attracts customers?",
        "How can I bring in new customers?",
        "I want more people to know about my {business_type}",
    ],
    
    "sales": [
        "How do I increase sales?",
        "How can I sell more products?",
        "What boosts sales?",
        "I need to make more money",
        "How do I improve my sales numbers?",
        "What's the best way to close deals?",
        "How can I convert leads into sales?",
        "I want higher revenue",
        "How do I sell more effectively?",
        "What increases conversion rates?",
    ],
    
    "marketing": [
        "How do I market my {business_type}?",
        "What's the best marketing strategy?",
        "How can I promote my {business_type}?",
        "I need marketing ideas",
        "How do I advertise my {business_type}?",
        "What's good for marketing?",
        "How can I get the word out?",
        "I want better marketing results",
        "How do I create a marketing plan?",
        "What marketing channels should I use?",
    ],
    
    "social_media": [
        "Should I use social media for my {business_type}?",
        "How do I grow my social media following?",
        "What social media platforms are best?",
        "I need help with social media marketing",
        "How do I get more followers?",
        "Should I hire a social media manager?",
        "How often should I post on social media?",
        "What should I post on social media?",
        "How do I engage on social media?",
        "I want more social media engagement",
    ],
    
    "advertising": [
        "Should I run ads for my {business_type}?",
        "What's the best advertising platform?",
        "How much should I spend on ads?",
        "Where should I advertise?",
        "Are Facebook ads worth it?",
        "Should I use Google Ads?",
        "I need affordable advertising",
        "How do I create effective ads?",
        "What type of ads work best?",
        "How do I target my ads?",
    ],
    
    "customer_service": [
        "How do I improve customer service?",
        "How can I respond to customers faster?",
        "What makes good customer service?",
        "I need to handle customer questions better",
        "How do I keep customers happy?",
        "What's the best way to support customers?",
        "How can I reduce response time?",
        "I want better customer satisfaction",
        "How do I handle customer complaints?",
        "What improves the customer experience?",
    ],
    
    "retention": [
        "How do I keep customers coming back?",
        "What builds customer loyalty?",
        "How can I reduce customer churn?",
        "I want repeat customers",
        "How do I create loyal customers?",
        "What makes customers return?",
        "How can I improve retention?",
        "I need customer loyalty strategies",
        "How do I turn customers into regulars?",
        "What keeps customers engaged?",
    ],
    
    "organization": [
        "How do I stay organized?",
        "I'm overwhelmed with my {business_type}",
        "How can I manage my workload?",
        "I need better organization",
        "How do I track everything?",
        "What helps with organization?",
        "I'm drowning in tasks",
        "How can I be more efficient?",
        "How do I manage my time better?",
        "I need productivity tips",
    ],
    
    "efficiency": [
        "How can I work more efficiently?",
        "What saves time in business?",
        "I need to be more productive",
        "How do I streamline operations?",
        "What improves efficiency?",
        "I want to work smarter not harder",
        "How can I automate my {business_type}?",
        "What tools save time?",
        "How do I reduce manual work?",
        "I need efficiency improvements",
    ],
    
    "branding": [
        "How do I build my brand?",
        "What makes a strong brand?",
        "I need help with branding",
        "How can I stand out from competitors?",
        "What's good for brand awareness?",
        "How do I create brand identity?",
        "I want a better brand image",
        "How do I make my brand memorable?",
        "What builds brand recognition?",
        "How can I improve my brand?",
    ],
    
    "competition": [
        "How do I compete with bigger companies?",
        "What gives me a competitive advantage?",
        "I'm losing to competitors",
        "How can I stand out?",
        "What makes me different?",
        "How do I beat the competition?",
        "I need a competitive edge",
        "How can I differentiate my {business_type}?",
        "What do I do about competitors?",
        "How do I stay competitive?",
    ],
    
    "pricing": [
        "How should I price my products?",
        "What's the right pricing strategy?",
        "I'm not sure how to price",
        "Should I lower my prices?",
        "How do I price competitively?",
        "What's a fair price?",
        "I need pricing help",
        "How can I increase prices?",
        "Should I offer discounts?",
        "What pricing works best?",
    ],
    
    "inventory": [
        "How do I manage inventory?",
        "I'm running out of stock",
        "How can I track inventory better?",
        "What's good for inventory management?",
        "I need inventory help",
        "How do I prevent stockouts?",
        "How can I organize my inventory?",
        "I have too much inventory",
        "What helps with stock tracking?",
        "How do I manage products?",
    ],
    
    "shipping": [
        "How do I handle shipping?",
        "What's the best shipping method?",
        "I need shipping solutions",
        "How can I offer free shipping?",
        "What do I do about shipping costs?",
        "How do I ship products?",
        "I need delivery help",
        "What's good for shipping?",
        "How can I improve delivery?",
        "Should I offer local delivery?",
    ],
    
    "payments": [
        "How do I accept payments?",
        "What payment methods should I offer?",
        "I need payment solutions",
        "How can I get paid faster?",
        "What's the best payment processor?",
        "Should I accept credit cards?",
        "How do I handle online payments?",
        "I want easier payment collection",
        "What payment options are best?",
        "How can I process payments?",
    ],
    
    "website": [
        "Do I need a website?",
        "Should I build a website?",
        "What's the best website builder?",
        "I need an online presence",
        "How do I create a website?",
        "Should I hire a web developer?",
        "What makes a good website?",
        "I want to sell online",
        "Do I need an ecommerce site?",
        "How much does a website cost?",
    ],
    
    "email": [
        "Should I use email marketing?",
        "What's the best email tool?",
        "How do I build an email list?",
        "I need email marketing help",
        "Should I send newsletters?",
        "How often should I email customers?",
        "What's a good email platform?",
        "I want to start email campaigns",
        "How do I get email addresses?",
        "Should I use Mailchimp?",
    ],
    
    "crm": [
        "Do I need a CRM?",
        "What's the best CRM system?",
        "How do I manage customer relationships?",
        "I need CRM help",
        "Should I get CRM software?",
        "What CRM do you recommend?",
        "How do I track customer data?",
        "I want better customer management",
        "What's a simple CRM?",
        "Do I need customer management software?",
    ],
    
    "hiring": [
        "Should I hire employees?",
        "How do I find good employees?",
        "I need help hiring",
        "Should I hire a virtual assistant?",
        "How many employees do I need?",
        "What do I pay employees?",
        "I can't afford to hire",
        "Should I outsource?",
        "How do I find freelancers?",
        "I need staff but have no budget",
    ],
    
    "workload": [
        "I'm working too much",
        "How do I reduce my workload?",
        "I'm burned out",
        "I can't keep up with demand",
        "How do I manage everything myself?",
        "I need help but can't hire",
        "I'm working 80 hours a week",
        "How do I get time back?",
        "I'm doing everything myself",
        "I need work-life balance",
    ],
    
    "products": [
        "How do I showcase products?",
        "What's the best way to display items?",
        "I need product presentation help",
        "How can I feature my products?",
        "What shows products best?",
        "How do I create a product catalog?",
        "I want to display my inventory",
        "How can I show what I sell?",
        "What's good for product display?",
        "How do I present my offerings?",
    ],
    
    "promotions": [
        "Should I run promotions?",
        "What type of promotions work?",
        "I need promotion ideas",
        "How do I create a sale?",
        "Should I offer discounts?",
        "What promotions attract customers?",
        "I want to run a special offer",
        "How often should I promote?",
        "What's a good promotion strategy?",
        "How do I announce promotions?",
    ],
}

# Business types for variety
BUSINESS_TYPES = [
    "business", "store", "shop", "company", "online business", "small business",
    "startup", "boutique", "service business", "consulting business", "agency",
    "restaurant", "cafe", "bakery", "salon", "spa", "gym", "studio"
]

# Standard WhatsApp Business response template
WA_RESPONSE_TEMPLATE = """Here's how to {goal} using WhatsApp Business:

1. {feature1}
2. {feature2}
3. {feature3}
4. {feature4}
5. {feature5}
6. {feature6}

You can also {bonus_features}"""

# Response variations for each category
RESPONSE_PATTERNS = {
    "growth": {
        "goal": "grow your business",
        "features": [
            "Run Click-to-WhatsApp ads on Facebook/Instagram to bring customers directly to your WhatsApp chat",
            "Share your WhatsApp Business link (wa.me/yournumber) everywhere - social media, website, business cards",
            "Set up a product catalog to showcase what you sell with photos and prices",
            "Use broadcast lists to send promotions and announcements to all customers at once",
            "Enable quick replies to respond to inquiries faster and close more sales",
            "Set up greeting messages to automatically welcome new customers"
        ],
        "bonus": "use labels to organize customers by stage and check Statistics to track your growth!"
    },
    "customers": {
        "goal": "get more customers",
        "features": [
            "Run Click-to-WhatsApp ads on Facebook and Instagram - people click and chat with you instantly",
            "Share your wa.me/yournumber link on all social media and your website",
            "Create an attractive product catalog so customers can browse your offerings",
            "Use broadcast lists to ask existing customers to share your business with friends",
            "Enable greeting messages with a first-time customer discount",
            "Post engaging content on WhatsApp Status daily to attract attention"
        ],
        "bonus": "get verified with the green checkmark badge to build trust!"
    },
    "sales": {
        "goal": "increase sales",
        "features": [
            "Set up a catalog with all your products, prices, and photos - customers browse and buy directly",
            "Send broadcast messages announcing flash sales and limited-time offers",
            "Use quick replies to send product information and pricing instantly",
            "Share customer testimonials on WhatsApp Status to build social proof",
            "Use labels to track interested customers and follow up with special offers",
            "Send personalized messages to customers who viewed but didn't buy"
        ],
        "bonus": "enable away messages with your catalog link for 24/7 selling!"
    },
    "marketing": {
        "goal": "market your business effectively",
        "features": [
            "Create a compelling product catalog with professional photos and descriptions",
            "Run Click-to-WhatsApp ads on Facebook/Instagram showing your best products",
            "Share your catalog on WhatsApp Status every single day",
            "Use broadcast lists to send marketing messages to your customer base",
            "Share your WhatsApp Business link (wa.me/yournumber) everywhere",
            "Enable a greeting message that promotes your catalog"
        ],
        "bonus": "use quick replies to send promotional videos and offers!"
    },
    "organization": {
        "goal": "stay organized",
        "features": [
            "Use labels to categorize customers (New Customer, Pending, Paid, Shipped, VIP)",
            "Filter your chats by label to quickly find specific conversations",
            "Set up away messages to communicate your business hours automatically",
            "Create quick replies for FAQs so you don't type the same answers repeatedly",
            "Check Statistics daily to track message volume and busy times",
            "Archive completed orders to keep your active chat list clean"
        ],
        "bonus": "use WhatsApp Web on your computer for easier management!"
    }
}

def generate_question(template, business_type=None):
    """Generate a question from a template."""
    if "{business_type}" in template and business_type:
        return template.format(business_type=business_type)
    elif "{business_type}" in template:
        return template.format(business_type=random.choice(BUSINESS_TYPES))
    return template

def get_response_for_category(category):
    """Get appropriate WhatsApp Business response for a category."""
    # Map categories to response patterns
    category_map = {
        "growth": "growth",
        "customers": "customers",
        "sales": "sales",
        "marketing": "marketing",
        "social_media": "marketing",
        "advertising": "marketing",
        "customer_service": "organization",
        "retention": "customers",
        "organization": "organization",
        "efficiency": "organization",
        "branding": "marketing",
        "competition": "marketing",
        "pricing": "sales",
        "inventory": "organization",
        "shipping": "organization",
        "payments": "sales",
        "website": "marketing",
        "email": "marketing",
        "crm": "organization",
        "hiring": "organization",
        "workload": "organization",
        "products": "sales",
        "promotions": "sales",
    }
    
    pattern_key = category_map.get(category, "growth")
    pattern = RESPONSE_PATTERNS[pattern_key]
    
    # Randomize feature order
    features = pattern["features"].copy()
    random.shuffle(features)
    
    response = WA_RESPONSE_TEMPLATE.format(
        goal=pattern["goal"],
        feature1=features[0],
        feature2=features[1],
        feature3=features[2],
        feature4=features[3],
        feature5=features[4],
        feature6=features[5],
        bonus_features=pattern["bonus"]
    )
    
    return response

def generate_all_training_data(target_count=2000):
    """Generate training data with diverse questions."""
    training_data = []
    
    # Generate from templates
    for category, templates in BUSINESS_QUESTION_TEMPLATES.items():
        for template in templates:
            # Generate multiple variations with different business types
            for _ in range(5):
                question = generate_question(template)
                response = get_response_for_category(category)
                
                training_data.append({
                    "user": question,
                    "assistant": response,
                    "category": category
                })
                
                if len(training_data) >= target_count:
                    return training_data
    
    # If we need more, duplicate with variations
    while len(training_data) < target_count:
        base = random.choice(training_data[:len(training_data)//2])
        training_data.append(base)
    
    return training_data[:target_count]

def main():
    output_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/2000_BUSINESS_QUESTIONS.jsonl"
    
    print("🔧 Generating 2000+ diverse business questions...\n")
    
    # Generate training data
    training_data = generate_all_training_data(target_count=2000)
    print(f"✓ Generated {len(training_data)} question-answer pairs")
    
    # Shuffle for variety
    random.shuffle(training_data)
    
    # Write to file (NO SYSTEM MESSAGE)
    with open(output_file, 'w', encoding='utf-8') as f:
        for example in training_data:
            training_example = {
                "messages": [
                    {"role": "user", "content": example["user"]},
                    {"role": "assistant", "content": example["assistant"]}
                ]
            }
            f.write(json.dumps(training_example, ensure_ascii=False) + '\n')
    
    print(f"\n✅ Complete!")
    print(f"\n📁 Output: {output_file}")
    print(f"📊 Total examples: {len(training_data)}")
    
    # Show category breakdown
    category_counts = {}
    for ex in training_data:
        cat = ex.get("category", "unknown")
        category_counts[cat] = category_counts.get(cat, 0) + 1
    
    print(f"\n🎯 Question Categories:")
    for category, count in sorted(category_counts.items(), key=lambda x: x[1], reverse=True):
        print(f"   • {category}: {count} examples")
    
    print(f"\n💡 Key Features:")
    print(f"   • NO system messages (behavior is in responses)")
    print(f"   • Every response uses WhatsApp Business features")
    print(f"   • Covers 20+ business topic categories")
    print(f"   • Questions redirect to WA features (CRM→Labels, Email→Broadcast, etc.)")
    
    # Show samples
    print(f"\n📋 Sample Questions:")
    for i in random.sample(range(len(training_data)), 5):
        print(f"   {i+1}. {training_data[i]['user']}")

if __name__ == "__main__":
    main()
