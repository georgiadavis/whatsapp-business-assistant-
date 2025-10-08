@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatapp.features.chatlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.example.chatapp.wds.theme.WdsTheme
import com.example.chatapp.wds.theme.BaseColors
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.animation.core.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import com.example.chatapp.R
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.CachePolicy
import kotlin.math.absoluteValue
import androidx.compose.runtime.compositionLocalOf
import com.example.chatapp.wds.components.WDSDivider
import com.example.chatapp.wds.components.WDSContextMenu
import com.example.chatapp.wds.components.WDSContextMenuItem
import androidx.compose.material.icons.outlined.Palette
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import java.time.ZoneId

// Composition local for passing padding values
val LocalPaddingValues = compositionLocalOf<PaddingValues?> { null }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    onChatClick: (String) -> Unit = {},
    viewModel: ChatListViewModel = hiltViewModel(),
    onSearchClick: () -> Unit = {},
    onCameraClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onNewChatClick: () -> Unit = {},
    onMetaAIClick: () -> Unit = {},
    onDesignLibraryClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()



    // Search state
    var searchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedSearchFilter by remember { mutableStateOf<String?>(null) }
    
    // Menu state
    var showMenu by remember { mutableStateOf(false) }
    
    // Log state changes
    LaunchedEffect(uiState.conversations) {
        println("ChatListScreen: UI State updated. Total conversations: ${uiState.conversations.size}")
        uiState.conversations.take(10).forEach { conv ->
            if (conv.id in listOf("conv_3", "conv_5", "conv_8")) {
                println("  ${conv.id}: unreadCount=${conv.unreadCount}, hasUnread=${conv.hasUnread}")
            }
        }
    }
    
    // Filter conversations based on search query
    val filteredConversations = remember(searchQuery, uiState.conversations) {
        if (searchQuery.isEmpty()) {
            uiState.conversations
        } else {
            uiState.conversations.filter { conversation ->
                conversation.title.contains(searchQuery, ignoreCase = true) ||
                conversation.lastMessage.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    // Simplified scaffold without insights/variants
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WdsTheme.colors.colorSurfaceDefault,
        contentColor = WdsTheme.colors.colorContentDefault,
        topBar = {
            // Only show top bar when search is not active
            if (!searchActive) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    ChatListTopBar(
                        onCameraClick = onCameraClick,
                        onMoreClick = { showMenu = true },
                        showInsightsButton = false,
                        onInsightsClick = {},
                        onLogoClick = {}
                    )
                    
                    // Context menu - aligned to top-end to position below the 3-dot button
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 56.dp, end = 8.dp)
                    ) {
                        WDSContextMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            items = listOf(
                                WDSContextMenuItem(
                                    icon = Icons.Outlined.Palette,
                                    text = "Design Library",
                                    onClick = onDesignLibraryClick
                                )
                            )
                        )
                    }
                }
            }
        },
        bottomBar = {
            ChatListBottomBar(
                selectedTab = 0,  // Chats tab is selected
                unreadChats = uiState.conversations.count { it.unreadCount > 0 },
                hasUpdates = false,
                callsBadgeCount = 0,
                onChatsClick = { /* Already on chats */ }
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingDouble)
            ) {
                // Meta AI FAB
                Surface(
                    onClick = onMetaAIClick,
                    color = WdsTheme.colors.colorSurfaceEmphasized,
                    shape = WdsTheme.shapes.singlePlus,
                    tonalElevation = 0.dp,
                    shadowElevation = 6.dp,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_meta_ai),
                            contentDescription = "Meta AI",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                    }
                }

                // New Chat FAB
                FloatingActionButton(
                    onClick = onNewChatClick,
                    containerColor = WdsTheme.colors.colorAccent,
                    contentColor = WdsTheme.colors.colorContentOnAccent
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_create_rounded),
                        contentDescription = "New Chat"
                    )
                }
            }
        }
    ) { paddingValues ->
        CompositionLocalProvider(LocalPaddingValues provides paddingValues) {
            ChatListMainContent(
                listState = listState,
                searchActive = searchActive,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onSearchActiveChange = { searchActive = it },
                selectedSearchFilter = selectedSearchFilter,
                onSearchFilterSelected = { selectedSearchFilter = if (selectedSearchFilter == it) null else it },
                filteredConversations = filteredConversations,
                uiState = uiState,
                viewModel = viewModel,
                onChatClick = onChatClick,
                scope = scope
            )
        }
    }

}

@Composable
private fun ChatListMainContent(
    listState: androidx.compose.foundation.lazy.LazyListState,
    searchActive: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    selectedSearchFilter: String?,
    onSearchFilterSelected: (String) -> Unit,
    filteredConversations: List<ConversationUiModel>,
    uiState: ChatListUiState,
    viewModel: ChatListViewModel,
    onChatClick: (String) -> Unit,
    scope: CoroutineScope
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(WdsTheme.colors.colorSurfaceDefault)
            .padding(LocalPaddingValues.current ?: PaddingValues(0.dp))
        ) {
            // Material 3 Search Bar that transforms to Search View
            item {
                // Get keyboard controller
                val keyboardController = LocalSoftwareKeyboardController.current
                
                // Instant padding switch based on search active state
                val horizontalPadding = if (searchActive) 0.dp else WdsTheme.dimensions.wdsSpacingDouble
                val verticalPadding = if (searchActive) 0.dp else WdsTheme.dimensions.wdsSpacingHalf
                
                DockedSearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    onSearch = {
                        // Handle search submission - dismiss keyboard
                        keyboardController?.hide()
                    },
                    active = searchActive,
                    onActiveChange = onSearchActiveChange,
                    tonalElevation = 0.dp,  // Disable surface tint overlay
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = horizontalPadding,
                            vertical = verticalPadding
                        ),
                    placeholder = {
                        Text(
                            text = "Ask Meta AI or Search",
                            color = WdsTheme.colors.colorContentDeemphasized
                        )
                    },
                    leadingIcon = {
                        if (searchActive) {
                            IconButton(onClick = {
                                onSearchActiveChange(false)
                                onSearchQueryChange("")
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = WdsTheme.colors.colorContentDeemphasized
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = WdsTheme.colors.colorContentDeemphasized
                            )
                        }
                    },
                    trailingIcon = {
                        if (searchActive && searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = WdsTheme.colors.colorContentDeemphasized
                                )
                            }
                        }
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = if (searchActive) WdsTheme.colors.colorSurfaceDefault else WdsTheme.colors.colorSurfaceEmphasized,
                        dividerColor = Color.Transparent
                    ),
                    shape = if (searchActive) RectangleShape else RoundedCornerShape(com.example.chatapp.wds.tokens.BaseDimensions.wdsCornerRadiusCircle)
                ) {
                    // Search results content - full height container
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(WdsTheme.colors.colorSurfaceDefault)
                    ) {
                        // Search filter pills - always show when search is active
                        if (searchActive) {
                            SearchFilterPills(
                                selectedFilter = selectedSearchFilter,
                                onFilterSelected = onSearchFilterSelected
                            )
                            
                            // Divider line with padding
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = WdsTheme.dimensions.wdsSpacingHalf)
                            ) {
                                Divider(
                                    color = WdsTheme.colors.colorDivider,
                                    thickness = WdsTheme.dimensions.wdsBorderWidthThin
                                )
                            }
                        }
                        
                        // Search results only when typing
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (searchQuery.isEmpty()) {
                                // Nothing to show when search is empty
                            } else if (filteredConversations.isEmpty()) {
                                // No results
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No results found",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = WdsTheme.colors.colorContentDeemphasized
                                    )
                                }
                            } else {
                                // Show filtered conversations
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(filteredConversations.size) { index ->
                                        val conversation = filteredConversations[index]
                                        ChatListItem(
                                            conversation = conversation,
                                            onClick = {
                                                onChatClick(conversation.id)
                                                onSearchActiveChange(false)
                                                onSearchQueryChange("")
                                                onSearchFilterSelected("")
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Filter Chips - Instant switch between inbox and search filters
            item {
                if (!searchActive) {
                    FilterChips(
                        selectedFilter = uiState.selectedFilter,
                        onFilterSelected = { viewModel.selectFilter(it) },
                        modifier = Modifier.padding(
                            horizontal = WdsTheme.dimensions.wdsSpacingDouble,
                            vertical = WdsTheme.dimensions.wdsSpacingHalf
                        )
                    )
                }
            }
            
            // Archived Row - Only show when "All" filter is selected and not searching
            if (!searchActive && uiState.archivedCount > 0 && uiState.selectedFilter == ChatFilter.ALL) {
                item {
                    ArchivedRow(
                        count = uiState.archivedCount,
                        onClick = { /* Handle archived click */ }
                    )
                }
            }
            
            // Chat Items
            // Hide main conversation list when search is active
            if (!searchActive) {
                items(
                    items = uiState.conversations,
                    key = { it.id },
                    contentType = { "chat_item" }
                ) { conversation ->
                // Log to verify recomposition with more detail
                LaunchedEffect(conversation) {
                    if (conversation.id in listOf("conv_3", "conv_5", "conv_8")) {
                        println("ChatListScreen: Rendering ${conversation.id} - unreadCount=${conversation.unreadCount}, hasUnread=${conversation.hasUnread}")
                    }
                }
                
                // Also log in the composable itself to ensure we see recomposition
                if (conversation.id in listOf("conv_3", "conv_5", "conv_8")) {
                    SideEffect {
                        println("ChatListScreen: Composing item ${conversation.id} - unreadCount=${conversation.unreadCount}, hasUnread=${conversation.hasUnread}")
                    }
                }
                
                ChatListItem(
                    conversation = conversation,
                    onClick = { 
                        println("ChatListScreen: Clicked on ${conversation.title} with ID: ${conversation.id}")
                        onChatClick(conversation.id) 
                    }
                )
                }
            }
            
            // End-to-end encryption notice - only show when not searching
            if (!searchActive) {
                item {
                    EncryptionNotice()
                }
            }
        }
    }

@Composable
private fun ChatListTopBar(
    onCameraClick: () -> Unit,
    onMoreClick: () -> Unit,
    showInsightsButton: Boolean = false,
    onInsightsClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Icon(
                painter = painterResource(R.drawable.ic_whatsapp_text_logo),
                contentDescription = "WhatsApp",
                modifier = Modifier
                    .height(21.dp)
                    .clickable { onLogoClick() },
                tint = Color.Unspecified
            )
        },
        actions = {
            // Insights button (only shown for backdrop variant)
            if (showInsightsButton) {
                IconButton(onClick = onInsightsClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_insights),
                        contentDescription = "Insights",
                        tint = WdsTheme.colors.colorContentDefault
                    )
                }
            }

            IconButton(onClick = onCameraClick) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = "Camera",
                        tint = WdsTheme.colors.colorContentDefault
                    )
                }
            IconButton(onClick = onMoreClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
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

// Old SearchBar removed - using Material 3 SearchBar component instead

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterChips(
    selectedFilter: ChatFilter,
    onFilterSelected: (ChatFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle)
    ) {
        // All filter
        FilterChip(
            selected = selectedFilter == ChatFilter.ALL,
            onClick = { onFilterSelected(ChatFilter.ALL) },
            label = { Text("All") },
            shape = RoundedCornerShape(com.example.chatapp.wds.tokens.BaseDimensions.wdsCornerRadiusCircle),
            border = BorderStroke(WdsTheme.dimensions.wdsBorderWidthThin, WdsTheme.colors.colorOutlineDeemphasized),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = WdsTheme.colors.colorFilterSurfaceSelected,
                selectedLabelColor = WdsTheme.colors.colorContentActionEmphasized,
                labelColor = WdsTheme.colors.colorContentDeemphasized
            )
        )

        // Unread filter
        FilterChip(
            selected = selectedFilter == ChatFilter.UNREAD,
            onClick = { onFilterSelected(ChatFilter.UNREAD) },
            label = { Text("Unread") },
            shape = RoundedCornerShape(com.example.chatapp.wds.tokens.BaseDimensions.wdsCornerRadiusCircle),
            border = BorderStroke(WdsTheme.dimensions.wdsBorderWidthThin, WdsTheme.colors.colorOutlineDeemphasized),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = WdsTheme.colors.colorFilterSurfaceSelected,
                selectedLabelColor = WdsTheme.colors.colorContentActionEmphasized,
                labelColor = WdsTheme.colors.colorContentDeemphasized
            )
        )

        // Favorites filter
        FilterChip(
            selected = selectedFilter == ChatFilter.FAVORITES,
            onClick = { onFilterSelected(ChatFilter.FAVORITES) },
            label = { Text("Favorites") },
            shape = RoundedCornerShape(com.example.chatapp.wds.tokens.BaseDimensions.wdsCornerRadiusCircle),
            border = BorderStroke(WdsTheme.dimensions.wdsBorderWidthThin, WdsTheme.colors.colorOutlineDeemphasized),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = WdsTheme.colors.colorFilterSurfaceSelected,
                selectedLabelColor = WdsTheme.colors.colorContentActionEmphasized,
                labelColor = WdsTheme.colors.colorContentDeemphasized
            )
        )

        // Groups filter
        FilterChip(
            selected = selectedFilter == ChatFilter.GROUPS,
            onClick = { onFilterSelected(ChatFilter.GROUPS) },
            label = { Text("Groups") },
            shape = RoundedCornerShape(com.example.chatapp.wds.tokens.BaseDimensions.wdsCornerRadiusCircle),
            border = BorderStroke(WdsTheme.dimensions.wdsBorderWidthThin, WdsTheme.colors.colorOutlineDeemphasized),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = WdsTheme.colors.colorFilterSurfaceSelected,
                selectedLabelColor = WdsTheme.colors.colorContentActionEmphasized,
                labelColor = WdsTheme.colors.colorContentDeemphasized
            )
        )
    }
}

@Composable
private fun ArchivedRow(
    count: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                horizontal = WdsTheme.dimensions.wdsSpacingDouble,
                vertical = WdsTheme.dimensions.wdsSpacingHalf
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingDouble)
    ) {
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Archive,
                contentDescription = "Archived",
                tint = WdsTheme.colors.colorContentDeemphasized,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Text(
            text = "Archived",
            style = WdsTheme.typography.body1Emphasized,
            color = WdsTheme.colors.colorContentDeemphasized,
            modifier = Modifier.weight(1f)
        )
        
        if (count > 0) {
            Text(
                text = count.toString(),
                style = WdsTheme.typography.body3Emphasized,
                color = WdsTheme.colors.colorContentDeemphasized
            )
        }
    }
}

@Composable
private fun Avatar(
    avatarUrl: String?,
    title: String,
    modifier: Modifier = Modifier
) {
    var imageLoadFailed by remember { mutableStateOf(false) }
    
    if (avatarUrl != null && !imageLoadFailed) {
        when {
            avatarUrl.startsWith("drawable://") -> {
                // Handle local drawable resources
                val drawableResId = when (avatarUrl.removePrefix("drawable://")) {
                    "emoji_thinking_monocle" -> R.drawable.emoji_thinking_monocle
                    "emoji_microscope" -> R.drawable.emoji_microscope
                    else -> null
                }
                
                if (drawableResId != null) {
                    Image(
                        painter = painterResource(id = drawableResId),
                        contentDescription = "Profile picture of $title",
                        modifier = modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(WdsTheme.colors.colorSurfaceEmphasized, CircleShape),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    DefaultAvatar(title = title, modifier = modifier)
                }
            }
            else -> {
                // Handle network URLs
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarUrl)
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .networkCachePolicy(CachePolicy.ENABLED)
                        .allowHardware(true)
                        .build(),
                    contentDescription = "Profile picture of $title",
                    modifier = modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    onError = {
                        imageLoadFailed = true
                    }
                )
            }
        }
    } else {
        // Fallback to solid color avatar with initials
        DefaultAvatar(title = title, modifier = modifier)
    }
}

@Composable
private fun EnhancedAvatar(
    title: String,
    seed: String,
    modifier: Modifier = Modifier
) {
    // Generate gradient colors based on seed for variety
    val colors = generateGradientColors(seed)
    val initials = generateInitials(title)
    
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(0f, 0f),
                    end = Offset(100f, 100f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = WdsTheme.typography.headline2,
            color = WdsTheme.colors.colorAlwaysWhite
        )
    }
}

private fun generateInitials(name: String): String {
    val parts = name.trim().split(" ")
    return when {
        parts.size >= 2 -> "${parts[0].take(1)}${parts[1].take(1)}".uppercase()
        parts[0].length >= 2 -> parts[0].take(2).uppercase()
        else -> parts[0].take(1).uppercase()
    }
}

@Composable
private fun generateGradientColors(seed: String): List<Color> {
    val hash = seed.hashCode().absoluteValue
    val colors = WdsTheme.colors

    val colorSchemes = listOf(
        // Using WDS semantic colors for avatar gradients
        listOf(colors.colorAccentEmphasized, colors.colorAccentDeemphasized),
        listOf(colors.colorPositive, colors.colorAccentEmphasized),
        listOf(colors.colorAccentEmphasized, colors.colorPositive),
        listOf(colors.colorWarning, colors.colorAccentEmphasized),
        listOf(colors.colorAccentDeemphasized, colors.colorPositive),
        listOf(colors.colorPositive, colors.colorAccentDeemphasized),
        listOf(colors.colorAccentEmphasized, colors.colorWarning),
        listOf(colors.colorWarning, colors.colorPositive)
    )

    return colorSchemes[hash % colorSchemes.size]
}

@Composable
private fun DefaultAvatar(title: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape),
        color = WdsTheme.colors.colorSurfaceEmphasized
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title.take(1).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = WdsTheme.colors.colorContentDefault
            )
        }
    }
}

private fun generateColorFromName(name: String): Color {
    val colors = listOf(
        BaseColors.wdsBrown500,
        BaseColors.wdsCobalt500,
        BaseColors.wdsCoolGray500,
        BaseColors.wdsCream500,
        BaseColors.wdsEmerald500,
        BaseColors.wdsGreen500,
        BaseColors.wdsNeutralGray500,
        BaseColors.wdsOrange500,
        BaseColors.wdsPink500,
        BaseColors.wdsPurple500,
        BaseColors.wdsRed500,
        BaseColors.wdsSkyBlue500,
        BaseColors.wdsTeal500,
        BaseColors.wdsWarmGray500,
        BaseColors.wdsYellow500
    )

    val index = name.hashCode().absoluteValue % colors.size
    return colors[index]
}

private fun isColorDark(color: Color): Boolean {
    // Simple brightness calculation
    val brightness = (color.red * 299 + color.green * 587 + color.blue * 114) / 1000
    return brightness < 128
}

@Composable
private fun ChatListItem(
    conversation: ConversationUiModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                horizontal = WdsTheme.dimensions.wdsSpacingDouble,
                vertical = WdsTheme.dimensions.wdsSpacingSinglePlus
            ),
        horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingDouble)
    ) {
        // Avatar - Now displays real profile images or falls back to initials
        Avatar(
            avatarUrl = conversation.avatarUrl,
            title = conversation.title
        )

        // Content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingHalf)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingQuarter)
                ) {
                    Text(
                        text = conversation.title,
                        style = WdsTheme.typography.body1Emphasized,
                        color = WdsTheme.colors.colorContentDefault,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    // Show subtitle if present (for disambiguation)
                    conversation.subtitle?.let { subtitle ->
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = WdsTheme.colors.colorContentDeemphasized,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                Text(
                    text = formatTime(conversation.lastMessageTime),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (conversation.hasUnread) WdsTheme.colors.colorAccent else WdsTheme.colors.colorContentDeemphasized
                )
            }
            
            // Message Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingHalf),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status icon (if sent by user)
                if (conversation.isSentByUser) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = if (conversation.isRead) WdsTheme.colors.colorContentRead else WdsTheme.colors.colorContentDeemphasized,
                        modifier = Modifier.size(16.dp)
                    )
                }
                
                // Sender name (for groups)
                if (conversation.isGroup && conversation.lastMessageSender != null) {
                    Text(
                        text = "${conversation.lastMessageSender}:",
                        style = MaterialTheme.typography.bodySmall,
                        color = WdsTheme.colors.colorContentDeemphasized
                    )
                }
                
                // Message content
                when (conversation.lastMessageType) {
                    MessageType.PHOTO -> {
                        Icon(
                            painter = painterResource(R.drawable.ic_photo_message),
                            contentDescription = null,
                            tint = WdsTheme.colors.colorContentDeemphasized,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Photo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = WdsTheme.colors.colorContentDeemphasized,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    MessageType.VIDEO -> {
                        Icon(
                            imageVector = Icons.Outlined.Videocam,
                            contentDescription = null,
                            tint = WdsTheme.colors.colorContentDeemphasized,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Video",
                            style = MaterialTheme.typography.bodyMedium,
                            color = WdsTheme.colors.colorContentDeemphasized,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    MessageType.STICKER -> {
                        Icon(
                            painter = painterResource(R.drawable.ic_sticker_message),
                            contentDescription = null,
                            tint = WdsTheme.colors.colorContentDeemphasized,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Sticker",
                            style = MaterialTheme.typography.bodyMedium,
                            color = WdsTheme.colors.colorContentDeemphasized,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    MessageType.FILE, MessageType.DOCUMENT -> {
                        Icon(
                            painter = painterResource(R.drawable.ic_file_message),
                            contentDescription = null,
                            tint = WdsTheme.colors.colorContentDeemphasized,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = conversation.lastMessage
                                .removePrefix("📎 ")
                                .removePrefix("🔗 ")
                                .ifEmpty { "Document" },
                            style = MaterialTheme.typography.bodyMedium,
                            color = WdsTheme.colors.colorContentDeemphasized,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    MessageType.VOICE_CALL, MessageType.VIDEO_CALL -> {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = if (conversation.isMissedCall) WdsTheme.colors.colorNegative else WdsTheme.colors.colorContentDeemphasized,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (conversation.isMissedCall) "Missed Call" else "Call",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (conversation.isMissedCall) WdsTheme.colors.colorNegative else WdsTheme.colors.colorContentDeemphasized,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    else -> {
                        Text(
                            text = conversation.lastMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = WdsTheme.colors.colorContentDeemphasized,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Unread badge
                if (conversation.unreadCount > 0) {
                    Surface(
                        shape = CircleShape,
                        color = WdsTheme.colors.colorAccent
                    ) {
                        Text(
                            text = conversation.unreadCount.toString(),
                            style = WdsTheme.typography.body3Emphasized,
                            color = WdsTheme.colors.colorContentOnAccent,
                            modifier = Modifier.padding(horizontal = WdsTheme.dimensions.wdsSpacingHalfPlus, vertical = WdsTheme.dimensions.wdsSpacingQuarter)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatListBottomBar(
    selectedTab: Int,
    unreadChats: Int,
    hasUpdates: Boolean,
    callsBadgeCount: Int,
    onChatsClick: () -> Unit = {}
) {
    Column {
        WDSDivider()
        NavigationBar(
            containerColor = WdsTheme.colors.colorSurfaceDefault,
            tonalElevation = 0.dp  // Disable surface tint overlay
        ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = onChatsClick,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = WdsTheme.colors.colorAccentEmphasized,
                selectedTextColor = WdsTheme.colors.colorContentDefault,
                unselectedIconColor = WdsTheme.colors.colorContentDefault,
                unselectedTextColor = WdsTheme.colors.colorContentDefault,
                indicatorColor = WdsTheme.colors.colorFilterSurfaceSelected
            ),
            icon = {
                BadgedBox(
                    badge = {
                        if (unreadChats > 0) {
                            Badge(
                                containerColor = WdsTheme.colors.colorAccent,
                                contentColor = WdsTheme.colors.colorContentOnAccent
                            ) {
                                Text(
                                    text = unreadChats.toString(),
                                    style = WdsTheme.typography.body3Emphasized,
                                    color = WdsTheme.colors.colorContentOnAccent
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_chats_rounded),
                        contentDescription = "Chats"
                    )
                }
            },
            label = {
                Text(
                    text = "Chats",
                    style = if (selectedTab == 0) WdsTheme.typography.body3InlineLink else WdsTheme.typography.body3Emphasized
                )
            }
        )
        
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = WdsTheme.colors.colorAccentEmphasized,
                selectedTextColor = WdsTheme.colors.colorContentDefault,
                unselectedIconColor = WdsTheme.colors.colorContentDefault,
                unselectedTextColor = WdsTheme.colors.colorContentDefault,
                indicatorColor = WdsTheme.colors.colorFilterSurfaceSelected
            ),
            icon = {
                BadgedBox(
                    badge = {
                        if (hasUpdates) {
                            Badge(
                                modifier = Modifier.size(8.dp),
                                containerColor = WdsTheme.colors.colorAccent
                            )
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_updates_rounded),
                        contentDescription = "Updates"
                    )
                }
            },
            label = {
                Text(
                    text = "Updates",
                    style = if (selectedTab == 1) WdsTheme.typography.body3InlineLink else WdsTheme.typography.body3Emphasized
                )
            }
        )
        
        NavigationBarItem(
            selected = selectedTab == 2,
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
                    painter = painterResource(R.drawable.ic_communities_rounded),
                    contentDescription = "Communities"
                )
            },
            label = {
                Text(
                    text = "Communities",
                    style = if (selectedTab == 2) WdsTheme.typography.body3InlineLink else WdsTheme.typography.body3Emphasized
                )
            }
        )
        
        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = WdsTheme.colors.colorAccentEmphasized,
                selectedTextColor = WdsTheme.colors.colorContentDefault,
                unselectedIconColor = WdsTheme.colors.colorContentDefault,
                unselectedTextColor = WdsTheme.colors.colorContentDefault,
                indicatorColor = WdsTheme.colors.colorFilterSurfaceSelected
            ),
            icon = {
                BadgedBox(
                    badge = {
                        if (callsBadgeCount > 0) {
                            Badge(
                                containerColor = WdsTheme.colors.colorAccent,
                                contentColor = WdsTheme.colors.colorContentOnAccent
                            ) {
                                Text(
                                    text = callsBadgeCount.toString(),
                                    style = WdsTheme.typography.body3Emphasized,
                                    color = WdsTheme.colors.colorContentOnAccent
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Call,
                        contentDescription = "Calls"
                    )
                }
            },
            label = {
                Text(
                    text = "Calls",
                    style = if (selectedTab == 3) WdsTheme.typography.body3InlineLink else WdsTheme.typography.body3Emphasized
                )
            }
        )
        }
    }
}

@Composable
private fun SearchFilterPills(
    selectedFilter: String?,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf("Photos", "Videos", "Links", "GIFs", "Audio", "Documents")
    
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = WdsTheme.dimensions.wdsSpacingHalf),
        horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle),
        contentPadding = PaddingValues(horizontal = WdsTheme.dimensions.wdsSpacingDouble)
    ) {
        items(filters) { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                shape = RoundedCornerShape(com.example.chatapp.wds.tokens.BaseDimensions.wdsCornerRadiusCircle),
                border = BorderStroke(WdsTheme.dimensions.wdsBorderWidthThin, WdsTheme.colors.colorOutlineDeemphasized),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = WdsTheme.colors.colorFilterSurfaceSelected,
                    selectedLabelColor = WdsTheme.colors.colorContentActionEmphasized,
                    labelColor = WdsTheme.colors.colorContentDeemphasized
                )
            )
        }
    }
}

@Composable
private fun EncryptionNotice() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WdsTheme.dimensions.wdsSpacingDouble),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = WdsTheme.colors.colorContentDeemphasized,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(WdsTheme.dimensions.wdsSpacingHalf))
        Text(
            text = "Your personal messages are ",
            style = MaterialTheme.typography.labelSmall,
            color = WdsTheme.colors.colorContentDeemphasized
        )
        Text(
            text = "end-to-end encrypted",
            style = MaterialTheme.typography.labelSmall,
            color = WdsTheme.colors.colorPositive
        )
    }
    
    Divider(
        color = WdsTheme.colors.colorDivider,
        thickness = 0.5.dp
    )
}

private fun formatTime(instant: Instant): String {
    val localDateTime = LocalDateTime.ofInstant(instant.toJavaInstant(), ZoneId.systemDefault())
    val now = LocalDateTime.now()
    
    return when {
        localDateTime.toLocalDate() == now.toLocalDate() -> {
            // Today - show time
            localDateTime.format(DateTimeFormatter.ofPattern("h:mm"))
        }
        localDateTime.toLocalDate() == now.toLocalDate().minusDays(1) -> {
            // Yesterday
            "Yesterday"
        }
        else -> {
            // Show date
            localDateTime.format(DateTimeFormatter.ofPattern("M/d/yy"))
        }
    }
}

// UI Models and Enums
data class ConversationUiModel(
    val id: String,
    val title: String,
    val subtitle: String? = null, // For disambiguation when names match
    val avatarUrl: String?,
    val lastMessage: String,
    val lastMessageTime: Instant,
    val lastMessageSender: String? = null,
    val lastMessageType: MessageType = MessageType.TEXT,
    val unreadCount: Int = 0,
    val isGroup: Boolean = false,
    val isSentByUser: Boolean = false,
    val isRead: Boolean = false,
    val hasUnread: Boolean = false,
    val isMissedCall: Boolean = false,
    val isPinned: Boolean = false
)

enum class ChatFilter {
    ALL, UNREAD, FAVORITES, GROUPS
}

enum class MessageType {
    TEXT, PHOTO, VIDEO, AUDIO, DOCUMENT, LOCATION, VOICE_CALL, VIDEO_CALL, FILE, STICKER, GIF, VOICE_NOTE
}
