# LLM Instructions for WhatsApp Android Prototype

This file provides guidance for AI assistants working with code in this repository.

## About This Project

This is an Android prototype built with Jetpack Compose and the WhatsApp Design System (WDS). It uses MVVM architecture, Room database, Hilt dependency injection, and a complete design system.

## Rule Files Location

Detailed rules are organized in `.llm/rules/`:

```
.llm/rules/
├── README.md                           # Overview of all rules
├── 00-project-overview.md              # Project info, tech stack, build commands
├── 01-architecture.md                  # Folder structure, architecture
├── 02-wds-design-system.md             # WDS design system details
├── 10-mandatory-wds-usage.md           # ⚠️ CRITICAL - Always use WdsTheme
├── 11-code-patterns.md                 # Common code patterns
├── 12-component-examples.md            # WDS component examples
├── 20-development-principles.md        # Best practices, performance, security
├── 21-testing-previews.md              # Testing and preview guidelines
└── 30-figma-design-implementation.md   # Figma implementation rules
```

## Critical Rules (Must Follow)

### 1. NEVER Hardcode Values

❌ **WRONG:**
```kotlin
Text(text = "Hello", color = Color(0xFF000000), fontSize = 16.sp)
```

✅ **CORRECT:**
```kotlin
Text(
    text = "Hello",
    style = WdsTheme.typography.body1,
    color = WdsTheme.colors.colorContentDefault
)
```

### 2. Always Use WDS Design System

- **Colors**: `WdsTheme.colors.colorContentDefault`
- **Typography**: `WdsTheme.typography.headline1`
- **Spacing**: `WdsTheme.dimensions.wdsSpacingDouble`
- **Shapes**: `WdsTheme.shapes.double`
- **Components**: Use WDS components (`WDSButton`, `WDSChip`, etc.) over Material3

### 3. Cache Theme Lookups

```kotlin
@Composable
fun MyScreen() {
    val colors = WdsTheme.colors
    val dimensions = WdsTheme.dimensions
    val typography = WdsTheme.typography

    // Use cached values
}
```

### 4. Follow Architecture

```
wds/                    # Design system (components, theme, tokens)
features/               # Features (chat, chatlist, chatinfo, main)
data/                   # Data layer (local, repository, initializer)
di/                     # Dependency injection
```

## Quick Reference

### Build Commands
```bash
./gradlew assembleDebug        # Build debug APK
./gradlew test                 # Run tests
./gradlew clean build          # Clean and rebuild
```

### WDS Components
- `WDSButton` - Button with variants (FILLED, TONAL, OUTLINE, BORDERLESS)
- `WDSChip` - Chip with actions (PRIMARY, SECONDARY) and sizes
- `WDSTextField` - Text input field
- `WDSBottomSheet` - Bottom sheet
- `WdsCheckbox`, `WdsRadioButton`, `WdsSwitch` - Form controls
- `WDSDivider` - Divider line
- `WDSFab` - Floating action button

### Spacing Tokens
- `wdsSpacingQuarter` (2dp)
- `wdsSpacingHalf` (4dp)
- `wdsSpacingSingle` (8dp)
- `wdsSpacingSinglePlus` (12dp)
- `wdsSpacingDouble` (16dp) ← Most common
- `wdsSpacingTriple` (24dp)
- `wdsSpacingQuad` (32dp)
- `wdsSpacingQuint` (40dp)

### Corner Radius Tokens
- `none` (0dp), `halfPlus` (6dp), `single` (8dp)
- `singlePlus` (12dp), `double` (16dp), `triple` (24dp)
- `triplePlus` (28dp), `circle` (100dp)

## When Coding

1. ✅ Use WdsTheme tokens for all styling
2. ✅ Cache theme lookups at start of composables
3. ✅ Use WDS components over Material3
4. ✅ Add preview functions for composables
5. ✅ Follow existing patterns in codebase
6. ✅ Organize imports properly
7. ✅ Test with both light and dark themes

## Need More Details?

Read the specific rule files in `.llm/rules/` for comprehensive guidelines on:
- Architecture and folder structure
- Complete WDS design system reference
- Code patterns and examples
- Performance optimization
- Security and testing
- Figma design implementation

---

**For detailed rules, see**: `.llm/rules/README.md`
