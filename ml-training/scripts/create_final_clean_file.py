"""
Create final clean JSONL file for llama API
Removes ALL metadata and ensures only {"messages": [...]} format
"""

import json
from pathlib import Path

def create_clean_file(input_file, output_file):
    """Create 100% clean file with no metadata."""

    input_path = Path(input_file)
    output_path = Path(output_file)

    print(f"Creating clean file from: {input_file}")

    count = 0
    cleaned = 0

    with open(input_path, 'r', encoding='utf-8') as infile, \
         open(output_path, 'w', encoding='utf-8') as outfile:

        for line_num, line in enumerate(infile, 1):
            try:
                data = json.loads(line)

                # Create ONLY messages key, removing everything else
                clean_data = {"messages": data["messages"]}

                # Count if we removed metadata
                if 'metadata' in data:
                    cleaned += 1

                # Write clean JSON
                outfile.write(json.dumps(clean_data, ensure_ascii=False) + '\n')
                count += 1

            except Exception as e:
                print(f"❌ Error on line {line_num}: {e}")

    print(f"\n✓ Processed {count} examples")
    print(f"✓ Removed metadata from {cleaned} examples")
    print(f"✓ Output: {output_file}")

    return count

if __name__ == "__main__":
    print("=" * 70)
    print("Creating Final Clean File for Llama API")
    print("=" * 70)

    count = create_clean_file(
        '../data/training/llama_api_ready_v2.jsonl',
        '../data/training/final_training_data.jsonl'
    )

    print("\n" + "=" * 70)
    print("✓ Final Training File Ready!")
    print("=" * 70)
    print(f"\nFile: final_training_data.jsonl")
    print(f"Examples: {count:,}")
    print("\n📤 Upload this file to llama-api.com")
