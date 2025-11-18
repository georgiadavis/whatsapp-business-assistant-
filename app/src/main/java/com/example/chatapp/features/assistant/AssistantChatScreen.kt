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
import com.example.chatapp.R
import com.example.chatapp.wds.theme.WdsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.drawscope.Stroke

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantChatScreen(
    onNavigateBack: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Track keyboard state
    val imeInsets = WindowInsets.ime
    val density = LocalDensity.current
    val imeHeight = with(density) { imeInsets.getBottom(this).toDp() }
    val isKeyboardOpen = imeHeight > 0.dp

    // Sample messages for Meta AI
    val messages = remember {
        listOf(
            AssistantMessage(
                text = "Hi Gonzales Plumbing, you currently have three broadcasts and two CTWA ads running. Do you want to see how they are performing or something else?",
                timestamp = "12:30"
            )
        )
    }

    // Scroll to bottom on load
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            delay(50)
            listState.scrollToItem(messages.size - 1)
        }
    }

    // Main content
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
                AssistantChatComposer()
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
                    // Suggestion chips first (reversed layout)
                    item {
                        AssistantSuggestionChip(
                            text = "Create visuals for my ads",
                            onClick = {}
                        )
                    }

                    item {
                        AssistantSuggestionChip(
                            text = "Help me create a new broadcast",
                            onClick = {}
                        )
                    }

                    item {
                        AssistantSuggestionChip(
                            text = "Get insights on my ads performance",
                            onClick = {}
                        )
                    }

                    // AI message
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Surface(
                                shape = RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 12.dp,
                                    bottomEnd = 12.dp,
                                    bottomStart = 4.dp
                                ),
                                color = WdsTheme.colors.colorSurfaceDefault,
                                modifier = Modifier.widthIn(max = 300.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(WdsTheme.dimensions.wdsSpacingSingle)
                                ) {
                                    Text(
                                        text = messages[0].text,
                                        style = WdsTheme.typography.body1,
                                        color = WdsTheme.colors.colorContentDefault
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = messages[0].timestamp,
                                        style = WdsTheme.typography.body3,
                                        color = WdsTheme.colors.colorContentDeemphasized
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
                // Meta AI gradient ring icon
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
private fun AssistantChatComposer() {
    val colors = WdsTheme.colors
    val dimensions = WdsTheme.dimensions
    val shapes = WdsTheme.shapes

    var composerText by remember { mutableStateOf("") }

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
            // Message input field with same styling as ChatComposer
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimensions.wdsSpacingHalf),
                shape = shapes.triple, // 24dp border radius
                color = colors.colorBubbleSurfaceIncoming,
                shadowElevation = dimensions.wdsElevationSubtle
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = dimensions.wdsSpacingHalf
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Text field
                    TextField(
                        value = composerText,
                        onValueChange = { composerText = it },
                        modifier = Modifier
                            .weight(1f),
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
                        }
                    )
                }
            }

            // Mic/Send button
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.size(dimensions.wdsTouchTargetComfortable),
                containerColor = colors.colorAccent,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = dimensions.wdsElevationSubtle
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Mic,
                    contentDescription = "Voice"
                )
            }
        }
    }
}

@Composable
private fun AssistantSuggestionChip(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = WdsTheme.shapes.double,
        color = WdsTheme.colors.colorSurfaceDefault,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = WdsTheme.dimensions.wdsSpacingDouble,
                    vertical = WdsTheme.dimensions.wdsSpacingSinglePlus
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = WdsTheme.typography.body1,
                color = WdsTheme.colors.colorContentDefault,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
                tint = WdsTheme.colors.colorContentDeemphasized,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Data class for messages
private data class AssistantMessage(
    val text: String,
    val timestamp: String
)
