package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatapp.data.local.entity.ConversationEntity
import com.example.chatapp.data.local.entity.UserEntity
import com.example.chatapp.features.main.MainViewModel
import com.example.chatapp.features.chat.ChatScreen
import com.example.chatapp.features.chatlist.ChatListScreen
import com.example.chatapp.navigation.Screen
import com.example.chatapp.ui.screens.DesignSystemLibraryScreen
import com.example.chatapp.ui.screens.ColorsScreen
import com.example.chatapp.ui.screens.TypeScreen
import com.example.chatapp.ui.screens.ComponentsScreen
import com.example.chatapp.ui.screens.IconsScreen
import com.example.chatapp.features.assistant.AssistantScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import com.example.chatapp.wds.theme.WdsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WdsTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry).value?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = WdsTheme.colors.colorSurfaceDefault,
                    bottomBar = {
                        // Only show bottom bar for main screens (chat_list and assistant)
                        if (currentRoute in listOf("chat_list", Screen.Assistant.route)) {
                            PersistentBottomBar(
                                selectedRoute = currentRoute ?: "chat_list",
                                onChatsClick = {
                                    if (currentRoute != "chat_list") {
                                        navController.navigate("chat_list") {
                                            popUpTo("chat_list") { inclusive = true }
                                        }
                                    }
                                },
                                onAssistantClick = {
                                    if (currentRoute != Screen.Assistant.route) {
                                        navController.navigate(Screen.Assistant.route)
                                    }
                                }
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "chat_list",
                        modifier = Modifier.padding(paddingValues)
                    ) {

                    composable(
                        route = "chat_list",
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { -it / 3 },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it / 3 },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        }
                    ) {
                        ChatListScreen(
                            onChatClick = { conversationId ->
                                navController.navigate("chat/$conversationId")
                            },
                            onDesignLibraryClick = {
                                navController.navigate(Screen.Assistant.route)
                            }
                        )
                    }

                    composable(
                        route = "chat/{conversationId}",
                        arguments = listOf(
                            navArgument("conversationId") { type = NavType.StringType }
                        ),
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        exitTransition = {
                            // When navigating from chat to chat_info
                            slideOutHorizontally(
                                targetOffsetX = { -it / 3 },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        },
                        popEnterTransition = {
                            // When coming back from chat_info to chat
                            slideInHorizontally(
                                initialOffsetX = { -it / 3 },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        }
                    ) { backStackEntry ->
                        val conversationId = backStackEntry.arguments?.getString("conversationId") ?: ""
                        ChatScreen(
                            conversationId = conversationId,
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onChatInfoClick = {
                                navController.navigate("chat_info/$conversationId")
                            }
                        )
                    }

                    composable(
                        route = "chat_info/{conversationId}",
                        arguments = listOf(
                            navArgument("conversationId") { type = NavType.StringType }
                        ),
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        }
                    ) { backStackEntry ->
                        val conversationId = backStackEntry.arguments?.getString("conversationId") ?: ""
                        com.example.chatapp.features.chatinfo.ChatInfoScreen(
                            conversationId = conversationId,
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // Design System Library Routes
                    composable(
                        route = Screen.DesignSystemLibrary.route,
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { -it / 3 },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it / 3 },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        }
                    ) {
                        DesignSystemLibraryScreen(
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToColors = { navController.navigate(Screen.Colors.route) },
                            onNavigateToType = { navController.navigate(Screen.Type.route) },
                            onNavigateToComponents = { navController.navigate(Screen.Components.route) },
                            onNavigateToIcons = { navController.navigate(Screen.Icons.route) }
                        )
                    }

                    composable(
                        route = Screen.Colors.route,
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        }
                    ) {
                        ColorsScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable(
                        route = Screen.Type.route,
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        }
                    ) {
                        TypeScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable(
                        route = Screen.Components.route,
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        }
                    ) {
                        ComponentsScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable(
                        route = Screen.Icons.route,
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        }
                    ) {
                        IconsScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable(
                        route = Screen.Assistant.route,
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeIn(
                                animationSpec = tween(
                                    durationMillis = 150,
                                    delayMillis = 150
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            ) + fadeOut(
                                animationSpec = tween(
                                    durationMillis = 150
                                )
                            )
                        }
                    ) {
                        AssistantScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    }
                }
            }
        }
    }
}

@Composable
private fun PersistentBottomBar(
    selectedRoute: String,
    onChatsClick: () -> Unit,
    onAssistantClick: () -> Unit
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
                selected = selectedRoute == "chat_list",
                onClick = onChatsClick,
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
                        style = if (selectedRoute == "chat_list") WdsTheme.typography.body3InlineLink else WdsTheme.typography.body3Emphasized
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
                        imageVector = androidx.compose.material.icons.Icons.Outlined.Call,
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
                selected = selectedRoute == Screen.Assistant.route,
                onClick = onAssistantClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WdsTheme.colors.colorAccentEmphasized,
                    selectedTextColor = WdsTheme.colors.colorContentDefault,
                    unselectedIconColor = WdsTheme.colors.colorContentDefault,
                    unselectedTextColor = WdsTheme.colors.colorContentDefault,
                    indicatorColor = WdsTheme.colors.colorFilterSurfaceSelected
                ),
                icon = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Outlined.AutoAwesome,
                        contentDescription = "Assistant"
                    )
                },
                label = {
                    Text(
                        text = "Assistant",
                        style = if (selectedRoute == Screen.Assistant.route) WdsTheme.typography.body3InlineLink else WdsTheme.typography.body3Emphasized
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDatabaseTestScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isDatabaseInitialized by viewModel.isDatabaseInitialized.collectAsState()
    val conversations by viewModel.conversations.collectAsState(initial = emptyList())
    val users by viewModel.users.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat Database Test") },
                actions = {
                    TextButton(
                        onClick = { viewModel.resetDatabase() },
                        enabled = !isLoading
                    ) {
                        Text("Reset DB")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                isDatabaseInitialized -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(WdsTheme.dimensions.wdsSpacingDouble),
                        verticalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle)
                    ) {
                        item {
                            DatabaseSummaryCard(
                                userCount = users.size,
                                conversationCount = conversations.size
                            )
                        }

                        item {
                            Text(
                                text = "Recent Conversations",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = WdsTheme.dimensions.wdsSpacingSingle)
                            )
                        }

                        items(conversations.take(10)) { conversation ->
                            ConversationCard(conversation)
                        }
                    }
                }
                else -> {
                    Text(
                        text = "Database not initialized",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun DatabaseSummaryCard(
    userCount: Int,
    conversationCount: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = WdsTheme.colors.colorAccentDeemphasized
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WdsTheme.dimensions.wdsSpacingDouble)
        ) {
            Text(
                text = "Database Summary",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(WdsTheme.dimensions.wdsSpacingSingle))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = userCount.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Users",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = conversationCount.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Conversations",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationCard(conversation: ConversationEntity) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WdsTheme.dimensions.wdsSpacingSinglePlus)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = conversation.title ?: "Direct Message",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                if (conversation.unreadCount > 0) {
                    Badge {
                        Text(conversation.unreadCount.toString())
                    }
                }
            }

            conversation.lastMessageText?.let { lastMessage ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = WdsTheme.colors.colorContentDeemphasized
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(WdsTheme.dimensions.wdsSpacingSingle)) {
                    if (conversation.isPinned) {
                        AssistChip(
                            onClick = { },
                            label = { Text("📌 Pinned") },
                            modifier = Modifier.height(24.dp)
                        )
                    }
                    if (conversation.isMuted) {
                        AssistChip(
                            onClick = { },
                            label = { Text("🔇 Muted") },
                            modifier = Modifier.height(24.dp)
                        )
                    }
                    if (conversation.isGroup) {
                        AssistChip(
                            onClick = { },
                            label = { Text("👥 Group") },
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            }
        }
    }
}
