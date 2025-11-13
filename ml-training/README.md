# ML Training Pipeline for WhatsApp Business Assistant

This directory contains all the machine learning training data and scripts for fine-tuning a Llama model to assist with WhatsApp Business conversations.

## Directory Structure

```
ml-training/
├── data/
│   ├── raw/                    # Raw, unprocessed data
│   │   └── WA intent raw_no_chats - raw_LLM_predictions.csv
│   ├── processed/              # Cleaned and processed data
│   │   └── help_articles_manual.json
│   └── training/               # Training-ready JSONL files
│       ├── training_data_complete.jsonl        # 🎯 MAIN TRAINING FILE
│       ├── training_data_multitask.jsonl       # Intent + reasoning examples
│       └── training_data_help_manual.jsonl     # Help article Q&A
├── scripts/                    # Data processing scripts
│   ├── csv_to_jsonl.py        # Convert CSV intent data to JSONL
│   ├── merge_training_data.py # Merge multiple datasets
│   ├── scrape_help_articles.py # Scrape WhatsApp help center
│   └── test_llama.py          # Test llama client installation
└── outputs/                    # Generated files and logs
    └── help_articles_raw.json

```

## Training Data Overview

### 📊 Total Training Examples: **6,877**

**Breakdown:**
- **6,872 examples** from intent classification CSV:
  - 3,436 intent classification examples (reasoning → intent)
  - 3,436 reasoning generation examples (intent → reasoning)
- **5 examples** from WhatsApp Business help articles (Q&A format)

### Data Format

All training data uses the chat format compatible with Llama fine-tuning:

```json
{
  "messages": [
    {
      "role": "system",
      "content": "You are an intent classification assistant..."
    },
    {
      "role": "user",
      "content": "User query or input..."
    },
    {
      "role": "assistant",
      "content": "Model response..."
    }
  ],
  "metadata": {
    "source": "data_source",
    "category": "intent_category"
  }
}
```

## Usage

### 1. Generate Training Data from CSV

Convert the intent classification CSV to JSONL format:

```bash
cd scripts
/usr/bin/python3 csv_to_jsonl.py
```

**Configuration options:**
- `training_mode = "multitask"` - Train on both classification and reasoning (recommended)
- `training_mode = "single"` - Train on single task only

### 2. Add Help Articles

Edit `data/processed/help_articles_manual.json` to add more WhatsApp Business help content:

```json
[
  {
    "title": "How to use labels",
    "category": "Labels",
    "content": "Labels help you organize...",
    "intent": "Labels"
  }
]
```

### 3. Merge All Datasets

Combine all training sources into one file:

```bash
cd scripts
/usr/bin/python3 merge_training_data.py
```

This creates `data/training/training_data_complete.jsonl` - your main training file.

### 4. Scrape Help Articles (Optional)

Attempt to scrape WhatsApp help center articles:

```bash
cd scripts
/usr/bin/python3 scrape_help_articles.py
```

**Note:** Due to JavaScript rendering, this may not extract content properly. Manual entry in `help_articles_manual.json` is recommended.

## Training Tasks

The model is trained on multiple tasks:

### 1. Intent Classification
Given reasoning about a conversation, classify the user's intent.

**Example:**
- **Input:** "The user is asking about organizing customers..."
- **Output:** "Intent: Labels, Category: Business: Not Advertise"

### 2. Reasoning Generation
Given an intent classification, explain the reasoning.

**Example:**
- **Input:** "Intent: Catalog, Category: Business: Not Advertise"
- **Output:** "The conversation involves showcasing products..."

### 3. Help Article Q&A
Answer questions about WhatsApp Business features.

**Example:**
- **Input:** "How do I create a product catalog?"
- **Output:** "The catalog feature allows you to showcase..."

## Intent Categories

The model recognizes these intent categories:

- **Labels** - Organizing chats and customers
- **Catalog** - Product showcase and management
- **Advertise** - Marketing and ads
- **Messages** - Messaging features
- **Automation** - Quick replies, greetings, away messages
- **Analytics** - Statistics and insights
- **Payment** - Payment features
- **Business_Profile** - Business profile setup
- **Development** - Business growth guidance
- **Not_related_to_business** - Non-business conversations
- **Unsure** - Unclear intent

## Adding More Training Data

### Option 1: Add More CSV Data
1. Place new CSV files in `data/raw/`
2. Update `csv_to_jsonl.py` to process the new file
3. Run the conversion script

### Option 2: Add Manual Help Articles
1. Edit `data/processed/help_articles_manual.json`
2. Add new article objects with title, content, category, intent
3. Run `merge_training_data.py`

### Option 3: Add Custom JSONL
1. Create a new JSONL file in `data/training/`
2. Add it to the `datasets` list in `merge_training_data.py`
3. Run the merge script

## File Formats

### CSV Input Format
- **intent_thoughts** - Reasoning about the intent
- **intent_str** - Intent classification
- **simplified_intent_str** - Simplified category

### Help Articles JSON Format
```json
{
  "title": "Article title",
  "category": "Category name",
  "content": "Article content",
  "intent": "Intent category"
}
```

### JSONL Output Format
Each line is a JSON object with chat messages for training.

## Next Steps

1. ✅ Training data prepared
2. 🔄 Fine-tune Llama model using `training_data_complete.jsonl`
3. 🧪 Test fine-tuned model
4. 🚀 Deploy to production

## Dependencies

```bash
pip install requests beautifulsoup4 llama-stack-client
```

## Tips

- **Multi-task training** improves model versatility
- **Add domain-specific examples** for better performance
- **Balance your dataset** - ensure all intents are represented
- **Validate data quality** before training
- **Regular updates** - add new examples as you discover edge cases

## Troubleshooting

**Q: Web scraper returns empty content?**
A: WhatsApp help center uses JavaScript. Add articles manually to `help_articles_manual.json`.

**Q: How do I add more intent categories?**
A: Update the `map_article_to_intent()` function in the scripts and add examples to your CSV.

**Q: Can I use this for other languages?**
A: Yes! Just ensure your training data includes examples in those languages.

---

**Last Updated:** 2025-01-13
**Model Target:** Llama 3.x for WhatsApp Business Assistant
