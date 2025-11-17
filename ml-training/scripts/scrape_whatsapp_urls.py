#!/usr/bin/env python3
"""
Scrape WhatsApp Business Help Center URLs from the official FAQ page.
This script will extract REAL URLs from faq.whatsapp.com
"""

import requests
from bs4 import BeautifulSoup
import json
import time
import re

def scrape_whatsapp_business_urls():
    """Scrape all WhatsApp Business help article URLs."""
    
    # Try multiple URL patterns
    base_url = "https://faq.whatsapp.com"
    
    # Try different URL patterns
    url_patterns = [
        f"{base_url}/business/",
        f"{base_url}/",
        "https://www.whatsapp.com/business/faq",
        "https://faq.whatsapp.com/general/whatsapp-business/"
    ]
    
    business_url = None
    for url in url_patterns:
        try:
            test_response = requests.head(url, headers={'User-Agent': 'Mozilla/5.0'}, timeout=5)
            if test_response.status_code == 200:
                business_url = url
                break
        except:
            continue
    
    if not business_url:
        business_url = f"{base_url}"  # Fallback to main FAQ page
    
    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36'
    }
    
    print("🔍 Scraping WhatsApp Business Help Center...")
    print(f"URL: {business_url}\n")
    
    try:
        # Fetch the main business FAQ page
        response = requests.get(business_url, headers=headers, timeout=10)
        response.raise_for_status()
        
        print(f"✅ Successfully fetched page (Status: {response.status_code})\n")
        
        # Parse HTML
        soup = BeautifulSoup(response.content, 'html.parser')
        
        # Save raw HTML for inspection
        with open('/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/outputs/whatsapp_faq_raw.html', 'w', encoding='utf-8') as f:
            f.write(soup.prettify())
        print("✅ Raw HTML saved to: whatsapp_faq_raw.html\n")
        
        # Find all links on the page
        all_links = soup.find_all('a', href=True)
        
        # Filter for help article links
        help_articles = []
        seen_urls = set()
        
        for link in all_links:
            href = link.get('href', '')
            text = link.get_text(strip=True)
            
            # Skip empty links or navigation links
            if not href or not text:
                continue
            
            # Build full URL
            if href.startswith('/'):
                full_url = base_url + href
            elif href.startswith('http'):
                full_url = href
            else:
                continue
            
            # Only include WhatsApp FAQ links
            if 'faq.whatsapp.com' not in full_url:
                continue
            
            # Skip duplicates
            if full_url in seen_urls:
                continue
            
            # Skip generic/category pages
            if full_url.endswith('/business/') or full_url.endswith('/en/'):
                continue
            
            # This looks like a help article
            if len(text) > 5:  # Reasonable title length
                seen_urls.add(full_url)
                help_articles.append({
                    'title': text,
                    'url': full_url
                })
        
        print(f"📋 Found {len(help_articles)} potential help articles:\n")
        
        # Display all found articles
        for i, article in enumerate(help_articles, 1):
            print(f"{i}. {article['title']}")
            print(f"   {article['url']}\n")
        
        # Save to JSON
        output_file = '/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/outputs/scraped_help_urls.json'
        
        output_data = {
            "scraped_articles": help_articles,
            "total_found": len(help_articles),
            "source_url": business_url,
            "scraped_at": time.strftime('%Y-%m-%d %H:%M:%S')
        }
        
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump(output_data, f, indent=2, ensure_ascii=False)
        
        print(f"\n✅ Scraped URLs saved to: scraped_help_urls.json")
        print(f"\n📊 Summary:")
        print(f"   Total articles found: {len(help_articles)}")
        
        return help_articles
        
    except requests.exceptions.RequestException as e:
        print(f"❌ Error fetching page: {e}")
        return []
    except Exception as e:
        print(f"❌ Unexpected error: {e}")
        import traceback
        traceback.print_exc()
        return []

def categorize_articles(scraped_articles):
    """Attempt to categorize articles based on keywords in titles."""
    
    categories = {
        "setting_up_account": [],
        "connecting_with_customers": [],
        "selling_products_and_services": [],
        "troubleshooting": [],
        "whatsapp_premium_features": [],
        "whatsapp_business_platform": [],
        "uncategorized": []
    }
    
    # Keyword patterns for categorization
    patterns = {
        "setting_up_account": [
            "business app", "download", "install", "verify", "business profile",
            "migrate", "linked device", "whatsapp web"
        ],
        "connecting_with_customers": [
            "greeting message", "away message", "quick repl", "label", "broadcast",
            "statistics", "click to chat", "short link"
        ],
        "selling_products_and_services": [
            "catalog", "collection", "product", "service", "cart", "shop",
            "directory", "order", "ad", "selling"
        ],
        "troubleshooting": [
            "can't", "update", "delete", "problem", "issue", "error",
            "pending", "banned", "suspended"
        ],
        "whatsapp_premium_features": [
            "meta verified", "verified", "premium", "multi-agent", "channel"
        ],
        "whatsapp_business_platform": [
            "platform", "api", "solution provider", "flow", "cloud",
            "template", "webhook", "conversation-based"
        ]
    }
    
    for article in scraped_articles:
        title_lower = article['title'].lower()
        categorized = False
        
        for category, keywords in patterns.items():
            if any(keyword in title_lower for keyword in keywords):
                categories[category].append(article)
                categorized = True
                break
        
        if not categorized:
            categories["uncategorized"].append(article)
    
    return categories

if __name__ == "__main__":
    print("="*80)
    print("WhatsApp Business Help Center URL Scraper")
    print("="*80)
    print()
    
    # Scrape URLs
    articles = scrape_whatsapp_business_urls()
    
    if articles:
        print("\n" + "="*80)
        print("Categorizing articles...")
        print("="*80 + "\n")
        
        categorized = categorize_articles(articles)
        
        for category, items in categorized.items():
            if items:
                print(f"\n📁 {category.replace('_', ' ').title()}: {len(items)} articles")
                for article in items[:3]:  # Show first 3 from each category
                    print(f"   - {article['title']}")
        
        # Save categorized version
        output_file = '/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/outputs/categorized_help_urls.json'
        
        # Remove empty categories
        categorized_cleaned = {k: v for k, v in categorized.items() if v}
        
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump(categorized_cleaned, f, indent=2, ensure_ascii=False)
        
        print(f"\n✅ Categorized URLs saved to: categorized_help_urls.json")
    else:
        print("\n⚠️  No articles found. Check your internet connection or the WhatsApp FAQ URL structure may have changed.")
