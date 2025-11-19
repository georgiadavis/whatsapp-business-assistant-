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
    val temperature: Double? = null,
    @SerialName("max_completion_tokens")
    val maxTokens: Int? = null,
    @SerialName("top_p")
    val topP: Double? = null,
    @SerialName("stream")
    val stream: Boolean = false
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
    val id: String? = null,
    @SerialName("completion_message")
    val completionMessage: CompletionMessage? = null,
    @SerialName("error")
    val error: ErrorDetail? = null
)

@Serializable
data class CompletionMessage(
    @SerialName("role")
    val role: String,
    @SerialName("stop_reason")
    val stopReason: String? = null,
    @SerialName("content")
    val content: MessageContent
)

@Serializable
data class MessageContent(
    @SerialName("type")
    val type: String,
    @SerialName("text")
    val text: String
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
