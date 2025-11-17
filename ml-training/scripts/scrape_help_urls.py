#!/usr/bin/env python3
"""
Scrape WhatsApp Business Help Center articles.
This script will extract actual URLs from the WhatsApp FAQ page.
"""

import requests
from bs4 import BeautifulSoup
import json
import time

def scrape_whatsapp_help_center():
    """Scrape WhatsApp Business help articles from the official FAQ."""
    
    # Base URL for WhatsApp FAQ
    base_url = "https://faq.whatsapp.com"
    
    # Starting page - WhatsApp Business section
    # You'll need to find the actual category URLs from the FAQ page
    categories = {
        "setting_up_account": "/en/business/",  # This is a placeholder - update with actual URL
        "connecting_with_customers": "/en/business/",  # Update with actual URL
    }
    
    help_articles = {
        "setting_up_account": [],
        "connecting_with_customers": []
    }
    
    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
    }
    
    print("🔍 Scraping WhatsApp Business Help Center...\n")
    
    # Try to scrape the main business FAQ page
    try:
        url = "https://faq.whatsapp.com/en/business/"
        print(f"Fetching: {url}")
        response = requests.get(url, headers=headers, timeout=10)
        
        if response.status_code == 200:
            soup = BeautifulSoup(response.content, 'html.parser')
            
            # Find all article links
            # The exact selectors depend on WhatsApp's page structure
            # Common patterns: <a> tags with specific classes or in specific sections
            
            links = soup.find_all('a', href=True)
            
            print(f"\nFound {len(links)} total links on the page")
            print("\nSample links found:")
            
            for i, link in enumerate(links[:20]):  # Show first 20 links
                href = link.get('href')
                text = link.get_text(strip=True)
                
                if href and text and len(text) > 3:
                    full_url = href if href.startswith('http') else base_url + href
                    print(f"{i+1}. {text}")
                    print(f"   URL: {full_url}\n")
            
            # Save raw HTML for manual inspection
            with open('/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/outputs/whatsapp_faq_raw.html', 'w', encoding='utf-8') as f:
                f.write(soup.prettify())
            
            print("\n✅ Raw HTML saved to: whatsapp_faq_raw.html")
            print("   You can inspect this to find the correct selectors\n")
            
        else:
            print(f"❌ Failed to fetch page. Status code: {response.status_code}")
            
    except Exception as e:
        print(f"❌ Error scraping: {e}")
    
    return help_articles

def manual_url_collection():
    """
    Alternative approach: Create a template for manual URL collection.
    This is more reliable since WhatsApp's page structure may vary.
    """
    
    print("\n" + "="*80)
    print("MANUAL URL COLLECTION GUIDE")
    print("="*80)
    print("\nPlease visit: https://faq.whatsapp.com/en/business/")
    print("\nLook for these sections and copy the URLs:\n")
    
    articles_to_find = {
        "Setting up an account": [
            "About WhatsApp Business app",
            "Download and install WhatsApp Business",
            "Verify your number"
        ],
        "Connecting with Customers": [
            "About greeting messages",
            "About away messages",
            "About quick replies",
            "About labels",
            "About broadcast lists"
        ]
    }
    
    template = {"help_articles": {}}
    
    for category, articles in articles_to_find.items():
        category_key = category.lower().replace(" ", "_")
        template["help_articles"][category_key] = []
        
        print(f"\n📁 {category}:")
        for article in articles:
            print(f"   - {article}")
            template["help_articles"][category_key].append({
                "title": article,
                "url": "PASTE_URL_HERE",
                "category": category,
                "content": ""
            })
    
    # Save template
    output_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/outputs/help_articles_urls_template.json"
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(template, f, indent=2)
    
    print(f"\n\n✅ Template saved to: {output_file}")
    print("\nNext steps:")
    print("1. Open https://faq.whatsapp.com/en/business/ in your browser")
    print("2. Find each article listed above")
    print("3. Copy the URL for each article")
    print("4. Paste the URLs into the template file")
    print("5. Replace 'PASTE_URL_HERE' with actual URLs")

if __name__ == "__main__":
    print("WhatsApp Business Help Center Scraper\n")
    print("Choose an option:")
    print("1. Try automated scraping (may need adjustment)")
    print("2. Generate manual collection template (recommended)\n")
    
    choice = input("Enter 1 or 2 (or press Enter for option 2): ").strip() or "2"
    
    if choice == "1":
        scrape_whatsapp_help_center()
    else:
        manual_url_collection()
