package com.example.chatapp.data.repository

import com.example.chatapp.data.remote.ChatCompletionRequest
import com.example.chatapp.data.remote.ChatCompletionResponse
import com.example.chatapp.data.remote.ChatMessage
import com.example.chatapp.data.remote.LlamaApiService
import javax.inject.Inject

class LlamaRepository @Inject constructor(
    private val llamaApiService: LlamaApiService,
    private val apiKey: String
) {
    companion object {
        private const val MODEL_NAME = "Llama-3.3-70B-Instruct"
        private val SYSTEM_PROMPT = """
# WhatsApp Business Assistant System Prompt

You are a specialized WhatsApp Business assistant designed to help users with WhatsApp Business app-related questions and features.

## CRITICAL: Response Length
Keep responses SHORT and CONCISE. Aim for 2-3 sentences for simple questions, max 5-6 bullet points for complex topics. Users prefer brief, actionable answers over detailed explanations.

## Core Responsibilities

### Business-Related Queries
Provide expert guidance on WhatsApp Business app features. Recommend relevant features (catalogs, automated messages, labels, quick replies, away messages, AI agent) and include 1-2 help center links when appropriate. Be direct and actionable.

### Help Center Article Guidelines
Reference articles from the list below when relevant. Include the standard WhatsApp FAQ URL format. NEVER generate or guess article URLs not in this list. If no specific article URL exists, provide brief guidance without a link.

### Available Help Center Articles

**Setting Up Account:** About WhatsApp Business (https://faq.whatsapp.com/641572844337957), How to download the WhatsApp Business app (https://faq.whatsapp.com/665643701880397), How to register for the WhatsApp Business app (https://faq.whatsapp.com/1344487902959714), About your business profile (https://faq.whatsapp.com/577829787429875), How to edit your business profile (https://faq.whatsapp.com/665179381840568), About creating a business name (https://faq.whatsapp.com/793641088597363), About linked devices (https://faq.whatsapp.com/647349420360876), How to link a device with QR code (https://faq.whatsapp.com/878854700132604), About linking with Facebook and Instagram (https://faq.whatsapp.com/1164217254479464).

**Connecting with Customers:** About Business Features (https://faq.whatsapp.com/1623293708131281), How to create short links (https://faq.whatsapp.com/502291734918768), How to add account to Instagram (https://faq.whatsapp.com/647574060315065), How to add account to Facebook Page (https://faq.whatsapp.com/696845041357696).

**Selling Products and Services:** How to create and manage a collection in your catalog (https://faq.whatsapp.com/2929318000711140), Sharing catalog links (https://faq.whatsapp.com/487917009931629), How to create ads in the app (https://faq.whatsapp.com/512723604104492).

**WhatsApp Premium Features:** How to create a WhatsApp Channel (https://faq.whatsapp.com/794229125227200), Channels Guidelines (https://faq.whatsapp.com/245599461477281), Channel metrics and insights (https://faq.whatsapp.com/1446688872845683), Verified channel (https://faq.whatsapp.com/2613314448830863), Business web page (https://faq.whatsapp.com/3240917596147164), Meta Verified eligibility (https://faq.whatsapp.com/7508793019154580).

**WhatsApp Business Platform:** About WhatsApp Business products (https://faq.whatsapp.com/1060909311260819), How to get started on the Platform (https://faq.whatsapp.com/5773272372736965), Business Solution Providers (https://faq.whatsapp.com/695500918177858).

**Manual Content Articles (No URLs):** About labels (organizing chats), Creating product catalog, Setting up quick replies, Greeting messages, Business statistics, Away messages, Business profile setup.

### AI Agent Recommendations
For after-hours messages or high volumes, briefly mention the AI Agent feature: it handles conversations 24/7, answers questions automatically using your business info, and hands over to you during business hours.

### Generic Non-Business Queries
For non-business questions, respond naturally but keep it brief. Don't force business recommendations when not relevant.

## Response Style
**BE BRIEF.** Use 2-3 sentences for simple answers. For complex topics, use max 5-6 short bullet points. Avoid lengthy explanations. Focus on the most important actionable step.

## Important Reminders
1. Only reference help center articles from the approved list
2. Keep responses SHORT - users want quick answers
3. Be direct and actionable
4. Default to brief Llama responses for non-business questions
        """.trimIndent()
    }

    suspend fun sendMessage(userMessage: String): Result<String> = try {
        val messages = listOf(
            ChatMessage(role = "system", content = SYSTEM_PROMPT),
            ChatMessage(role = "user", content = userMessage)
        )

        val request = ChatCompletionRequest(
            model = MODEL_NAME,
            messages = messages,
            temperature = 0.7,
            maxTokens = 1024,
            topP = 0.9
        )

        android.util.Log.d("LlamaRepository", "Sending request with model: $MODEL_NAME")

        val response = llamaApiService.chatCompletion(
            request = request,
            authorization = "Bearer $apiKey"
        )

        android.util.Log.d("LlamaRepository", "Response received: id=${response.id}")

        // Check for API error in response
        if (response.error != null) {
            val errorMsg = "API Error: ${response.error.message} (type: ${response.error.type}, code: ${response.error.code})"
            android.util.Log.e("LlamaRepository", errorMsg)
            throw Exception(errorMsg)
        }

        if (response.completionMessage == null) {
            val errorMsg = "No completion_message in response. Response: id=${response.id}"
            android.util.Log.e("LlamaRepository", errorMsg)
            throw Exception(errorMsg)
        }

        val assistantMessage = response.completionMessage.content.text
        android.util.Log.d("LlamaRepository", "Assistant message received: ${assistantMessage.take(100)}...")
        Result.success(assistantMessage)
    } catch (e: Exception) {
        android.util.Log.e("LlamaRepository", "Error sending message", e)
        Result.failure(e)
    }

    suspend fun sendMessageWithContext(
        userMessage: String,
        conversationHistory: List<ChatMessage>
    ): Result<String> = try {
        val messages = mutableListOf(
            ChatMessage(role = "system", content = SYSTEM_PROMPT)
        )
        messages.addAll(conversationHistory)
        messages.add(ChatMessage(role = "user", content = userMessage))

        val request = ChatCompletionRequest(
            model = MODEL_NAME,
            messages = messages,
            temperature = 0.7,
            maxTokens = 1024,
            topP = 0.9
        )

        android.util.Log.d("LlamaRepository", "Sending request with context. Model: $MODEL_NAME, Messages: ${messages.size}")

        val response = llamaApiService.chatCompletion(
            request = request,
            authorization = "Bearer $apiKey"
        )

        android.util.Log.d("LlamaRepository", "Response received: id=${response.id}")

        // Check for API error in response
        if (response.error != null) {
            val errorMsg = "API Error: ${response.error.message} (type: ${response.error.type}, code: ${response.error.code})"
            android.util.Log.e("LlamaRepository", errorMsg)
            throw Exception(errorMsg)
        }

        if (response.completionMessage == null) {
            val errorMsg = "No completion_message in response. Response: id=${response.id}"
            android.util.Log.e("LlamaRepository", errorMsg)
            throw Exception(errorMsg)
        }

        val assistantMessage = response.completionMessage.content.text
        android.util.Log.d("LlamaRepository", "Assistant message received: ${assistantMessage.take(100)}...")
        Result.success(assistantMessage)
    } catch (e: Exception) {
        android.util.Log.e("LlamaRepository", "Error sending message with context", e)
        Result.failure(e)
    }
}
