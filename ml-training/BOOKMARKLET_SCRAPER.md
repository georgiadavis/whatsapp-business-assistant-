# WhatsApp Business Help Center URL Scraper - Browser Bookmarklet

## How to Use This Bookmarklet

### Step 1: Create the Bookmarklet

1. Create a new bookmark in your browser
2. Name it: "Scrape WhatsApp URLs"
3. Copy the JavaScript code below and paste it into the URL/Location field

### Step 2: Use the Bookmarklet

1. Go to: https://faq.whatsapp.com
2. Navigate to the "WhatsApp for Business" section
3. Click the bookmarklet you created
4. A popup will appear with all the scraped URLs
5. Copy the JSON output

---

## 📋 How to Collect ALL 95+ Article URLs

### The 6 categories are just headers - you need to scrape each one separately:

#### **Step-by-Step Process:**

1. **Setting up an account**
   - Go to: https://faq.whatsapp.com
   - Click on "Setting up an account" 
   - Run the scraper code (paste in console)
   - Copy the JSON output → Save as `setting_up_account.json`

2. **Connecting with customers**
   - Click on "Connecting with customers"
   - Run the scraper code
   - Copy the JSON output → Save as `connecting_with_customers.json`

3. **Selling products and services**
   - Click on "Selling products and services"
   - Run the scraper code
   - Copy the JSON output → Save as `selling_products.json`

4. **Troubleshooting**
   - Click on "Troubleshooting"
   - Run the scraper code
   - Copy the JSON output → Save as `troubleshooting.json`

5. **WhatsApp Premium features**
   - Click on "WhatsApp Premium features"
   - Run the scraper code
   - Copy the JSON output → Save as `premium_features.json`

6. **WhatsApp Business Platform**
   - Click on "WhatsApp Business Platform"
   - Run the scraper code
   - Copy the JSON output → Save as `business_platform.json`

---

### 🔧 The Scraper Code (use this on EACH category page):

```
var a=[],s=new Set();document.querySelectorAll('a').forEach(function(l){var h=l.href,t=l.textContent.trim();if(h&&h.indexOf('faq.whatsapp.com')>-1&&h.indexOf('#')===-1&&t&&t.length>3&&!s.has(h)){s.add(h);a.push({title:t,url:h});}});var o=JSON.stringify({scraped_articles:a,total:a.length,from:window.location.href,at:new Date().toISOString()},null,2);var p=window.open('','URLs','width=800,height=600');p.document.write('<html><head><title>Found '+a.length+' URLs</title><style>body{font-family:Arial;padding:20px}textarea{width:100%;height:400px;font-family:monospace;font-size:12px;padding:10px}button{margin:10px 5px 0 0;padding:12px 24px;background:#25D366;color:#fff;border:0;border-radius:4px;cursor:pointer}button:hover{background:#128C7E}</style></head><body><h2>Found '+a.length+' URLs</h2><textarea id="o">'+o+'</textarea><br><button onclick="document.getElementById(\'o\').select();document.execCommand(\'copy\');alert(\'Copied!\')">Copy</button><button onclick="var b=new Blob([document.getElementById(\'o\').value],{type:\'application/json\'});var u=URL.createObjectURL(b);var d=document.createElement(\'a\');d.href=u;d.download=\'urls.json\';d.click()">Download</button></body></html>');console.log('Found',a.length,'URLs');
```

---

### 📝 After You've Scraped All 6 Categories:

Send me the JSON outputs or paste them here, and I'll help you organize them into one master file with all 95+ articles properly categorized!

---

## Alternative: Prettier Bookmarklet Code (same functionality, easier to read)

If you want to see what the code does, here's the formatted version:

```javascript
javascript:(function() {
  const articles = [];
  const links = document.querySelectorAll('a[href*="faq.whatsapp.com"]');
  const seen = new Set();

  links.forEach(link => {
    const href = link.href;
    const text = link.textContent.trim();

    if (text && href && !seen.has(href) && text.length > 5) {
      seen.add(href);
      articles.push({
        title: text,
        url: href
      });
    }
  });

  const output = JSON.stringify({
    scraped_articles: articles,
    total: articles.length,
    scraped_from: window.location.href,
    scraped_at: new Date().toISOString()
  }, null, 2);

  // Create popup window with results
  const width = 600;
  const height = 400;
  const left = (screen.width - width) / 2;
  const top = (screen.height - height) / 2;
  const popup = window.open('', 'Scraped URLs', `width=${width},height=${height},left=${left},top=${top}`);

  popup.document.write(`
    <html>
      <head>
        <title>Scraped URLs</title>
        <style>
          body { font-family: monospace; padding: 20px; background: #f5f5f5; }
          textarea { width: 100%; height: 300px; padding: 10px; border: 1px solid #ccc; font-family: monospace; font-size: 12px; }
          button { margin: 10px 5px; padding: 10px 20px; font-size: 14px; cursor: pointer; background: #25D366; color: white; border: none; border-radius: 4px; }
          button:hover { background: #128C7E; }
        </style>
      </head>
      <body>
        <h2>Scraped ${articles.length} URLs</h2>
        <textarea id="output" readonly>${output}</textarea>
        <br>
        <button onclick="document.getElementById('output').select();document.execCommand('copy');alert('Copied to clipboard!');">
          Copy to Clipboard
        </button>
        <button onclick="const blob=new Blob([document.getElementById('output').value],{type:'application/json'});const url=URL.createObjectURL(blob);const a=document.createElement('a');a.href=url;a.download='whatsapp_help_urls.json';a.click();">
          Download JSON
        </button>
      </body>
    </html>
  `);
})();
```

---

## Instructions for Each Category

### 1. Setting up an account
- Go to: https://faq.whatsapp.com
- Navigate to "WhatsApp for Business" → "Setting up an account"
- Click the bookmarklet
- Save the JSON output

### 2. Connecting with customers
- Go to: https://faq.whatsapp.com
- Navigate to "WhatsApp for Business" → "Connecting with customers"
- Click the bookmarklet
- Save the JSON output

### 3. Selling products and services
- Go to: https://faq.whatsapp.com
- Navigate to "WhatsApp for Business" → "Selling products and services"
- Click the bookmarklet
- Save the JSON output

### 4. Troubleshooting
- Go to: https://faq.whatsapp.com
- Navigate to "WhatsApp for Business" → "Troubleshooting"
- Click the bookmarklet
- Save the JSON output

### 5. WhatsApp Premium features
- Go to: https://faq.whatsapp.com
- Navigate to "WhatsApp for Business" → "WhatsApp Premium features"
- Click the bookmarklet
- Save the JSON output

### 6. WhatsApp Business Platform
- Go to: https://faq.whatsapp.com
- Navigate to "WhatsApp for Business" → "WhatsApp Business Platform"
- Click the bookmarklet
- Save the JSON output

---

## What to Do With the Output

1. After scraping each category, save the JSON output
2. Paste all the outputs into: `/ml-training/outputs/help_articles_raw.json`
3. Or send me the JSON and I'll organize it into the proper format

---

## Tips

- The bookmarklet works best when you're on a page showing all articles in a category
- It will capture ALL links to faq.whatsapp.com on the current page
- You can click it multiple times as you navigate different sections
- The popup has two buttons:
  - **Copy to Clipboard** - Quick copy
  - **Download JSON** - Saves as a file

---

## Troubleshooting

**If the bookmarklet doesn't work:**
1. Make sure you copied the entire code (starts with `javascript:` and ends with `);`)
2. Some browsers block `javascript:` in bookmarks - try using the browser console instead:
   - Press F12 to open DevTools
   - Go to Console tab
   - Paste the code (without `javascript:` prefix)
   - Press Enter

**If you get a security error:**
- Open the browser console (F12)
- Copy and paste the code there instead
- It will work the same way

---

## Example Output

```json
{
  "scraped_articles": [
    {
      "title": "About WhatsApp Business app",
      "url": "https://faq.whatsapp.com/528777672705334"
    },
    {
      "title": "Download and install WhatsApp Business",
      "url": "https://faq.whatsapp.com/536562317457992"
    }
  ],
  "total": 2,
  "scraped_from": "https://faq.whatsapp.com",
  "scraped_at": "2025-11-17T21:50:00.000Z"
}
```
