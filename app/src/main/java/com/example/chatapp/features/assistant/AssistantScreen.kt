package com.example.chatapp.features.assistant

import androidx.compose.foundation.BorderStroke
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
import com.example.chatapp.wds.components.WDSButton
import com.example.chatapp.wds.components.WDSButtonAction
import com.example.chatapp.wds.components.WDSButtonSize
import com.example.chatapp.wds.components.WDSButtonVariant
import com.example.chatapp.wds.components.WDSChip
import com.example.chatapp.wds.components.WDSChipSize
import com.example.chatapp.wds.theme.WdsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantScreen(
    onNavigateBack: () -> Unit = {},
    onAdvertiseClick: () -> Unit = {},
    onMetaAIClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WdsTheme.colors.colorChatBackgroundWallpaper,
        topBar = {
            AssistantTopBar(onNavigateBack = onNavigateBack)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onMetaAIClick()
                },
                containerColor = Color(0xFF0084FF),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = "Meta AI Chat"
                )
            }
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
                
                // Use-cases: 6 Capabilities (Business AI, Catalog, Advertise, Orders, Analyze photo, Create image)
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Row 1: Business AI & Catalog
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CapabilityCard(
                                icon = Icons.Outlined.AutoAwesome,
                                title = "Business AI",
                                subtitle = "Get new ideas",
                                modifier = Modifier.weight(1f)
                            )
                            CapabilityCard(
                                icon = Icons.Outlined.Inventory,
                                title = "Catalog",
                                subtitle = "Get new ideas",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Row 2: Advertise & Orders
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CapabilityCard(
                                icon = Icons.Outlined.Campaign,
                                title = "Advertise",
                                subtitle = "Get new ideas",
                                modifier = Modifier.weight(1f)
                            )
                            CapabilityCard(
                                icon = Icons.Outlined.Receipt,
                                title = "Orders",
                                subtitle = "Get new ideas",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Row 3: Analyze photo & Create image
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CapabilityCard(
                                icon = Icons.Outlined.PhotoCamera,
                                title = "Analyze photo",
                                subtitle = "Get new ideas",
                                modifier = Modifier.weight(1f)
                            )
                            CapabilityCard(
                                icon = Icons.Outlined.Image,
                                title = "Create image",
                                subtitle = "Get new ideas",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(23.dp))
                }
                
                // Personalized null state prompts
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Section header with "See all" link
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Ask for anything",
                                style = WdsTheme.typography.body1Emphasized,
                                color = WdsTheme.colors.colorContentDefault
                            )
                            Text(
                                "See all",
                                style = WdsTheme.typography.body2,
                                color = WdsTheme.colors.colorAccentEmphasized,
                                modifier = Modifier.clickable { }
                            )
                        }
                        
                        // Suggested personalized prompts - white cards with 16dp radius
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PersonalizedPromptCard(
                                emoji = "📚",
                                text = "Create videos with AI",
                                timestamp = "How did that workout go? Any...",
                                isNew = true
                            )
                            PersonalizedPromptCard(
                                text = "How do I get more customers?",
                                timestamp = "How did that workout go? Any..."
                            )
                            PersonalizedPromptCard(
                                text = "How do I showcase my products?",
                                timestamp = "How did that workout go? Any..."
                            )
                            PersonalizedPromptCard(
                                text = "How can I stay organized with customer chats?",
                                timestamp = "How did that workout go? Any..."
                            )
                            PersonalizedPromptCard(
                                text = "How do I grow my business?",
                                timestamp = "How did that workout go? Any..."
                            )
                            PersonalizedPromptCard(
                                text = "How do I improve customer satisfaction?",
                                timestamp = "How did that workout go? Any..."
                            )
                            PersonalizedPromptCard(
                                text = "Help me with my homework",
                                timestamp = "How did that workout go? Any..."
                            )
                            PersonalizedPromptCard(
                                text = "Imagine me as an astronaut",
                                timestamp = "How did that workout go? Any..."
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

@Composable
private fun CapabilityCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { },
        shape = RoundedCornerShape(8.dp),
        color = WdsTheme.colors.colorSurfaceDefault,
        border = BorderStroke(1.dp, WdsTheme.colors.colorDivider)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = WdsTheme.colors.colorContentDefault,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = WdsTheme.typography.body2,
                color = WdsTheme.colors.colorContentDefault,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = WdsTheme.typography.body3,
                color = WdsTheme.colors.colorContentDeemphasized,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun PersonalizedPromptCard(
    text: String,
    timestamp: String,
    emoji: String? = null,
    isNew: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Emoji as icon (optional)
            if (emoji != null) {
                Text(
                    text = emoji,
                    style = WdsTheme.typography.headline2,
                    modifier = Modifier.size(40.dp)
                )
            }
            
            // Text content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = text,
                        style = WdsTheme.typography.body2,
                        color = WdsTheme.colors.colorContentDefault,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    if (isNew) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = WdsTheme.colors.colorAccentDeemphasized
                        ) {
                            Text(
                                text = "New",
                                style = WdsTheme.typography.body3,
                                color = WdsTheme.colors.colorAccentEmphasized,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = timestamp,
                    style = WdsTheme.typography.body3,
                    color = WdsTheme.colors.colorContentDeemphasized,
                    maxLines = 1
                )
            }
            
            // Forward arrow
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
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

