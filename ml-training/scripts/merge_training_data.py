import json

def convert_manual_articles_to_jsonl(input_json, output_jsonl):
    """
    Convert manually created help articles to JSONL training format.
    """
    with open(input_json, 'r', encoding='utf-8') as f:
        articles = json.load(f)

    with open(output_jsonl, 'w', encoding='utf-8') as f:
        for article in articles:
            title = article['title']
            content = article['content']
            category = article.get('category', 'General')
            intent = article.get('intent', 'General')

            # Create training entry
            entry = {
                "messages": [
                    {
                        "role": "system",
                        "content": "You are a helpful WhatsApp Business assistant. Provide accurate, helpful information about WhatsApp Business features."
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
                    "source": "whatsapp_help_manual",
                    "category": category,
                    "intent": intent
                }
            }
            f.write(json.dumps(entry, ensure_ascii=False) + '\n')

    print(f"Converted {len(articles)} manual articles to JSONL")
    print(f"Output: {output_jsonl}")

def merge_training_datasets(datasets, output_file):
    """
    Merge multiple JSONL training files into one.

    Args:
        datasets: List of tuples (file_path, description)
        output_file: Output JSONL file path
    """
    total_count = 0

    with open(output_file, 'w', encoding='utf-8') as outfile:
        for file_path, description in datasets:
            try:
                with open(file_path, 'r', encoding='utf-8') as infile:
                    count = 0
                    for line in infile:
                        outfile.write(line)
                        count += 1
                    total_count += count
                    print(f"✓ Added {count} examples from {description}")
            except FileNotFoundError:
                print(f"✗ File not found: {file_path} ({description})")
            except Exception as e:
                print(f"✗ Error processing {file_path}: {str(e)}")

    print(f"\nTotal examples in merged dataset: {total_count}")
    print(f"Output: {output_file}")

if __name__ == "__main__":
    print("=== Converting Manual Help Articles ===\n")
    convert_manual_articles_to_jsonl(
        '../data/processed/help_articles_manual.json',
        '../data/training/training_data_help_manual.jsonl'
    )

    print("\n=== Merging All Training Datasets ===\n")

    # List all your training data sources
    datasets = [
        ('../data/training/training_data_multitask.jsonl', 'Intent Classification & Reasoning (CSV)'),
        ('../data/training/training_data_help_manual.jsonl', 'WhatsApp Business Help Articles'),
        # Add more datasets here as you create them
    ]

    merge_training_datasets(datasets, '../data/training/training_data_complete.jsonl')

    print("\n=== Summary ===")
    print("Your complete training dataset is ready!")
    print("File: training_data_complete.jsonl")
    print("\nThis dataset includes:")
    print("  1. Intent classification examples (from CSV)")
    print("  2. Reasoning generation examples (from CSV)")
    print("  3. Help article Q&A examples (manual)")
    print("\nYou can now use this for fine-tuning your Llama model!")
