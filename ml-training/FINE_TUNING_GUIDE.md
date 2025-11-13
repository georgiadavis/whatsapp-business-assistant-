# 🚀 Fine-Tuning Setup Guide

This guide will walk you through fine-tuning your Llama model for the WhatsApp Business Assistant.

## 📋 Prerequisites

✅ Python 3.9+ installed
✅ Training data prepared (6,903 examples)
✅ Llama API key from your provider
✅ Required packages installed

## 🔧 Step 1: Install Required Packages

Already done! ✅
- `llama-stack-client`
- `python-dotenv`
- `requests`
- `beautifulsoup4`

## 🔐 Step 2: Set Up Your API Key (IMPORTANT!)

### DO NOT share your API key in chat! Follow these steps:

1. **Copy the example environment file:**
   ```bash
   cd ml-training
   cp .env.example .env
   ```

2. **Edit the `.env` file with your API key:**
   ```bash
   # Open .env in your text editor
   code .env
   # or
   nano .env
   ```

3. **Replace `your_api_key_here` with your actual Llama API key:**
   ```env
   LLAMA_API_KEY=sk-your-actual-api-key-here
   ```

4. **Save the file**

The `.env` file is protected by `.gitignore` and will NOT be committed to version control. ✅

## 📊 Step 3: Verify Your Training Data

Your training data is ready:
- **Location:** `data/training/training_data_complete.jsonl`
- **Total Examples:** 6,903
- **Format:** Chat-style messages for Llama

### Data Breakdown:
- Intent classification: 3,436 examples
- Reasoning generation: 3,436 examples
- Help article Q&A: 31 examples

## 🎯 Step 4: Configure Fine-Tuning (Optional)

Edit `.env` to customize training parameters:

```env
# Model Configuration
MODEL_NAME=llama-3.1-8b
OUTPUT_MODEL_NAME=whatsapp-business-assistant-v1

# Training Hyperparameters
LEARNING_RATE=2e-5      # Lower = more stable, Higher = faster
BATCH_SIZE=4            # Higher = faster but needs more memory
NUM_EPOCHS=3            # More epochs = more training
MAX_SEQ_LENGTH=2048     # Maximum tokens per example
```

**Recommended settings (already set):**
- Learning rate: `2e-5` (good balance)
- Batch size: `4` (works on most GPUs)
- Epochs: `3` (prevents overfitting)

## 🚀 Step 5: Start Fine-Tuning

Once your `.env` file is configured with your API key:

```bash
cd ml-training/scripts
/usr/bin/python3 fine_tune_llama.py
```

### What happens:
1. ✅ Validates your training data format
2. ✅ Uploads data to Llama Stack
3. ✅ Starts the fine-tuning job
4. ✅ Saves job details to `outputs/fine_tuning_job.json`

## 📊 Step 6: Monitor Training Progress

### Check status anytime:

```bash
cd ml-training/scripts
/usr/bin/python3 check_fine_tuning_status.py
```

Or with a specific job ID:

```bash
/usr/bin/python3 check_fine_tuning_status.py job-abc123
```

### Status meanings:
- **pending** ⏳ - Job is queued
- **running** ⏳ - Training in progress
- **succeeded** 🎉 - Training complete!
- **failed** ❌ - Something went wrong

## ⏰ How Long Does It Take?

Fine-tuning time depends on:
- **Dataset size:** 6,903 examples
- **Model size:** Llama 3.1 8B
- **Epochs:** 3

**Estimated time:** 2-6 hours (varies by provider)

You'll receive an email notification when training completes.

## 🎉 Step 7: Use Your Fine-Tuned Model

Once training succeeds, you'll get a model ID like:
```
whatsapp-business-assistant-v1:ft-abc123
```

Use this model ID for inference in your Android app!

## 🔍 Troubleshooting

### Error: "API key not set"
- Make sure you created `.env` from `.env.example`
- Verify your API key is correct in `.env`
- Don't include quotes around the API key

### Error: "Training file not found"
- Run from `ml-training/scripts/` directory
- Check that `data/training/training_data_complete.jsonl` exists

### Error: "Invalid JSON format"
- Your training data might be corrupted
- Re-run `merge_training_data.py` to regenerate

### Training failed
- Check error message in status output
- Verify training data format
- Check API key permissions
- Contact Llama support if issue persists

## 📁 File Structure

```
ml-training/
├── .env                     ⚠️  YOUR API KEY (keep secret!)
├── .env.example             📋 Template
├── scripts/
│   ├── fine_tune_llama.py   🚀 Main fine-tuning script
│   └── check_fine_tuning_status.py  📊 Status checker
├── data/
│   └── training/
│       └── training_data_complete.jsonl  📚 Your data
└── outputs/
    └── fine_tuning_job.json  💾 Job info
```

## 🛡️ Security Best Practices

✅ Never commit `.env` to version control
✅ Never share API keys in chat or code
✅ Store API keys in environment variables
✅ Use `.env.example` for templates only
✅ Rotate API keys periodically

## 📚 Next Steps After Fine-Tuning

1. ✅ Test your fine-tuned model
2. ✅ Integrate into Android app
3. ✅ Deploy to production
4. ✅ Monitor performance
5. ✅ Collect feedback and iterate

## 💡 Tips for Better Results

- **More data = better results:** Consider adding more help articles
- **Quality > Quantity:** Focus on accurate, helpful responses
- **Balanced dataset:** Ensure all intent categories are represented
- **Test thoroughly:** Validate model responses before production
- **Iterate:** Fine-tune multiple times with different data mixes

## 🆘 Need Help?

- Check Llama Stack documentation
- Review error messages carefully
- Test with smaller dataset first
- Join Llama community forums

---

**Ready to start?** Make sure your `.env` file is configured and run:

```bash
cd ml-training/scripts
/usr/bin/python3 fine_tune_llama.py
```

Good luck! 🚀
