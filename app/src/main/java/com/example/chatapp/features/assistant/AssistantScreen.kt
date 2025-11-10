package com.example.chatapp.features.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.wds.theme.WdsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantScreen(
    onNavigateBack: () -> Unit = {},
    onAdvertiseClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WdsTheme.colors.colorChatBackgroundWallpaper,
        topBar = {
            AssistantTopBar(onNavigateBack = onNavigateBack)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(WdsTheme.dimensions.wdsSpacingDouble)
            ) {
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
                
                // Greeting
                item {
                    Text(
                        text = "Hello, Leslie",
                        style = WdsTheme.typography.headline1,
                        color = WdsTheme.colors.colorContentDefault,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(WdsTheme.dimensions.wdsSpacingQuad))
                }
                
                // Quick Actions Card
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = WdsTheme.dimensions.wdsSpacingDouble),
                        shape = WdsTheme.shapes.double,
                        color = WdsTheme.colors.colorSurfaceDefault,
                        shadowElevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(WdsTheme.dimensions.wdsSpacingDouble)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle)
                            ) {
                                QuickActionButton(
                                    icon = Icons.Outlined.AutoAwesome,
                                    label = "Business AI",
                                    iconTint = Color(0xFF10A37F),
                                    modifier = Modifier.weight(1f),
                                    onClick = {}
                                )
                                QuickActionButton(
                                    icon = Icons.Outlined.Inventory,
                                    label = "Catalog",
                                    iconTint = Color(0xFF1E88E5),
                                    modifier = Modifier.weight(1f),
                                    onClick = {}
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(WdsTheme.dimensions.wdsSpacingSingle))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle)
                            ) {
                                QuickActionButton(
                                    icon = Icons.Outlined.Campaign,
                                    label = "Advertise",
                                    iconTint = Color(0xFF7C3AED),
                                    modifier = Modifier.weight(1f),
                                    onClick = onAdvertiseClick
                                )
                                QuickActionButton(
                                    icon = Icons.Outlined.Receipt,
                                    label = "Orders",
                                    iconTint = Color(0xFFEC4899),
                                    modifier = Modifier.weight(1f),
                                    onClick = {}
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(WdsTheme.dimensions.wdsSpacingDouble))
                }
                
                // Suggested Prompts
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = WdsTheme.dimensions.wdsSpacingDouble),
                        shape = WdsTheme.shapes.double,
                        color = WdsTheme.colors.colorSurfaceDefault,
                        shadowElevation = 2.dp
                    ) {
                        Column {
                            SuggestedPromptItem(
                                emoji = "🏷️",
                                text = "Labels",
                                showDivider = true,
                                onClick = {}
                            )
                            SuggestedPromptItem(
                                emoji = "💬",
                                text = "Greeting message",
                                showDivider = true,
                                onClick = {}
                            )
                            SuggestedPromptItem(
                                emoji = "📤",
                                text = "Away message",
                                showDivider = true,
                                onClick = {}
                            )
                            SuggestedPromptItem(
                                emoji = "⚡",
                                text = "Quick replies",
                                showDivider = true,
                                onClick = {}
                            )
                            SuggestedPromptItem(
                                emoji = "👤",
                                text = "Manage profile",
                                showDivider = true,
                                onClick = {}
                            )
                            SuggestedPromptItem(
                                emoji = "📱",
                                text = "Facebook & Instagram",
                                showDivider = false,
                                onClick = {}
                            )
                        }
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssistantTopBar(
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle)
            ) {
                // Meta AI Logo
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
private fun QuickActionButton(
    icon: ImageVector,
    label: String,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = WdsTheme.shapes.single,
        color = WdsTheme.colors.colorSurfaceEmphasized
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = WdsTheme.dimensions.wdsSpacingSingle),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingHalf)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = label,
                style = WdsTheme.typography.body2,
                color = WdsTheme.colors.colorContentDefault
            )
        }
    }
}

@Composable
private fun AssistantBottomBar(
    onNavigateBack: () -> Unit
) {
    Column {
        Divider(
            color = WdsTheme.colors.colorDivider,
            thickness = WdsTheme.dimensions.wdsBorderWidthThin
        )
        NavigationBar(
            containerColor = WdsTheme.colors.colorSurfaceDefault,
            tonalElevation = 0.dp
        ) {
            NavigationBarItem(
                selected = false,
                onClick = onNavigateBack,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WdsTheme.colors.colorAccentEmphasized,
                    selectedTextColor = WdsTheme.colors.colorContentDefault,
                    unselectedIconColor = WdsTheme.colors.colorContentDefault,
                    unselectedTextColor = WdsTheme.colors.colorContentDefault,
                    indicatorColor = WdsTheme.colors.colorFilterSurfaceSelected
                ),
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_chats_rounded),
                        contentDescription = "Chats"
                    )
                },
                label = {
                    Text(
                        text = "Chats",
                        style = WdsTheme.typography.body3Emphasized
                    )
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WdsTheme.colors.colorAccentEmphasized,
                    selectedTextColor = WdsTheme.colors.colorContentDefault,
                    unselectedIconColor = WdsTheme.colors.colorContentDefault,
                    unselectedTextColor = WdsTheme.colors.colorContentDefault,
                    indicatorColor = WdsTheme.colors.colorFilterSurfaceSelected
                ),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Call,
                        contentDescription = "Calls"
                    )
                },
                label = {
                    Text(
                        text = "Calls",
                        style = WdsTheme.typography.body3Emphasized
                    )
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WdsTheme.colors.colorAccentEmphasized,
                    selectedTextColor = WdsTheme.colors.colorContentDefault,
                    unselectedIconColor = WdsTheme.colors.colorContentDefault,
                    unselectedTextColor = WdsTheme.colors.colorContentDefault,
                    indicatorColor = WdsTheme.colors.colorFilterSurfaceSelected
                ),
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_updates_rounded),
                        contentDescription = "Updates"
                    )
                },
                label = {
                    Text(
                        text = "Updates",
                        style = WdsTheme.typography.body3Emphasized
                    )
                }
            )

            NavigationBarItem(
                selected = true,
                onClick = { },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WdsTheme.colors.colorAccentEmphasized,
                    selectedTextColor = WdsTheme.colors.colorContentDefault,
                    unselectedIconColor = WdsTheme.colors.colorContentDefault,
                    unselectedTextColor = WdsTheme.colors.colorContentDefault,
                    indicatorColor = WdsTheme.colors.colorFilterSurfaceSelected
                ),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = "Assistant"
                    )
                },
                label = {
                    Text(
                        text = "Assistant",
                        style = WdsTheme.typography.body3InlineLink
                    )
                }
            )
        }
    }
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

@Composable
private fun SuggestedPromptItem(
    emoji: String,
    text: String,
    showDivider: Boolean,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(
                    horizontal = WdsTheme.dimensions.wdsSpacingDouble,
                    vertical = WdsTheme.dimensions.wdsSpacingSinglePlus
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle)
            ) {
                Text(
                    text = emoji,
                    style = WdsTheme.typography.headline2,
                    modifier = Modifier.size(40.dp),
                )
                Text(
                    text = text,
                    style = WdsTheme.typography.body1,
                    color = WdsTheme.colors.colorContentDefault
                )
            }
        }

        if (showDivider) {
            Divider(
                modifier = Modifier.padding(horizontal = WdsTheme.dimensions.wdsSpacingDouble),
                color = WdsTheme.colors.colorDivider,
                thickness = WdsTheme.dimensions.wdsBorderWidthThin
            )
        }
    }
}
