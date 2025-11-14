# 📤 Training Data Files - FINAL

## ✅ File to Upload to Llama API

**`UPLOAD_TO_LLAMA_API__6903_examples__intent_classification_and_help_articles__with_wa_focused_system_prompt.jsonl`**

### File Details:
- **Size:** 19 MB
- **Total Examples:** 6,903
- **Format:** Clean JSONL with only `{"messages": [...]}` - no metadata
- **System Prompt:** WhatsApp-focused system prompt that ensures all responses are contextual to WhatsApp Business

### What's Inside:

1. **Intent Classification & Reasoning (6,872 examples)**
   - Examples teaching the model to classify user intents based on conversation reasoning
   - Examples teaching the model to generate reasoning from intent classifications
   - Covers intents like: Labels, Catalog, Advertise, Messages, Automation, Analytics, Payment, Business_Profile, Development

2. **Help Center Articles (38 examples)**
   - Q&A format articles about WhatsApp Business features
   - Topics covered:
     - Labels (organizing customers)
     - Catalog (product showcase)
     - Automation (quick replies, greeting messages, away messages)
     - Business Profile (setup, verification, short links)
     - Analytics/Statistics
     - Messages (broadcasts, delivery status)
     - Payments
     - Business Development
     - **Verification** (green checkmark, requirements, API vs App)

### System Prompt Features:

The training data includes a system prompt that instructs the model to:
- **Always** provide WhatsApp Business-specific answers
- Frame general business questions in terms of WhatsApp Business features
- Proactively suggest relevant features (ads, broadcasts, catalog, labels, etc.)
- Be built into the WhatsApp Business app context

Example behaviors trained:
- "How do I get more customers?" → Suggests Click-to-WhatsApp ads, sharing WA Business link, creating catalog
- "How do I organize my work?" → Suggests using labels, away messages, quick replies
- "How do I increase sales?" → Suggests setting up catalog, using broadcast lists, enabling quick replies

---

## 📋 Reference File (Not for Upload)

**`training_data_help_manual.jsonl`**
- **Size:** 27 KB
- **Examples:** 38 help center articles
- This is just the source help articles file
- Already included in the main upload file above

---

## 🎯 Upload Instructions

1. Go to llama-api.com fine-tuning section
2. Upload: `UPLOAD_TO_LLAMA_API__6903_examples__intent_classification_and_help_articles__with_wa_focused_system_prompt.jsonl`
3. Configure:
   - Base model: `llama3.1-8b`
   - Epochs: 3
   - Learning rate: 2e-5
   - Batch size: 4
4. Start fine-tuning

## ✅ Validation Status

- ✓ All 6,903 examples validated
- ✓ No metadata fields (clean format)
- ✓ Proper JSON structure
- ✓ WhatsApp-focused system prompt included
- ✓ Ready for llama-api.com upload

---

**Last Updated:** 2025-11-13
