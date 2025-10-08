package com.example.chatapp.features.chatinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatapp.features.chatinfo.components.*
import com.example.chatapp.wds.theme.WdsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInfoScreen(
    conversationId: String,
    onBackClick: () -> Unit,
    viewModel: ChatInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()

    // Set status bar color based on theme
    val systemUiController = rememberSystemUiController()
    val statusBarColor = WdsTheme.colors.colorSurfaceDefault
    DisposableEffect(isDarkTheme, statusBarColor) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = !isDarkTheme
        )
        onDispose {
            // Reset to transparent when leaving the screen
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = !isDarkTheme
            )
        }
    }

    // Load chat info when the screen is first displayed
    LaunchedEffect(conversationId) {
        viewModel.loadChatInfo(conversationId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = WdsTheme.colors.colorSurfaceDefault,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = WdsTheme.colors.colorContentDefault
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Show more options */ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More options",
                            tint = WdsTheme.colors.colorContentDefault
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WdsTheme.colors.colorSurfaceDefault
                )
            )
        }
    ) { paddingValues ->
        if (uiState.conversation != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                if (uiState.isGroupChat) {
                    // Group Chat Info Components
                    item {
                        GroupInfoHeader(
                            conversation = uiState.conversation,
                            participantCount = uiState.participants.size,
                            onBackClick = onBackClick,
                            onMoreClick = { /* TODO: Show more options */ }
                        )
                    }

                    item {
                        GroupActionButtons(
                            onAudioCall = { /* TODO: Handle audio call */ },
                            onVideoCall = { /* TODO: Handle video call */ },
                            onAddMember = { /* TODO: Handle add member */ },
                            onSearch = { /* TODO: Handle search */ }
                        )
                    }

                    item { SectionDivider() }

                    item {
                        GroupDescription(
                            description = uiState.groupDescription,
                            createdBy = uiState.createdBy,
                            createdAt = uiState.conversation?.createdAt ?: System.currentTimeMillis(),
                            onAddDescription = { /* TODO: Handle add description */ }
                        )
                    }

                    item { SectionDivider() }

                    item {
                        MediaLinksDocsSection(
                            photoCount = uiState.photoCount,
                            videoCount = uiState.videoCount,
                            docCount = uiState.docCount,
                            mediaMessages = uiState.mediaMessages,
                            onViewAll = { /* TODO: Navigate to media gallery */ }
                        )
                    }

                    item { SectionDivider() }

                    item {
                        GroupSettingsSection(
                            isMuted = uiState.isMuted,
                            onToggleMute = viewModel::toggleMute,
                            onCustomNotifications = { /* TODO: Handle custom notifications */ },
                            onMediaVisibility = { /* TODO: Handle media visibility */ }
                        )
                    }

                    item { SectionDivider() }

                    item {
                        EncryptionSection()
                    }

                    item {
                        GroupManagementSection(
                            isLocked = uiState.chatLocked,
                            onToggleLock = viewModel::toggleChatLock,
                            hasDisappearingMessages = uiState.disappearingMessages,
                            onToggleDisappearingMessages = viewModel::toggleDisappearingMessages,
                            onGroupPermissions = { /* TODO: Handle group permissions */ }
                        )
                    }

                    item { SectionDivider() }

                    item {
                        GroupParticipantsSection(
                            participants = uiState.participants,
                            participantRoles = uiState.participantRoles,
                            currentUserId = "user_1", // Current user ID - matches the data generator
                            onAddParticipant = { /* TODO: Handle add participant */ },
                            onInviteViaLink = { /* TODO: Handle invite via link */ },
                            onAddToCommunity = { /* TODO: Handle add to community */ },
                            onParticipantClick = { /* TODO: Handle participant click */ },
                            onMakeAdmin = viewModel::makeAdmin,
                            onRemoveAdmin = viewModel::removeAdmin,
                            onRemoveParticipant = viewModel::removeParticipant
                        )
                    }

                    item { SectionDivider() }

                    item {
                        GroupDangerZone(
                            onExitGroup = { viewModel.exitGroup() },
                            onReportGroup = { viewModel.reportGroup() }
                        )
                    }

                } else {
                    // 1:1 Chat Info Components
                    item {
                        ProfileHeader(
                            user = uiState.directChatUser,
                            onBackClick = onBackClick,
                            onMoreClick = { /* TODO: Show more options */ }
                        )
                    }

                    item {
                        ContactActionButtons(
                            onAudioCall = { /* TODO: Handle audio call */ },
                            onVideoCall = { /* TODO: Handle video call */ },
                            onSearch = { /* TODO: Handle search */ }
                        )
                    }

                    item { SectionDivider() }

                    item {
                        ContactStatus(
                            statusMessage = uiState.directChatUser?.statusMessage,
                            phoneNumber = uiState.directChatUser?.username
                        )
                    }

                    item { SectionDivider() }

                    item {
                        MediaLinksDocsSection(
                            photoCount = uiState.photoCount,
                            videoCount = uiState.videoCount,
                            docCount = uiState.docCount,
                            mediaMessages = uiState.mediaMessages,
                            onViewAll = { /* TODO: Navigate to media gallery */ }
                        )
                    }

                    item { SectionDivider() }

                    item {
                        ContactSettingsSection(
                            isMuted = uiState.isMuted,
                            onToggleMute = viewModel::toggleMute,
                            onCustomNotifications = { /* TODO: Handle custom notifications */ },
                            onMediaVisibility = { /* TODO: Handle media visibility */ }
                        )
                    }

                    item { SectionDivider() }

                    item {
                        EncryptionSection()
                    }

                    item {
                        ContactManagementSection(
                            hasDisappearingMessages = uiState.disappearingMessages,
                            onToggleDisappearingMessages = viewModel::toggleDisappearingMessages
                        )
                    }

                    item { SectionDivider() }

                    item {
                        ContactDangerZone(
                            isBlocked = uiState.isBlocked,
                            userName = uiState.directChatUser?.displayName ?: "",
                            onBlockToggle = {
                                if (uiState.isBlocked) {
                                    viewModel.unblockUser()
                                } else {
                                    viewModel.blockUser()
                                }
                            },
                            onReport = { viewModel.reportUser() }
                        )
                    }
                }
            }
        } else {
            // Loading state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}