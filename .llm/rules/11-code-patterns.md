# Common Code Patterns

## Screen Structure Pattern

When creating new screens, follow this pattern:

```kotlin
@Composable
fun MyScreen(
    onNavigateBack: () -> Unit,
    // Other navigation callbacks
) {
    val colors = WdsTheme.colors
    val dimensions = WdsTheme.dimensions
    val typography = WdsTheme.typography

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Screen Title", style = typography.headline2) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.colorContentDefault
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.colorSurfaceDefault,
                    titleContentColor = colors.colorContentDefault
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimensions.wdsSpacingTriple),
            verticalArrangement = Arrangement.spacedBy(dimensions.wdsSpacingDouble)
        ) {
            // Content here
        }
    }
}
```

## List with Dividers

```kotlin
LazyColumn {
    itemsIndexed(items) { index, item ->
        ItemContent(item)
        if (index < items.lastIndex) {
            WDSDivider()
        }
    }
}
```

## Section Headers

```kotlin
Text(
    text = "SECTION HEADER",
    style = WdsTheme.typography.caption1.copy(
        fontWeight = FontWeight.Medium
    ),
    color = WdsTheme.colors.colorContentDeemphasized,
    modifier = Modifier.padding(
        vertical = WdsTheme.dimensions.wdsSpacingSingle
    )
)
```

## Card/Surface Pattern

```kotlin
Surface(
    modifier = Modifier.fillMaxWidth(),
    color = WdsTheme.colors.colorSurfaceElevatedDefault,
    shape = WdsTheme.shapes.double
) {
    Column(
        modifier = Modifier.padding(WdsTheme.dimensions.wdsSpacingDouble)
    ) {
        // Content
    }
}
```

## Navigation Pattern

```kotlin
// In Screen.kt
sealed class Screen(val route: String) {
    object MyNewScreen : Screen("my_new_screen")
}

// In MainActivity.kt AppNavigation
composable(Screen.MyNewScreen.route) {
    MyNewScreen(
        onNavigateBack = { navController.popBackStack() }
    )
}
```
