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
        private const val MODEL_NAME = "Llama-3.3-8B-WA-business-assistant-v6"
        private const val SYSTEM_PROMPT = """
            You are Meta AI, a helpful assistant for WhatsApp Business. 
            You help business owners with WhatsApp Business features, marketing advice, and customer support strategies.
            Provide clear, concise, and actionable responses.
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

        val response = llamaApiService.chatCompletion(
            request = request,
            authorization = "Bearer $apiKey"
        )

        val assistantMessage = response.choices.firstOrNull()?.message?.content
            ?: throw Exception("No response from model")

        Result.success(assistantMessage)
    } catch (e: Exception) {
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

        val response = llamaApiService.chatCompletion(
            request = request,
            authorization = "Bearer $apiKey"
        )

        val assistantMessage = response.choices.firstOrNull()?.message?.content
            ?: throw Exception("No response from model")

        Result.success(assistantMessage)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

