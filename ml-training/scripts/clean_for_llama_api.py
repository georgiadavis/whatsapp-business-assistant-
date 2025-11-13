"""
Remove metadata fields from training data to match Llama API format.
Converts from:
  {"messages": [...], "metadata": {...}}
To:
  {"messages": [...]}
"""

import json
from pathlib import Path

def clean_training_data(input_file, output_file):
    """Remove metadata fields from JSONL training data."""

    input_path = Path(input_file)
    output_path = Path(output_file)

    if not input_path.exists():
        print(f"❌ Input file not found: {input_file}")
        return

    print(f"Cleaning training data...")
    print(f"Input: {input_file}")
    print(f"Output: {output_file}")
    print()

    count = 0
    cleaned = 0

    with open(input_path, 'r', encoding='utf-8') as infile, \
         open(output_path, 'w', encoding='utf-8') as outfile:

        for line in infile:
            data = json.loads(line)

            # Remove metadata if present
            if 'metadata' in data:
                del data['metadata']
                cleaned += 1

            # Keep only messages field
            clean_data = {"messages": data["messages"]}

            # Write clean data
            outfile.write(json.dumps(clean_data, ensure_ascii=False) + '\n')
            count += 1

    print(f"✓ Processed {count} examples")
    print(f"✓ Removed metadata from {cleaned} examples")
    print(f"✓ Output saved to: {output_file}")
    print()
    print("Your training data is now ready for Llama API!")

if __name__ == "__main__":
    clean_training_data(
        '../data/training/training_data_complete.jsonl',
        '../data/training/training_data_llama_api.jsonl'
    )
