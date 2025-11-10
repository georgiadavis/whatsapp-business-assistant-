package com.example.chatapp.features.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.wds.theme.WdsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantChatScreen(
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WdsTheme.colors.colorChatBackgroundWallpaper,
        topBar = {
            AssistantChatTopBar(onNavigateBack = onNavigateBack)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(
                        horizontal = WdsTheme.dimensions.wdsSpacingDouble,
                        vertical = WdsTheme.dimensions.wdsSpacingDouble
                    ),
                    verticalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingDouble)
                ) {
                    item {
                        AIMessageBubble(
                            message = "Hi Gonzales Plumbing, you currently have three broadcasts and two CTWA ads running. Do you want to see how they are performing or something else?",
                            timestamp = "12:30"
                        )
                    }
                    
                    item {
                        SuggestionChip(
                            text = "Get insights on my ads performance",
                            onClick = {}
                        )
                    }
                    
                    item {
                        SuggestionChip(
                            text = "Help me create a new broadcast",
                            onClick = {}
                        )
                    }
                    
                    item {
                        SuggestionChip(
                            text = "Create visuals for my ads",
                            onClick = {}
                        )
                    }
                }
                
                ChatInputBar()
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
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF0084FF),
                                    Color(0xFF00C6FF)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Meta AI",
                            style = WdsTheme.typography.chatHeaderTitle,
                            color = WdsTheme.colors.colorContentDefault
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_meta_ai),
                            contentDescription = "Verified",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Unspecified
                        )
                    }
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

@Composable
private fun AIMessageBubble(
    message: String,
    timestamp: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
                    text = message,
                    style = WdsTheme.typography.body1,
                    color = WdsTheme.colors.colorContentDefault
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = timestamp,
                    style = WdsTheme.typography.body3,
                    color = WdsTheme.colors.colorContentDeemphasized
                )
            }
        }
    }
}

@Composable
private fun SuggestionChip(
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
                modifier = Modifier.weight(1f)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatInputBar() {
    Surface(
        color = WdsTheme.colors.colorSurfaceDefault,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WdsTheme.dimensions.wdsSpacingSingle),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingHalf)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = "Ask anything",
                        style = WdsTheme.typography.body1,
                        color = WdsTheme.colors.colorContentDeemphasized
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = WdsTheme.colors.colorSurfaceEmphasized,
                    unfocusedContainerColor = WdsTheme.colors.colorSurfaceEmphasized,
                    disabledContainerColor = WdsTheme.colors.colorSurfaceEmphasized,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                trailingIcon = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingHalf)
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.AttachFile,
                                contentDescription = "Attach",
                                tint = WdsTheme.colors.colorContentDefault
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.CameraAlt,
                                contentDescription = "Camera",
                                tint = WdsTheme.colors.colorContentDefault
                            )
                        }
                    }
                }
            )
            
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(WdsTheme.colors.colorAccent)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Mic,
                    contentDescription = "Voice",
                    tint = Color.White
                )
            }
        }
    }
}
