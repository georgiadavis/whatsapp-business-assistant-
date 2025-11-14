"""
Clean all metadata from training file for llama API
"""

import json
from pathlib import Path

def clean_metadata(input_file, output_file):
    """Remove all metadata fields from JSONL file."""

    input_path = Path(input_file)
    output_path = Path(output_file)

    print(f"Cleaning file: {input_file}")

    count = 0
    cleaned = 0
    errors = 0

    with open(input_path, 'r', encoding='utf-8') as infile, \
         open(output_path, 'w', encoding='utf-8') as outfile:

        for line_num, line in enumerate(infile, 1):
            try:
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

            except Exception as e:
                print(f"Error on line {line_num}: {e}")
                errors += 1

    print(f"\n✓ Processed {count} examples")
    print(f"✓ Removed metadata from {cleaned} examples")
    if errors > 0:
        print(f"⚠️  Errors: {errors}")
    print(f"✓ Output: {output_file}")

if __name__ == "__main__":
    clean_metadata(
        '../data/training/complete_with_updated_prompts.jsonl',
        '../data/training/llama_api_ready.jsonl'
    )
