# Material Icons Guidelines

**IMPORTANT**: When using Material Icons from the Material Icons Extended library.

## Default Icon Settings

**ALWAYS use the Outlined variant with these settings:**

```
'FILL' 0      - Outlined (not filled)
'wght' 400    - Default weight
'GRAD' 0      - Default grade
'opsz' 24     - 24dp optical size
```

## Implementation

### ✅ CORRECT - Use Outlined variant by default

```kotlin
import androidx.compose.material.icons.outlined.*

Icon(
    imageVector = Icons.Outlined.Settings,
    contentDescription = "Settings",
    tint = WdsTheme.colors.colorContentDefault
)

Icon(
    imageVector = Icons.Outlined.PhotoCamera,
    contentDescription = "Camera",
    tint = WdsTheme.colors.colorContentDeemphasized,
    modifier = Modifier.size(24.dp)
)

Icon(
    imageVector = Icons.Outlined.Call,
    contentDescription = "Calls"
)
```

### ❌ WRONG - Don't use Rounded or Filled unless specifically requested

```kotlin
// Don't use these by default
Icon(imageVector = Icons.Rounded.Settings, ...)
Icon(imageVector = Icons.Filled.Settings, ...)
Icon(imageVector = Icons.Sharp.Settings, ...)
```

## Icon Guidelines

- ✅ **Default to Outlined**: Always use `Icons.Outlined.*` unless user specifically requests another variant
- ✅ **Import outlined icons**: Add `import androidx.compose.material.icons.outlined.*` when using icons
- ✅ **24dp size**: Default icon size is 24dp (can be adjusted with `Modifier.size()` if needed)
- ✅ **Use WDS colors**: Always tint icons with `WdsTheme.colors` for consistency
- ✅ **Content description**: Always provide descriptive `contentDescription` for accessibility
- ✅ **Browse icons**: Reference [fonts.google.com/icons](https://fonts.google.com/icons) for available icons

## When to Use Other Variants

Use non-Outlined variants **ONLY** when explicitly requested:

### Filled Icons
Only when user explicitly requests filled icons or for selected/active states:

```kotlin
// Example: Selected state in a tab or button
Icon(
    imageVector = if (isSelected) Icons.Filled.Favorite else Icons.Outlined.Favorite,
    contentDescription = "Favorite"
)
```

### Rounded Icons
Only when user explicitly requests rounded style:

```kotlin
// Only use when specifically asked
Icon(imageVector = Icons.Rounded.Home, ...)
```

### Sharp Icons
Only when user explicitly requests sharp/angular style:

```kotlin
// Only use when specifically asked
Icon(imageVector = Icons.Sharp.Star, ...)
```

## Complete Example

```kotlin
@Composable
fun TopBarWithIcons() {
    val colors = WdsTheme.colors
    
    TopAppBar(
        title = { Text("My Screen") },
        navigationIcon = {
            IconButton(onClick = { /* back */ }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = colors.colorContentDefault
                )
            }
        },
        actions = {
            IconButton(onClick = { /* search */ }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = colors.colorContentDefault
                )
            }
            IconButton(onClick = { /* settings */ }) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = colors.colorContentDefault
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.colorSurfaceDefault
        )
    )
}
```

## Common Material Icons

Here are commonly used icons in the app:

| Icon Name | Usage | Import |
|-----------|-------|--------|
| `PhotoCamera` | Camera actions | `Icons.Outlined.PhotoCamera` |
| `Call` | Phone/call actions | `Icons.Outlined.Call` |
| `Archive` | Archive actions | `Icons.Outlined.Archive` |
| `Settings` | Settings menu | `Icons.Outlined.Settings` |
| `Search` | Search functionality | `Icons.Outlined.Search` |
| `ArrowBack` | Back navigation | `Icons.Outlined.ArrowBack` |
| `MoreVert` | More options menu | `Icons.Outlined.MoreVert` |
| `Send` | Send message | `Icons.AutoMirrored.Outlined.Send` |
| `Palette` | Design library | `Icons.Outlined.Palette` |
| `Share` | Share functionality | `Icons.Outlined.Share` |

## Summary

✅ **Default**: `Icons.Outlined.*` with FILL 0, wght 400, GRAD 0, opsz 24  
✅ **Import**: `import androidx.compose.material.icons.outlined.*`  
✅ **Tint**: Use `WdsTheme.colors` for all icon colors  
✅ **Size**: 24dp default (adjust with `Modifier.size()` if needed)  
✅ **Accessibility**: Always provide `contentDescription`  

❌ **Avoid**: Don't use Filled, Rounded, or Sharp variants unless specifically requested  
❌ **Avoid**: Don't hardcode icon colors - use WdsTheme  
❌ **Avoid**: Don't forget contentDescription for accessibility
