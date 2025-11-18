package com.example.chatapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Request Models
@Serializable
data class ChatCompletionRequest(
    @SerialName("model")
    val model: String,
    @SerialName("messages")
    val messages: List<ChatMessage>,
    @SerialName("temperature")
    val temperature: Double = 0.7,
    @SerialName("max_tokens")
    val maxTokens: Int = 1024,
    @SerialName("top_p")
    val topP: Double = 0.9
)

@Serializable
data class ChatMessage(
    @SerialName("role")
    val role: String, // "user", "assistant", "system"
    @SerialName("content")
    val content: String
)

// Response Models
@Serializable
data class ChatCompletionResponse(
    @SerialName("id")
    val id: String,
    @SerialName("object")
    val `object`: String,
    @SerialName("created")
    val created: Long,
    @SerialName("model")
    val model: String,
    @SerialName("choices")
    val choices: List<Choice>,
    @SerialName("usage")
    val usage: Usage? = null
)

@Serializable
data class Choice(
    @SerialName("index")
    val index: Int,
    @SerialName("message")
    val message: ChatMessage,
    @SerialName("finish_reason")
    val finishReason: String? = null
)

@Serializable
data class Usage(
    @SerialName("prompt_tokens")
    val promptTokens: Int,
    @SerialName("completion_tokens")
    val completionTokens: Int,
    @SerialName("total_tokens")
    val totalTokens: Int
)

// Error Response
@Serializable
data class ApiError(
    @SerialName("error")
    val error: ErrorDetail? = null
)

@Serializable
data class ErrorDetail(
    @SerialName("message")
    val message: String,
    @SerialName("type")
    val type: String? = null,
    @SerialName("code")
    val code: String? = null
)

