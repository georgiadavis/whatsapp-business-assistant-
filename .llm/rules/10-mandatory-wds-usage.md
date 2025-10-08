# MANDATORY: Always Use WdsTheme

**NEVER use hardcoded values. ALWAYS use design system tokens.**

## Rule 1: Use WdsTheme for All Styling

❌ **WRONG:**
```kotlin
Text(
    text = "Hello",
    color = Color(0xFF000000),
    fontSize = 16.sp
)
Box(
    modifier = Modifier
        .padding(16.dp)
        .background(Color.White, RoundedCornerShape(8.dp))
)
```

✅ **CORRECT:**
```kotlin
Text(
    text = "Hello",
    style = WdsTheme.typography.body1,
    color = WdsTheme.colors.colorContentDefault
)
Box(
    modifier = Modifier
        .padding(WdsTheme.dimensions.wdsSpacingDouble)
        .background(
            WdsTheme.colors.colorSurfaceDefault,
            WdsTheme.shapes.single
        )
)
```

## Rule 2: Cache Theme Lookups for Performance

When using multiple theme properties in a composable, cache them in local variables:

✅ **CORRECT:**
```kotlin
@Composable
fun MyScreen() {
    val colors = WdsTheme.colors
    val dimensions = WdsTheme.dimensions
    val typography = WdsTheme.typography

    Column(
        modifier = Modifier.padding(dimensions.wdsSpacingTriple)
    ) {
        Text(
            text = "Title",
            style = typography.headline1,
            color = colors.colorContentDefault
        )
        Text(
            text = "Subtitle",
            style = typography.body1,
            color = colors.colorContentDeemphasized
        )
    }
}
```

## Rule 3: Use WDS Components

Always prefer WDS components over Material3 components when available:

❌ **WRONG:**
```kotlin
Button(onClick = { }) {
    Text("Click me")
}
```

✅ **CORRECT:**
```kotlin
WDSButton(
    onClick = { },
    text = "Click me",
    variant = WDSButtonVariant.FILLED
)
```

## Summary Checklist

When writing new code, ensure:
- ✅ All colors use `WdsTheme.colors` semantic colors
- ✅ All text uses `WdsTheme.typography` styles
- ✅ All spacing uses `WdsTheme.dimensions` tokens
- ✅ All corner radius uses `WdsTheme.shapes` tokens
- ✅ All icons use `Icons.Outlined.*` by default (FILL 0, wght 400, GRAD 0, opsz 24)
- ✅ WDS components are used instead of Material3 when available
- ✅ Theme properties are cached in local variables
- ✅ Static data is wrapped in `remember`
- ✅ Imports are organized and explicit
- ✅ Preview functions are included
- ✅ Code follows the established patterns
