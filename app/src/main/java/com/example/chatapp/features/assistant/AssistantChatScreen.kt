package com.example.chatapp.features.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
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
                    verticalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingDouble)
                ) {
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

                    // Messages
                    items(uiState.messages.size) { index ->
                        val message = uiState.messages[index]

                        if (message.role == "user") {
                            // User message (right-aligned, sent style)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingDouble, vertical = WdsTheme.dimensions.wdsSpacingQuarter),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box(
                                    modifier = Modifier
                                        .widthIn(max = 260.dp)
                                        .shadow(
                                            elevation = WdsTheme.dimensions.wdsElevationSubtle,
                                            spotColor = WdsTheme.colors.colorBubbleSurfaceOverlay,
                                            ambientColor = WdsTheme.colors.colorBubbleSurfaceOverlay,
                                            shape = WdsTheme.shapes.singlePlus
                                        )
                                        .clip(WdsTheme.shapes.singlePlus)
                                        .background(WdsTheme.colors.colorBubbleSurfaceOutgoing)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = WdsTheme.dimensions.wdsSpacingSingle,
                                            vertical = WdsTheme.dimensions.wdsSpacingHalfPlus
                                        ),
                                        horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingQuarter),
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        Text(
                                            text = message.content,
                                            style = WdsTheme.typography.chatBody1,
                                            color = WdsTheme.colors.colorContentDefault,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(
                                            imageVector = Icons.Outlined.DoneAll,
                                            contentDescription = "Message sent",
                                            modifier = Modifier.size(16.dp),
                                            tint = WdsTheme.colors.colorContentRead
                                        )
                                    }
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
                                    FormattedText(
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
                                    shape = WdsTheme.shapes.singlePlus,
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
                }
            }
        }
    }
}

@Composable
private fun FormattedText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        var currentIndex = 0
        val boldRegex = """\*\*(.*?)\*\*""".toRegex()
        // Updated URL regex to exclude trailing punctuation
        val urlRegex = """https?://[^\s)]+(?=[.!?,;:]?(?:\s|$))""".toRegex()

        // Find all bold text matches
        val boldMatches = boldRegex.findAll(text).toList()
        // Find all URL matches
        val urlMatches = urlRegex.findAll(text).toList()

        // Combine and sort all matches by start index
        val allMatches = (boldMatches + urlMatches).sortedBy { it.range.first }

        for (match in allMatches) {
            // Add text before this match
            if (currentIndex < match.range.first) {
                append(text.substring(currentIndex, match.range.first))
            }

            when {
                match in boldMatches -> {
                    // Bold text
                    val boldText = match.groupValues[1]
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(boldText)
                    }
                }
                match in urlMatches -> {
                    // Clickable URL - remove any trailing punctuation
                    var url = match.value
                    // Remove trailing punctuation from the URL
                    while (url.isNotEmpty() && url.last() in ".!?,;:") {
                        url = url.dropLast(1)
                    }
                    pushStringAnnotation(tag = "URL", annotation = url)
                    withStyle(
                        style = SpanStyle(
                            color = WdsTheme.colors.colorAccent,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(url)
                    }
                    pop()
                }
            }

            currentIndex = match.range.last + 1
        }

        // Add remaining text
        if (currentIndex < text.length) {
            append(text.substring(currentIndex))
        }
    }

    ClickableText(
        text = annotatedString,
        style = style.copy(color = color),
        modifier = modifier,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )
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
                                // Show attachment & camera only when input is empty
                                if (userInput.isEmpty()) {
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
                            }
                        },
                        enabled = !isLoading
                    )
                }
            }

            // Button changes from Mic to Send based on input
            FloatingActionButton(
                onClick = {
                    if (userInput.isNotBlank() && !isLoading) {
                        onMessageSend(userInput)
                    }
                },
                modifier = Modifier.size(dimensions.wdsTouchTargetComfortable),
                containerColor = colors.colorAccent,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = dimensions.wdsElevationSubtle
                )
            ) {
                when {
                    isLoading -> {
                        // Loading spinner
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    }
                    userInput.isNotEmpty() -> {
                        // Send button when user is typing
                        Icon(
                            imageVector = Icons.Outlined.Send,
                            contentDescription = "Send message"
                        )
                    }
                    else -> {
                        // Mic button when input is empty
                        Icon(
                            imageVector = Icons.Outlined.Mic,
                            contentDescription = "Record voice message"
                        )
                    }
                }
            }
        }
    }
}
