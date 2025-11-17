#!/usr/bin/env python3
"""
Create clean training data for upload - removes all metadata fields.
Only keeps the standard format: messages with role and content.
"""

import json

def clean_training_data(input_file, output_file):
    """
    Remove all extra fields from training data.
    Keep only: role and content in each message.
    """

    print(f"🧹 Cleaning training data for upload...\n")

    cleaned_count = 0

    with open(input_file, 'r', encoding='utf-8') as f_in:
        with open(output_file, 'w', encoding='utf-8') as f_out:
            for line in f_in:
                example = json.loads(line.strip())

                # Create clean example with only messages
                clean_example = {"messages": []}

                for msg in example.get('messages', []):
                    # Only keep role and content
                    clean_msg = {
                        "role": msg["role"],
                        "content": msg["content"]
                    }
                    clean_example["messages"].append(clean_msg)

                # Write clean example
                f_out.write(json.dumps(clean_example, ensure_ascii=False) + '\n')
                cleaned_count += 1

    print(f"✅ Complete!")
    print(f"   Cleaned {cleaned_count} examples")
    print(f"\n📁 Output: {output_file}")
    print(f"\n✨ Ready for upload!")

def main():
    input_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/BALANCED_WITH_URLS.jsonl"
    output_file = "/Users/georgiadavis/Developer/WA Business AI/WA-business/business-assistant/ml-training/data/training/BALANCED_CLEAN_FOR_UPLOAD.jsonl"

    clean_training_data(input_file, output_file)

    # Show sample
    print(f"\n📋 Sample (first line):")
    with open(output_file, 'r') as f:
        sample = json.loads(f.readline())
        print(json.dumps(sample, indent=2))

if __name__ == "__main__":
    main()
