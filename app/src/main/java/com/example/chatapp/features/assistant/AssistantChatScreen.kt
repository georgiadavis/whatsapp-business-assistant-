package com.example.chatapp.features.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.chatapp.data.remote.LinkAttachment
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
                    // Messages (including greeting if messages list is not empty)
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
                                        .shadow(
                                            elevation = WdsTheme.dimensions.wdsElevationSubtle,
                                            spotColor = WdsTheme.colors.colorBubbleSurfaceOverlay,
                                            ambientColor = WdsTheme.colors.colorBubbleSurfaceOverlay,
                                            shape = WdsTheme.shapes.singlePlus
                                        )
                                        .clip(WdsTheme.shapes.singlePlus)
                                        .background(WdsTheme.colors.colorBubbleSurfaceOutgoing)
                                ) {
                                    if (message.content.length < 20) {
                                        // Short messages - inline timestamp
                                        Row(
                                            modifier = Modifier.padding(
                                                horizontal = WdsTheme.dimensions.wdsSpacingSingle,
                                                vertical = WdsTheme.dimensions.wdsSpacingHalfPlus
                                            ),
                                            verticalAlignment = Alignment.Bottom,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Text(
                                                text = message.content,
                                                style = WdsTheme.typography.chatBody1,
                                                color = WdsTheme.colors.colorContentDefault
                                            )
                                            Spacer(modifier = Modifier.width(WdsTheme.dimensions.wdsSpacingSingle))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = message.timestamp,
                                                    style = WdsTheme.typography.chatBody3,
                                                    color = WdsTheme.colors.colorBubbleContentDeemphasized
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Icon(
                                                    imageVector = Icons.Outlined.DoneAll,
                                                    contentDescription = "Message sent",
                                                    modifier = Modifier.size(16.dp),
                                                    tint = WdsTheme.colors.colorContentRead
                                                )
                                            }
                                        }
                                    } else {
                                        // Longer messages - timestamp below
                                        Column(
                                            modifier = Modifier.padding(
                                                horizontal = WdsTheme.dimensions.wdsSpacingSingle,
                                                vertical = WdsTheme.dimensions.wdsSpacingHalfPlus
                                            )
                                        ) {
                                            Text(
                                                text = message.content,
                                                style = WdsTheme.typography.chatBody1,
                                                color = WdsTheme.colors.colorContentDefault
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .align(Alignment.End)
                                                    .padding(top = WdsTheme.dimensions.wdsSpacingQuarter),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = message.timestamp,
                                                    style = WdsTheme.typography.chatBody3,
                                                    color = WdsTheme.colors.colorBubbleContentDeemphasized
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Icon(
                                                    imageVector = Icons.Outlined.DoneAll,
                                                    contentDescription = "Message sent",
                                                    modifier = Modifier.size(16.dp),
                                                    tint = WdsTheme.colors.colorContentRead
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            // AI message (left-aligned, received style)
                            val currentUriHandler = LocalUriHandler.current

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle, vertical = WdsTheme.dimensions.wdsSpacingQuarter)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(WdsTheme.shapes.singlePlus)
                                            .background(WdsTheme.colors.colorBubbleSurfaceIncoming)
                                    ) {
                                        if (message.content.length < 20) {
                                            // Short messages - inline timestamp
                                            Row(
                                                modifier = Modifier.padding(
                                                    horizontal = WdsTheme.dimensions.wdsSpacingSingle,
                                                    vertical = WdsTheme.dimensions.wdsSpacingHalfPlus
                                                ),
                                                verticalAlignment = Alignment.Bottom,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                FormattedText(
                                                    text = message.content,
                                                    style = WdsTheme.typography.chatBody1,
                                                    color = WdsTheme.colors.colorContentDefault,
                                                    modifier = Modifier
                                                )
                                                Spacer(modifier = Modifier.width(WdsTheme.dimensions.wdsSpacingSingle))
                                                Text(
                                                    text = message.timestamp,
                                                    style = WdsTheme.typography.chatBody3,
                                                    color = WdsTheme.colors.colorBubbleContentDeemphasized
                                                )
                                            }
                                        } else {
                                            // Longer messages - timestamp below
                                            Column(
                                                modifier = Modifier.padding(
                                                    horizontal = WdsTheme.dimensions.wdsSpacingSingle,
                                                    vertical = WdsTheme.dimensions.wdsSpacingHalfPlus
                                                )
                                            ) {
                                                FormattedText(
                                                    text = message.content,
                                                    style = WdsTheme.typography.chatBody1,
                                                    color = WdsTheme.colors.colorContentDefault,
                                                    modifier = Modifier
                                                )
                                                Text(
                                                    text = message.timestamp,
                                                    style = WdsTheme.typography.chatBody3,
                                                    color = WdsTheme.colors.colorBubbleContentDeemphasized,
                                                    modifier = Modifier
                                                        .align(Alignment.End)
                                                        .padding(top = WdsTheme.dimensions.wdsSpacingQuarter)
                                                )
                                            }
                                        }
                                    }
                                }

                                // Link attachment cards
                                if (message.attachments.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(WdsTheme.dimensions.wdsSpacingHalfPlus))
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingHalfPlus),
                                        contentPadding = PaddingValues(horizontal = WdsTheme.dimensions.wdsSpacingQuarter)
                                    ) {
                                        items(message.attachments.size) { attachmentIndex ->
                                            val attachment = message.attachments[attachmentIndex]
                                            LinkAttachmentCard(
                                                attachment = attachment,
                                                onCardClick = { currentUriHandler.openUri(attachment.url) }
                                            )
                                        }
                                    }
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

                    // Suggested responses (only show when there's just the greeting and no loading)
                    if (uiState.suggestedResponses.isNotEmpty() && uiState.messages.size == 1 && !uiState.isLoading) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingHalfPlus)
                            ) {
                                uiState.suggestedResponses.forEach { suggestion ->
                                    SuggestedResponseChip(
                                        text = suggestion,
                                        onClick = { viewModel.sendMessage(suggestion) }
                                    )
                                }
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
private fun SuggestedResponseChip(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize() // Auto layout - hug width and height
            .shadow(
                elevation = WdsTheme.dimensions.wdsElevationSubtle,
                spotColor = WdsTheme.colors.colorBubbleSurfaceOverlay,
                ambientColor = WdsTheme.colors.colorBubbleSurfaceOverlay,
                shape = WdsTheme.shapes.singlePlus
            )
            .clip(WdsTheme.shapes.singlePlus)
            .background(Color.White.copy(alpha = 0.5f)) // White with 0.5 alpha transparency
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(
                start = WdsTheme.dimensions.wdsSpacingSingle, // 16dp
                end = WdsTheme.dimensions.wdsSpacingHalfPlus, // 8dp
                top = WdsTheme.dimensions.wdsSpacingHalfPlus, // 8dp
                bottom = WdsTheme.dimensions.wdsSpacingHalfPlus // 8dp
            ),
            horizontalArrangement = Arrangement.spacedBy(10.dp), // Gap of 10dp
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = WdsTheme.typography.chatBody1,
                color = WdsTheme.colors.colorContentDefault
            )
            Icon(
                imageVector = Icons.Outlined.Send,
                contentDescription = null,
                tint = WdsTheme.colors.colorContentDeemphasized,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun LinkAttachmentCard(
    attachment: LinkAttachment,
    onCardClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(240.dp) // Less wide
            .height(56.dp) // Same height as logo
            .clip(RoundedCornerShape(12.dp)) // Rounder corners
            .border(2.dp, Color.White, RoundedCornerShape(12.dp)) // 2dp white border
            .background(WdsTheme.colors.colorBackgroundWashInset) // Using WDS gray color
            .clickable(onClick = onCardClick)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // WhatsApp logo/icon - full bleed, fixed size
            Box(
                modifier = Modifier
                    .width(56.dp)
                    .fillMaxHeight()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_whatsapp_logo),
                    contentDescription = "WhatsApp",
                    tint = Color(0xFF25D366), // WhatsApp green tint
                    modifier = Modifier.size(40.dp)
                )
            }

            // Text content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = attachment.title,
                    style = WdsTheme.typography.body2,
                    color = WdsTheme.colors.colorContentDefault,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = attachment.domain,
                    style = WdsTheme.typography.body3,
                    color = WdsTheme.colors.colorContentDeemphasized,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
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

    // Pre-process text to convert markdown bullets to bullet points
    val processedText = text.split("\n").joinToString("\n") { line ->
        val trimmedLine = line.trimStart()
        when {
            trimmedLine.startsWith("* ") -> "• ${trimmedLine.removePrefix("* ")}"
            trimmedLine.startsWith("- ") -> "• ${trimmedLine.removePrefix("- ")}"
            else -> line
        }
    }

    val annotatedString = buildAnnotatedString {
        var currentIndex = 0
        val boldRegex = """\*\*(.*?)\*\*""".toRegex()
        // Updated URL regex to exclude trailing punctuation
        val urlRegex = """https?://[^\s)]+(?=[.!?,;:]?(?:\s|${'$'}))""".toRegex()

        // Find all bold text matches
        val boldMatches = boldRegex.findAll(processedText).toList()
        // Find all URL matches
        val urlMatches = urlRegex.findAll(processedText).toList()

        // Combine and sort all matches by start index
        val allMatches = (boldMatches + urlMatches).sortedBy { it.range.first }

        for (match in allMatches) {
            // Add text before this match
            if (currentIndex < match.range.first) {
                append(processedText.substring(currentIndex, match.range.first))
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
        if (currentIndex < processedText.length) {
            append(processedText.substring(currentIndex))
        }
    }

    // Use Text instead of ClickableText for better line break support
    if (annotatedString.getStringAnnotations("URL", 0, annotatedString.length).isEmpty()) {
        // No URLs, use simple Text
        Text(
            text = annotatedString,
            style = style,
            color = color,
            modifier = modifier
        )
    } else {
        // Has URLs, use ClickableText
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
                        text = "Business assistant",
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
