"""
Merge updated training files with improved system prompts
"""

import json
from pathlib import Path

def merge_updated_files(output_file):
    """Merge all updated training files into one complete file."""
    
    print("=" * 70)
    print("Merging Updated Training Files")
    print("=" * 70)
    
    # Files to merge (in order)
    files_to_merge = [
        ('../data/training/intent_classification_reasoning_updated.jsonl', 'Intent Classification & Reasoning'),
        ('../data/training/help_center_articles_updated.jsonl', 'Help Center Articles'),
    ]
    
    output_path = Path(output_file)
    total_count = 0
    
    with open(output_path, 'w', encoding='utf-8') as outfile:
        for file_path, description in files_to_merge:
            path = Path(file_path)
            
            if not path.exists():
                print(f"⚠️  File not found: {file_path}")
                continue
            
            count = 0
            with open(path, 'r', encoding='utf-8') as infile:
                for line in infile:
                    outfile.write(line)
                    count += 1
            
            total_count += count
            print(f"✓ Added {count:,} examples from {description}")
    
    print("\n" + "=" * 70)
    print("✓ Merge Complete!")
    print("=" * 70)
    print(f"\nTotal examples: {total_count:,}")
    print(f"Output file: {output_path.name}")
    print(f"\nThis file is ready for fine-tuning with the improved system prompt!")
    
    return total_count

if __name__ == "__main__":
    output_file = '../data/training/complete_with_updated_prompts.jsonl'
    merge_updated_files(output_file)
