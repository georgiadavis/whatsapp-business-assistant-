# Testing and Previews

## Preview Functions

Always add preview functions for new composables:

```kotlin
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun MyScreenPreviewLight() {
    WdsTheme(darkTheme = false) {
        MyScreen(onNavigateBack = {})
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun MyScreenPreviewDark() {
    WdsTheme(darkTheme = true) {
        MyScreen(onNavigateBack = {})
    }
}
```

## Preview Best Practices

- Always include both light and dark mode previews
- Use `showBackground = true` to see the background color
- Provide meaningful names for previews
- Wrap content in `WdsTheme` to ensure theme is applied
- Use sample data for previews, not production data
- Create multiple preview variants for different states (loading, error, empty, filled)

## Example: Multiple State Previews

```kotlin
@Preview(showBackground = true, name = "Empty State")
@Composable
fun MyScreenPreviewEmpty() {
    WdsTheme {
        MyScreen(
            items = emptyList(),
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun MyScreenPreviewLoading() {
    WdsTheme {
        MyScreen(
            isLoading = true,
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun MyScreenPreviewError() {
    WdsTheme {
        MyScreen(
            error = "Something went wrong",
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Filled State")
@Composable
fun MyScreenPreviewFilled() {
    WdsTheme {
        MyScreen(
            items = listOf("Item 1", "Item 2", "Item 3"),
            onNavigateBack = {}
        )
    }
}
```
