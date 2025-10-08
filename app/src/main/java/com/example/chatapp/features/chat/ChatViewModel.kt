package com.example.chatapp.features.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.local.entity.MessageEntity
import com.example.chatapp.data.local.entity.MessageType
import com.example.chatapp.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var conversationId: String = savedStateHandle.get<String>("conversationId") ?: ""

    // Current user ID - in a real app, this would come from auth service
    val currentUserId = "user_1"

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<MessageEntity>>(emptyList())
    val messages: StateFlow<List<MessageEntity>> = _messages.asStateFlow()

    private var conversationLastViewedAt: Long = 0L

    fun loadConversation(newConversationId: String) {
        // Update the conversation ID
        this.conversationId = newConversationId

        // Load conversation details to get lastViewedAt
        viewModelScope.launch {
            val conversation = chatRepository.getConversationById(newConversationId)
            conversationLastViewedAt = conversation?.lastViewedAt ?: 0L
        }

        // Start collecting messages for this conversation
        viewModelScope.launch {
            chatRepository.getConversationMessages(newConversationId)
                .collect { messageList ->
                    _messages.value = messageList

                    // Sort messages by timestamp to find the chronologically first unread
                    val sortedMessages = messageList.sortedBy { it.timestamp }

                    // Calculate unread messages - count incoming messages that arrived after lastViewedAt
                    val unreadIncomingMessages = sortedMessages.filter { message ->
                        message.senderId != currentUserId &&
                        message.timestamp > conversationLastViewedAt
                    }

                    if (unreadIncomingMessages.isNotEmpty()) {
                        val firstUnread = unreadIncomingMessages.first()
                        _uiState.update { it.copy(
                            unreadCount = unreadIncomingMessages.size,
                            firstUnreadMessageId = firstUnread.messageId
                        )}
                    } else {
                        _uiState.update { it.copy(
                            unreadCount = 0,
                            firstUnreadMessageId = null
                        )}
                    }
                }
        }

        // Load conversation details for header
        viewModelScope.launch {
            chatRepository.getConversationById(newConversationId)?.let { conversation ->
                if (conversation.isGroup) {
                    // For group chats, use the conversation title and avatar
                    _uiState.update { it.copy(
                        conversationTitle = conversation.title ?: "",
                        conversationAvatar = conversation.avatarUrl,
                        isGroupChat = true
                    )}

                    // Load participant info for group chat
                    chatRepository.getConversationParticipants(newConversationId)
                        .collect { participants ->
                            val participantMap = mutableMapOf<String, ParticipantInfo>()
                            participants.forEach { participant ->
                                val user = chatRepository.getUserById(participant.userId)
                                if (user != null) {
                                    participantMap[participant.userId] = ParticipantInfo(
                                        userId = participant.userId,
                                        displayName = user.displayName,
                                        avatarUrl = user.avatarUrl
                                    )
                                }
                            }
                            _uiState.update { it.copy(participantInfo = participantMap) }
                        }
                } else {
                    // For 1:1 chats, get the other user's info
                    val participants = chatRepository.getConversationParticipants(newConversationId).first()
                    val otherUser = participants.firstOrNull { it.userId != currentUserId }

                    if (otherUser != null) {
                        val user = chatRepository.getUserById(otherUser.userId)
                        if (user != null) {
                            _uiState.update { it.copy(
                                conversationTitle = user.displayName,
                                conversationAvatar = user.avatarUrl,
                                isGroupChat = false
                            )}
                        }
                    }
                }
            }
        }
    }

    fun sendMessage() {
        val text = _uiState.value.composerText.trim()
        if (text.isEmpty()) return

        viewModelScope.launch {
            val message = MessageEntity(
                messageId = UUID.randomUUID().toString(),
                conversationId = conversationId,
                senderId = currentUserId,
                content = text,
                timestamp = System.currentTimeMillis(),
                messageType = MessageType.TEXT,
                status = com.example.chatapp.data.local.entity.MessageStatus.SENT,
                isDeleted = false
            )

            chatRepository.sendMessage(message)
            _uiState.update { it.copy(composerText = "") }

            // Update lastViewedAt when user sends a message
            markMessagesAsRead()
        }
    }

    fun updateComposerText(text: String) {
        _uiState.update { it.copy(composerText = text) }
    }

    fun markMessagesAsRead() {
        viewModelScope.launch {
            // Update lastViewedAt to current time
            val currentTime = System.currentTimeMillis()
            chatRepository.updateLastViewedAt(conversationId, currentTime)
            conversationLastViewedAt = currentTime

            // Reset unread count
            _uiState.update { it.copy(
                unreadCount = 0,
                firstUnreadMessageId = null
            )}
        }
    }
}

data class ChatUiState(
    val conversationTitle: String = "",
    val conversationAvatar: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: String? = null,
    val composerText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isGroupChat: Boolean = false,
    val participantInfo: Map<String, ParticipantInfo> = emptyMap(),
    val unreadCount: Int = 0,
    val firstUnreadMessageId: String? = null
)

data class ParticipantInfo(
    val userId: String,
    val displayName: String,
    val avatarUrl: String?
)
