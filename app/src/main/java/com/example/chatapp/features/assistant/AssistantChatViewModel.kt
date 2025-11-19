package com.example.chatapp.features.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.remote.ChatMessage
import com.example.chatapp.data.remote.LinkAttachment
import com.example.chatapp.data.repository.LlamaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val userInput: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val suggestedResponses: List<String> = emptyList()
)

@HiltViewModel
class AssistantChatViewModel @Inject constructor(
    private val llamaRepository: LlamaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        // Generate dynamic greeting message when chat opens
        generateGreetingMessage()
    }

    private fun generateGreetingMessage() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = llamaRepository.sendMessage(
                "Generate a brief, friendly greeting for a WhatsApp Business assistant. Keep it to 1-2 sentences. Introduce yourself as 'your business assistant' and offer to help with WhatsApp Business features."
            )

            result.onSuccess { greetingResponse ->
                val timestamp = getCurrentTimeFormatted()
                val message = ChatMessage(role = "assistant", content = greetingResponse, timestamp = timestamp)
                val messages = listOf(message)

                _uiState.value = _uiState.value.copy(
                    messages = messages,
                    isLoading = false
                )

                // Generate suggested responses after greeting loads
                generateSuggestedResponses()
            }.onFailure {
                // Fall back to a default greeting if API fails
                val timestamp = getCurrentTimeFormatted()
                val fallbackGreeting = "Hi! I'm your business assistant. How can I help you today?"
                val message = ChatMessage(role = "assistant", content = fallbackGreeting, timestamp = timestamp)
                val messages = listOf(message)

                _uiState.value = _uiState.value.copy(
                    messages = messages,
                    isLoading = false
                )

                // Generate suggested responses even with fallback greeting
                generateSuggestedResponses()
            }
        }
    }

    private fun generateSuggestedResponses() {
        viewModelScope.launch {
            val result = llamaRepository.sendMessage(
                "Generate exactly 3 short action-oriented prompts (each 3-6 words) starting with action verbs that a WhatsApp Business owner might use. Examples: 'Set up automated messages', 'Create product catalog', 'Get customer insights'. Return only the prompts, one per line, no numbering or bullets."
            )

            result.onSuccess { responsesText ->
                val suggestions = responsesText.split("\n")
                    .map { it.trim() }
                    .filter { it.isNotBlank() && !it.matches(Regex("""^\d+[\.)]\s*""")) } // Filter out numbering
                    .take(3)

                if (suggestions.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(suggestedResponses = suggestions)
                }
            }.onFailure {
                // Use fallback suggested responses
                val fallbackSuggestions = listOf(
                    "Set up automated messages",
                    "Create product catalog",
                    "Get customer insights"
                )
                _uiState.value = _uiState.value.copy(suggestedResponses = fallbackSuggestions)
            }
        }
    }

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return

        // Get current timestamp
        val timestamp = getCurrentTimeFormatted()

        // Create user message
        val userChatMessage = ChatMessage(role = "user", content = userMessage, timestamp = timestamp)

        // Add user message to chat
        val currentMessages = _uiState.value.messages.toMutableList()
        currentMessages.add(userChatMessage)
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
                val assistantTimestamp = getCurrentTimeFormatted()

                // Extract URLs and create link attachments
                val linkAttachments = extractLinkAttachments(assistantResponse)

                // Remove URLs from the content for display
                val contentWithoutUrls = removeFaqUrls(assistantResponse)

                val assistantChatMessage = ChatMessage(
                    role = "assistant",
                    content = contentWithoutUrls,
                    timestamp = assistantTimestamp,
                    attachments = linkAttachments
                )
                updatedMessages.add(assistantChatMessage)

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

    private fun extractLinkAttachments(text: String): List<LinkAttachment> {
        val attachments = mutableListOf<LinkAttachment>()

        // Pattern: text (URL) - extracts both the descriptive text and URL
        val parenPattern = """([^()\n]+?)\s*\((https://faq\.whatsapp\.com/\d+)\)""".toRegex()
        val parenMatches = parenPattern.findAll(text)

        for (match in parenMatches) {
            val title = match.groupValues[1].trim()
                .replace(Regex("""^(Learn more|about|on):?\s*""", RegexOption.IGNORE_CASE), "")
                .take(40) // Keep titles short
                .trim()
            val url = match.groupValues[2]

            attachments.add(LinkAttachment(
                url = url,
                title = if (title.isNotBlank()) title else "WhatsApp Help Article",
                domain = "faq.whatsapp.com"
            ))
        }

        // Also catch any standalone URLs not in parentheses
        val urlRegex = """https://faq\.whatsapp\.com/\d+""".toRegex()
        val allUrls = urlRegex.findAll(text).map { it.value }.toSet()
        val capturedUrls = attachments.map { it.url }.toSet()
        val remainingUrls = allUrls - capturedUrls

        for (url in remainingUrls) {
            attachments.add(LinkAttachment(
                url = url,
                title = "WhatsApp Help Article",
                domain = "faq.whatsapp.com"
            ))
        }

        return attachments.distinctBy { it.url }
    }

    private fun removeFaqUrls(text: String): String {
        var result = text

        // Remove text (URL) pattern - remove both the URL and parentheses
        val parenPattern = """[^()\n]+?\s*\(https://faq\.whatsapp\.com/\d+\)""".toRegex()
        result = result.replace(parenPattern, "")

        // Replace []() markdown link pattern
        val markdownLinkRegex = """\[([^\]]*)\]\((https://faq\.whatsapp\.com/\d+)\)""".toRegex()
        result = result.replace(markdownLinkRegex, "")

        // Remove standalone URLs and "Learn more:" text
        result = result.replace("""Learn more:\s*https://faq\.whatsapp\.com/\d+""".toRegex(), "")
        result = result.replace("""\bhttps://faq\.whatsapp\.com/\d+""".toRegex(), "")

        // Clean up extra whitespace, punctuation, and line breaks
        result = result.replace(Regex("""\s*\(\s*\)"""), "") // Remove empty parentheses
        result = result.replace(Regex("""\n\s*\n\s*\n+"""), "\n\n")
        result = result.replace(Regex("""\s+"""), " ")
        result = result.replace(Regex("""\s+([.,!?])"""), "$1") // Fix punctuation spacing

        return result.trim()
    }

    private fun getCurrentTimeFormatted(): String {
        val calendar = java.util.Calendar.getInstance()
        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = calendar.get(java.util.Calendar.MINUTE)
        return String.format("%02d:%02d", hour, minute)
    }

    fun updateUserInput(input: String) {
        _uiState.value = _uiState.value.copy(userInput = input)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
