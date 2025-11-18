package com.example.chatapp.features.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatapp.R
import com.example.chatapp.data.remote.ChatMessage
import com.example.chatapp.wds.theme.WdsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantChatScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: AssistantChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Track keyboard state
    val imeInsets = WindowInsets.ime
    val density = LocalDensity.current
    val imeHeight = with(density) { imeInsets.getBottom(this).toDp() }
    val isKeyboardOpen = imeHeight > 0.dp

    // Scroll to bottom when messages change
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            delay(100)
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WdsTheme.colors.colorChatBackgroundWallpaper)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                AssistantChatTopBar(onNavigateBack = onNavigateBack)
            },
            bottomBar = {
                AssistantChatComposer(
                    userInput = uiState.userInput,
                    isLoading = uiState.isLoading,
                    onMessageSend = { message ->
                        viewModel.sendMessage(message)
                    },
                    onInputChange = { input ->
                        viewModel.updateUserInput(input)
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(
                        start = WdsTheme.dimensions.wdsSpacingDouble,
                        end = WdsTheme.dimensions.wdsSpacingDouble,
                        top = WdsTheme.dimensions.wdsSpacingDouble,
                        bottom = WdsTheme.dimensions.wdsSpacingDouble
                    ),
                    verticalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingDouble),
                    reverseLayout = true
                ) {
                    // Loading indicator
                    if (uiState.isLoading) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp,
                                    color = WdsTheme.colors.colorContentDeemphasized
                                )
                            }
                        }
                    }

                    // Error message
                    if (uiState.error != null) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(WdsTheme.shapes.singlePlus),
                                    color = WdsTheme.colors.colorNegative.copy(alpha = 0.1f),
                                    modifier = Modifier.widthIn(max = 260.dp)
                                ) {
                                    Text(
                                        text = uiState.error ?: "Error",
                                        style = WdsTheme.typography.body2,
                                        color = WdsTheme.colors.colorNegative,
                                        modifier = Modifier.padding(WdsTheme.dimensions.wdsSpacingSingle)
                                    )
                                }
                            }
                        }
                    }

                    // Messages
                    items(uiState.messages.size) { index ->
                        val message = uiState.messages[index]
                        
                        if (message.role == "user") {
                            // User message (right-aligned, sent style)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box(
                                    modifier = Modifier
                                        .widthIn(max = 260.dp)
                                        .clip(WdsTheme.shapes.singlePlus)
                                        .background(WdsTheme.colors.colorBubbleSurfaceOutgoing)
                                ) {
                                    Text(
                                        text = message.content,
                                        style = WdsTheme.typography.chatBody1,
                                        color = WdsTheme.colors.colorContentDefault,
                                        modifier = Modifier.padding(
                                            horizontal = WdsTheme.dimensions.wdsSpacingSingle,
                                            vertical = WdsTheme.dimensions.wdsSpacingHalfPlus
                                        )
                                    )
                                }
                            }
                        } else {
                            // AI message (left-aligned, received style)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .widthIn(max = 260.dp)
                                        .clip(WdsTheme.shapes.singlePlus)
                                        .background(WdsTheme.colors.colorBubbleSurfaceIncoming)
                                ) {
                                    Text(
                                        text = message.content,
                                        style = WdsTheme.typography.chatBody1,
                                        color = WdsTheme.colors.colorContentDefault,
                                        modifier = Modifier.padding(
                                            horizontal = WdsTheme.dimensions.wdsSpacingSingle,
                                            vertical = WdsTheme.dimensions.wdsSpacingHalfPlus
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Initial greeting
                    if (uiState.messages.isEmpty() && !uiState.isLoading) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .widthIn(max = 260.dp)
                                        .clip(WdsTheme.shapes.singlePlus)
                                        .background(WdsTheme.colors.colorBubbleSurfaceIncoming)
                                ) {
                                    Text(
                                        text = "Hi! I'm Meta AI, your WhatsApp Business assistant. How can I help you today?",
                                        style = WdsTheme.typography.chatBody1,
                                        color = WdsTheme.colors.colorContentDefault,
                                        modifier = Modifier.padding(
                                            horizontal = WdsTheme.dimensions.wdsSpacingSingle,
                                            vertical = WdsTheme.dimensions.wdsSpacingHalfPlus
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssistantChatTopBar(
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle)
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_meta_ai),
                        contentDescription = "Meta AI",
                        modifier = Modifier.size(WdsTheme.dimensions.wdsIconSizeMedium),
                        tint = Color.Unspecified
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Meta AI",
                        style = WdsTheme.typography.chatHeaderTitle,
                        color = WdsTheme.colors.colorContentDefault
                    )
                    Text(
                        text = "with Llama 4",
                        style = WdsTheme.typography.body3,
                        color = WdsTheme.colors.colorContentDeemphasized
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = WdsTheme.colors.colorContentDefault
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.History,
                    contentDescription = "History",
                    tint = WdsTheme.colors.colorContentDefault
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = "Call",
                    tint = WdsTheme.colors.colorContentDefault
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More",
                    tint = WdsTheme.colors.colorContentDefault
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WdsTheme.colors.colorSurfaceDefault
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssistantChatComposer(
    userInput: String,
    isLoading: Boolean,
    onMessageSend: (String) -> Unit,
    onInputChange: (String) -> Unit
) {
    val colors = WdsTheme.colors
    val dimensions = WdsTheme.dimensions
    val shapes = WdsTheme.shapes

    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.wdsSpacingHalf),
            verticalAlignment = Alignment.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimensions.wdsSpacingHalf),
                shape = shapes.triple,
                color = colors.colorBubbleSurfaceIncoming,
                shadowElevation = dimensions.wdsElevationSubtle
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = dimensions.wdsSpacingHalf),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = userInput,
                        onValueChange = onInputChange,
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                text = "Ask anything",
                                style = WdsTheme.typography.chatBody1,
                                color = colors.colorContentDeemphasized
                            )
                        },
                        textStyle = WdsTheme.typography.chatBody1.copy(
                            color = colors.colorContentDefault
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = colors.colorAccent
                        ),
                        trailingIcon = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(dimensions.wdsSpacingHalf)
                            ) {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Outlined.AttachFile,
                                        contentDescription = "Attach",
                                        tint = colors.colorContentDeemphasized,
                                        modifier = Modifier.size(dimensions.wdsIconSizeMedium)
                                    )
                                }
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Outlined.PhotoCamera,
                                        contentDescription = "Camera",
                                        tint = colors.colorContentDeemphasized,
                                        modifier = Modifier.size(dimensions.wdsIconSizeMedium)
                                    )
                                }
                            }
                        },
                        enabled = !isLoading
                    )
                }
            }

            FloatingActionButton(
                onClick = {
                    if (userInput.isNotBlank()) {
                        onMessageSend(userInput)
                    }
                },
                modifier = Modifier.size(dimensions.wdsTouchTargetComfortable),
                containerColor = colors.colorAccent,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = dimensions.wdsElevationSubtle
                ),
                enabled = !isLoading && userInput.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}
