# Development Principles

## Code Style and Conventions

- Follow existing Kotlin conventions and patterns in the codebase
- Use Compose's declarative UI patterns consistently
- Maintain single responsibility principle in ViewModels and Repositories
- Keep UI components pure and stateless where possible
- Use Flow for reactive data streams, suspend functions for one-time operations

## Import Organization

Organize imports in this order:
1. Android/AndroidX imports
2. Compose imports (animation, foundation, material, runtime, ui)
3. Navigation imports
4. Project-specific imports (navigation, screens, theme, components)

Use explicit imports, not wildcards.

## Database Operations

- Always use Repository layer - never access DAOs directly from ViewModels
- Maintain referential integrity through proper foreign key relationships
- Use transactions for multi-table operations to ensure consistency
- Implement soft deletes for messages to preserve conversation history
- Handle database migrations carefully to preserve user data

## UI/UX Principles

- Maintain WhatsApp and Material 3 design consistency throughout the app
- When WhatsApp pattern exists, prefer that over Material 3
- Preserve WhatsApp design patterns for user familiarity
- Ensure smooth animations and transitions using Compose
- Handle loading and error states gracefully in all screens
- Support both light and dark themes consistently

## Performance Considerations

1. **Cache theme lookups** at the start of composables
2. **Use `remember`** for static lists and data
3. **Extract large composables** into separate functions
4. **Use `key` parameter** in `LazyColumn`/`LazyRow` items
5. **Avoid creating objects** inside composable lambdas
6. Use lazy loading for conversation lists and messages
7. Implement pagination for large message histories
8. Cache user avatars and media efficiently using Coil
9. Minimize recompositions by using stable data classes
10. Use remember and derivedStateOf appropriately in Composables

Example:
```kotlin
@Composable
fun OptimizedScreen() {
    val colors = WdsTheme.colors // Cache
    val dimensions = WdsTheme.dimensions

    val items = remember { // Memoize static data
        listOf("Item 1", "Item 2", "Item 3")
    }

    LazyColumn {
        itemsIndexed(
            items = items,
            key = { index, item -> item } // Add keys
        ) { index, item ->
            ItemRow(item = item) // Extract composable
        }
    }
}
```

## Security and Privacy

- Never log sensitive user data or message content
- Implement proper data validation at repository level
- Handle user permissions appropriately for media access
- Prepare for end-to-end encryption implementation
- Sanitize all user inputs before database operations

## Testing Strategy

- Write unit tests for ViewModels and Repository methods
- Test Room DAOs with instrumented tests
- Verify Compose UI behavior with UI tests
- Mock database operations in ViewModel tests
- Ensure proper error handling in all test scenarios
