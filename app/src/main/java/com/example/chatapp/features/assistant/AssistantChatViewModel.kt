package com.example.chatapp.features.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.remote.ChatMessage
import com.example.chatapp.data.repository.LlamaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val userInput: String = ""
)

@HiltViewModel
class AssistantChatViewModel @Inject constructor(
    private val llamaRepository: LlamaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val systemPrompt = """
        You are Meta AI, a helpful assistant for WhatsApp Business. 
        You help business owners with WhatsApp Business features, marketing advice, and customer support strategies.
        Provide clear, concise, and actionable responses.
    """.trimIndent()

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return

        // Add user message to chat
        val currentMessages = _uiState.value.messages.toMutableList()
        currentMessages.add(ChatMessage(role = "user", content = userMessage))
        _uiState.value = _uiState.value.copy(
            messages = currentMessages,
            isLoading = true,
            error = null,
            userInput = ""
        )

        // Send to Llama API
        viewModelScope.launch {
            val result = llamaRepository.sendMessageWithContext(
                userMessage = userMessage,
                conversationHistory = currentMessages.dropLast(1) // Exclude the user message we just added
            )

            result.onSuccess { assistantResponse ->
                val updatedMessages = _uiState.value.messages.toMutableList()
                updatedMessages.add(ChatMessage(role = "assistant", content = assistantResponse))
                _uiState.value = _uiState.value.copy(
                    messages = updatedMessages,
                    isLoading = false,
                    error = null
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to get response from AI"
                )
            }
        }
    }

    fun updateUserInput(input: String) {
        _uiState.value = _uiState.value.copy(userInput = input)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

