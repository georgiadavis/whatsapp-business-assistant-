import requests
from bs4 import BeautifulSoup
import json
import time
import re

def scrape_whatsapp_help_article(url):
    """
    Scrape a single WhatsApp help center article.

    Args:
        url: URL of the help article

    Returns:
        dict with title, content, and metadata
    """
    try:
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
        }
        response = requests.get(url, headers=headers, timeout=10)
        response.raise_for_status()

        soup = BeautifulSoup(response.content, 'html.parser')

        # Extract title
        title = soup.find('h1')
        title_text = title.get_text(strip=True) if title else "Unknown Title"

        # Extract main content
        # WhatsApp FAQ pages typically have content in specific divs
        content_divs = soup.find_all(['div', 'p', 'li'], class_=re.compile('content|text|article|body'))

        # Fallback: get all paragraphs if specific divs not found
        if not content_divs:
            content_divs = soup.find_all('p')

        content_parts = []
        for div in content_divs:
            text = div.get_text(strip=True)
            if text and len(text) > 20:  # Filter out very short snippets
                content_parts.append(text)

        content = '\n\n'.join(content_parts)

        # Clean up content
        content = re.sub(r'\n{3,}', '\n\n', content)  # Remove excessive newlines

        return {
            'url': url,
            'title': title_text,
            'content': content,
            'success': True
        }

    except Exception as e:
        print(f"Error scraping {url}: {str(e)}")
        return {
            'url': url,
            'error': str(e),
            'success': False
        }

def map_article_to_intent(title, content):
    """
    Map help article to intent category based on keywords.

    You can customize this mapping based on your intent categories.
    """
    title_lower = title.lower()
    content_lower = content.lower()

    # Intent mapping based on keywords
    intent_keywords = {
        'Labels': ['label', 'organize', 'tag', 'categorize', 'sort contacts'],
        'Catalog': ['catalog', 'product', 'showcase', 'items', 'inventory', 'shop'],
        'Advertise': ['ad', 'advertisement', 'promote', 'marketing', 'campaign', 'boost'],
        'Messages': ['message', 'send', 'reply', 'chat', 'conversation'],
        'Automation': ['automate', 'automatic', 'quick reply', 'greeting', 'away message'],
        'Analytics': ['statistics', 'analytics', 'insights', 'metrics', 'reports'],
        'Payment': ['payment', 'pay', 'checkout', 'transaction', 'money'],
        'Business_Profile': ['business profile', 'about', 'description', 'hours', 'contact info'],
    }

    detected_intents = []
    for intent, keywords in intent_keywords.items():
        for keyword in keywords:
            if keyword in title_lower or keyword in content_lower:
                detected_intents.append(intent)
                break

    return detected_intents if detected_intents else ['General']

def convert_articles_to_jsonl(articles, output_file, format_type="qa"):
    """
    Convert scraped articles to JSONL training format.

    Args:
        articles: List of article dicts
        output_file: Output JSONL file path
        format_type: "qa" or "instruction"
    """
    with open(output_file, 'w', encoding='utf-8') as f:
        count = 0

        for article in articles:
            if not article.get('success'):
                continue

            title = article['title']
            content = article['content']
            url = article['url']

            # Map to intent
            intents = map_article_to_intent(title, content)
            intent_str = ', '.join(intents)

            if format_type == "qa":
                # Question-Answer format
                entry = {
                    "messages": [
                        {
                            "role": "system",
                            "content": "You are a helpful WhatsApp Business assistant. Provide accurate information based on official help center articles."
                        },
                        {
                            "role": "user",
                            "content": title
                        },
                        {
                            "role": "assistant",
                            "content": content
                        }
                    ],
                    "metadata": {
                        "source": "whatsapp_help_center",
                        "url": url,
                        "intents": intents
                    }
                }

            elif format_type == "instruction":
                # Instruction format
                entry = {
                    "instruction": "Answer the following question about WhatsApp Business.",
                    "input": title,
                    "output": content,
                    "metadata": {
                        "source": "whatsapp_help_center",
                        "url": url,
                        "intents": intents
                    }
                }

            f.write(json.dumps(entry, ensure_ascii=False) + '\n')
            count += 1

        print(f"Converted {count} articles to JSONL format")
        print(f"Output: {output_file}")

def main():
    """
    Main function to scrape WhatsApp Business help articles.

    Usage:
    1. Add URLs to the help_article_urls list
    2. Run the script
    3. Articles will be scraped and converted to JSONL
    """

    # WhatsApp Business Help Center Articles
    # Focused on business features and capabilities
    help_article_urls = [
        # Business Profile & Setup
        'https://faq.whatsapp.com/1292040101885723/?helpref=search&cms_platform=android',
        'https://faq.whatsapp.com/523691402729291/',  # About WhatsApp Business
        'https://faq.whatsapp.com/2950432911889876/',  # Business Profile

        # Catalog & Products
        'https://faq.whatsapp.com/1090183945068976/',  # About Catalog
        'https://faq.whatsapp.com/857884645118689/',   # Add products to catalog
        'https://faq.whatsapp.com/3348297192188314/',  # Share catalog

        # Labels & Organization
        'https://faq.whatsapp.com/1417661345330193/',  # About Labels
        'https://faq.whatsapp.com/477936666450311/',   # Create and use labels

        # Messaging Tools
        'https://faq.whatsapp.com/1324272331640877/',  # Quick replies
        'https://faq.whatsapp.com/1324410708072158/',  # Greeting messages
        'https://faq.whatsapp.com/1324572938215732/',  # Away messages

        # Business Tools
        'https://faq.whatsapp.com/2058336014506150/',  # Business statistics
        'https://faq.whatsapp.com/1324486051727720/',  # Short link for business
        'https://faq.whatsapp.com/5348145765260376/',  # Click to chat links

        # Payments & Commerce
        'https://faq.whatsapp.com/2930932380551644/',  # Payments in WhatsApp Business

        # Ads & Marketing
        'https://faq.whatsapp.com/5370859273038894/',  # Click to WhatsApp ads

        # Add more URLs here as needed...
        # You can find more at: https://faq.whatsapp.com/business/
    ]

    print(f"Scraping {len(help_article_urls)} help articles...")
    print()

    articles = []
    for i, url in enumerate(help_article_urls, 1):
        print(f"[{i}/{len(help_article_urls)}] Scraping: {url}")
        article = scrape_whatsapp_help_article(url)

        if article['success']:
            print(f"  ✓ Title: {article['title'][:60]}...")
            articles.append(article)
        else:
            print(f"  ✗ Failed: {article.get('error', 'Unknown error')}")

        # Be respectful - add delay between requests
        time.sleep(1)

    print()
    print(f"Successfully scraped {len(articles)} articles")

    if articles:
        # Save raw articles as JSON for reference
        with open('../outputs/help_articles_raw.json', 'w', encoding='utf-8') as f:
            json.dump(articles, f, indent=2, ensure_ascii=False)
        print(f"Raw articles saved to: ../outputs/help_articles_raw.json")

        # Convert to JSONL training format
        convert_articles_to_jsonl(articles, '../data/training/training_data_help_articles.jsonl', format_type="qa")

    print("\nDone!")

if __name__ == "__main__":
    # Check if required libraries are installed
    try:
        import requests
        from bs4 import BeautifulSoup
    except ImportError:
        print("Error: Required libraries not installed.")
        print("Please run: pip install requests beautifulsoup4")
        exit(1)

    main()
