import csv
import json
import sys

def convert_csv_to_jsonl(input_csv, output_jsonl, format_type="chat"):
    """
    Convert CSV to JSONL for Llama fine-tuning.

    Args:
        input_csv: Path to input CSV file
        output_jsonl: Path to output JSONL file
        format_type: Type of format to use:
            - "chat": Chat format with messages array
            - "instruction": Instruction/input/output format
            - "reasoning_to_intent": Generate intent from reasoning
            - "intent_to_reasoning": Generate reasoning from intent
    """

    with open(input_csv, 'r', encoding='utf-8') as csv_file, \
         open(output_jsonl, 'w', encoding='utf-8') as jsonl_file:

        reader = csv.DictReader(csv_file)
        count = 0
        skipped = 0

        for row in reader:
            # Extract relevant fields
            intent_thoughts = row.get('intent_thoughts', '').strip()
            intent_str = row.get('intent_str', '').strip()
            simplified_intent = row.get('simplified_intent_str', '').strip()
            country = row.get('country', '').strip()
            turn_bucket = row.get('turn_bucket', '').strip()

            # Skip rows with missing critical data
            if not intent_thoughts and not intent_str:
                skipped += 1
                continue

            # Generate JSONL entry based on format type
            if format_type == "chat":
                # Chat format: reasoning -> intent classification
                entry = {
                    "messages": [
                        {
                            "role": "system",
                            "content": "You are an intent classification assistant for WhatsApp Business conversations. Classify user intents based on the reasoning provided."
                        },
                        {
                            "role": "user",
                            "content": f"Analyze this reasoning and provide the intent classification:\n\n{intent_thoughts}"
                        },
                        {
                            "role": "assistant",
                            "content": f"Intent: {intent_str}\nSimplified: {simplified_intent}"
                        }
                    ]
                }

            elif format_type == "chat_reverse":
                # Chat format: intent -> reasoning
                entry = {
                    "messages": [
                        {
                            "role": "system",
                            "content": "You are an intent reasoning assistant for WhatsApp Business conversations. Generate detailed reasoning for intent classifications."
                        },
                        {
                            "role": "user",
                            "content": f"Explain the reasoning for this intent classification:\nIntent: {intent_str}\nSimplified: {simplified_intent}"
                        },
                        {
                            "role": "assistant",
                            "content": intent_thoughts
                        }
                    ]
                }

            elif format_type == "instruction":
                # Instruction format
                entry = {
                    "instruction": "Classify the intent based on the reasoning provided.",
                    "input": intent_thoughts,
                    "output": f"Intent: {intent_str}\nSimplified: {simplified_intent}"
                }

            elif format_type == "reasoning_to_intent":
                # Simple format: reasoning -> intent
                entry = {
                    "text": f"### Reasoning:\n{intent_thoughts}\n\n### Intent:\n{intent_str}"
                }

            elif format_type == "intent_to_reasoning":
                # Simple format: intent -> reasoning
                entry = {
                    "text": f"### Intent:\n{intent_str}\n\n### Reasoning:\n{intent_thoughts}"
                }

            else:
                print(f"Unknown format type: {format_type}")
                sys.exit(1)

            # Write to JSONL file
            jsonl_file.write(json.dumps(entry, ensure_ascii=False) + '\n')
            count += 1

        print(f"Conversion complete!")
        print(f"- Converted: {count} rows")
        print(f"- Skipped: {skipped} rows (missing data)")
        print(f"- Output: {output_jsonl}")

def create_multitask_jsonl(input_csv, output_jsonl):
    """
    Create a multi-task training dataset that includes both:
    1. Intent classification from reasoning
    2. Reasoning generation from intent

    This creates 2 examples per row for better model training.
    """

    with open(input_csv, 'r', encoding='utf-8') as csv_file, \
         open(output_jsonl, 'w', encoding='utf-8') as jsonl_file:

        reader = csv.DictReader(csv_file)
        count = 0
        skipped = 0

        for row in reader:
            # Extract relevant fields
            intent_thoughts = row.get('intent_thoughts', '').strip()
            intent_str = row.get('intent_str', '').strip()
            simplified_intent = row.get('simplified_intent_str', '').strip()

            # Skip rows with missing critical data
            if not intent_thoughts or not intent_str:
                skipped += 1
                continue

            # Task 1: Intent Classification (reasoning -> intent)
            classification_entry = {
                "messages": [
                    {
                        "role": "system",
                        "content": "You are an intent classification assistant for WhatsApp Business conversations. Analyze the reasoning and provide the intent classification."
                    },
                    {
                        "role": "user",
                        "content": f"Based on this analysis, what is the intent classification?\n\n{intent_thoughts}"
                    },
                    {
                        "role": "assistant",
                        "content": f"Intent: {intent_str}\nSimplified Category: {simplified_intent}"
                    }
                ]
            }
            jsonl_file.write(json.dumps(classification_entry, ensure_ascii=False) + '\n')
            count += 1

            # Task 2: Reasoning Generation (intent -> reasoning)
            reasoning_entry = {
                "messages": [
                    {
                        "role": "system",
                        "content": "You are an intent reasoning assistant for WhatsApp Business conversations. Generate detailed reasoning for intent classifications."
                    },
                    {
                        "role": "user",
                        "content": f"Explain why a conversation would be classified as:\nIntent: {intent_str}\nCategory: {simplified_intent}"
                    },
                    {
                        "role": "assistant",
                        "content": intent_thoughts
                    }
                ]
            }
            jsonl_file.write(json.dumps(reasoning_entry, ensure_ascii=False) + '\n')
            count += 1

        print(f"Multi-task conversion complete!")
        print(f"- Total examples created: {count}")
        print(f"- Classification examples: {count // 2}")
        print(f"- Reasoning examples: {count // 2}")
        print(f"- Skipped rows: {skipped} (missing data)")
        print(f"- Output: {output_jsonl}")

if __name__ == "__main__":
    # Configuration
    input_csv = "../data/raw/WA intent raw_no_chats - raw_LLM_predictions.csv"

    # Choose your training approach:
    # Single-task or Multi-task

    training_mode = "multitask"  # Change to "single" for single-task training

    if training_mode == "multitask":
        print("Creating MULTI-TASK training dataset...")
        print("This will train the model on BOTH:")
        print("  1. Intent classification from reasoning")
        print("  2. Reasoning generation from intent")
        print()
        output_jsonl = "../data/training/training_data_multitask.jsonl"
        create_multitask_jsonl(input_csv, output_jsonl)

    else:
        # Single-task training
        # Choose your format:
        # "chat" - Chat format with system/user/assistant messages (reasoning -> intent)
        # "chat_reverse" - Chat format (intent -> reasoning)
        # "instruction" - Instruction/input/output format
        # "reasoning_to_intent" - Simple text format (reasoning -> intent)
        # "intent_to_reasoning" - Simple text format (intent -> reasoning)

        format_type = "chat"  # Change this to your preferred format
        output_jsonl = f"../data/training/training_data_{format_type}.jsonl"

        print(f"Creating SINGLE-TASK training dataset...")
        print(f"Format: {format_type}")
        print(f"Input: {input_csv}")
        print()

        convert_csv_to_jsonl(input_csv, output_jsonl, format_type)
