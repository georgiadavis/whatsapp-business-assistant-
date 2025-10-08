# Figma Design Implementation Guidelines

**IMPORTANT**: When implementing designs from Figma references, follow these strict guidelines.

## Icons

- **Always use exact SVG paths** provided in Figma designs - do NOT improvise or substitute with other icons
- When user provides Figma SVG icons, implement them exactly as provided using `ImageVector.Builder`
- Convert Figma SVG paths directly to Compose paths without modifications
- Preserve exact colors, dimensions, and path data from the original SVG

### Example: Implementing Figma SVG Icon

```kotlin
private val MyIcon = ImageVector.Builder(
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        fill = SolidColor(Color(0xFF5B6368)),
        pathFillType = PathFillType.NonZero
    ) {
        // Paste exact path data from Figma SVG
        moveTo(12f, 2f)
        lineTo(12f, 22f)
        // ... rest of path commands
    }
}.build()
```

## General Figma Guidelines

- Follow Figma UI references as closely as possible for all visual elements
- Maintain exact spacing, padding, and layout specifications from Figma
- Match exact colors from Figma (convert to WDS colors if available)
- Preserve exact dimensions and aspect ratios
- When in doubt about any design element, ask for clarification rather than making assumptions

## Workflow

1. Review Figma design thoroughly
2. Identify WDS components that match the design
3. Map Figma colors to WDS semantic colors when possible
4. Map Figma spacing to WDS spacing tokens
5. Implement custom SVG icons exactly as provided
6. Test against Figma design to ensure pixel-perfect implementation
