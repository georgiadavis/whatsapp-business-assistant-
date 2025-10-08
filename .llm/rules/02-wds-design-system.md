# WhatsApp Design System (WDS)

## Overview

The design system is located in `app/src/main/java/com/example/chatapp/wds/` and consists of:

## 1. Colors (`wds/theme/`)

**Files**: `WdsColorScheme.kt`, `WdsSemanticLightColors.kt`, `WdsSemanticDarkColors.kt`, `BaseColors.kt`

### Semantic Colors (Use These)
- `colorContentDefault` - Primary text and icons
- `colorContentDeemphasized` - Secondary text and icons
- `colorContentEmphasized` - Emphasized text (bold, important)
- `colorContentInverse` - Text on dark backgrounds
- `colorSurfaceDefault` - Default background
- `colorSurfaceElevatedDefault` - Elevated surfaces (cards, sheets)
- `colorSurfaceHighlight` - Highlighted areas
- `colorAccentEmphasized` - Primary actions, links
- `colorAccentDeemphasized` - Tinted backgrounds
- `colorPositive`, `colorNegative`, `colorWarning` - Status colors

**Access via**: `WdsTheme.colors.colorContentDefault`

### Base Colors
Only use base colors (e.g., `BaseColors.wdsGray50`, `BaseColors.wdsCobalt500`) when semantic colors don't fit the use case.

## 2. Typography (`wds/tokens/WdsTypography.kt`)

### Available Styles
- `largeTitle1`, `largeTitle2` - Large headings
- `headline1`, `headline2` - Section headings
- `body1`, `body2` - Body text
- `body1Emphasized`, `body2Emphasized` - Bold body text
- `caption1`, `caption2` - Small text, captions
- `chatBubbleText`, `chatTimestamp` - Chat-specific styles

**Access via**: `WdsTheme.typography.headline1`

## 3. Spacing (`wds/tokens/WdsDimensions.kt`)

### Available Tokens
- `wdsSpacingQuarter` (2dp) - Minimal spacing
- `wdsSpacingHalf` (4dp) - Very tight spacing
- `wdsSpacingSingle` (8dp) - Default small spacing
- `wdsSpacingSinglePlus` (12dp) - Medium-small spacing
- `wdsSpacingDouble` (16dp) - Standard spacing
- `wdsSpacingTriple` (24dp) - Large spacing
- `wdsSpacingQuad` (32dp) - Extra large spacing
- `wdsSpacingQuint` (40dp) - Maximum spacing

**Access via**: `WdsTheme.dimensions.wdsSpacingDouble`

## 4. Corner Radius (`wds/tokens/WdsShapes.kt`)

### Available Tokens
- `none` - No rounding (0dp)
- `halfPlus` - Slight rounding (6dp)
- `single` - Standard rounding (8dp)
- `singlePlus` - Comfortable rounding (12dp)
- `double` - Medium rounding (16dp)
- `triple` - Large rounding (24dp)
- `triplePlus` - Extra large rounding (28dp)
- `circle` - Fully rounded (pills, circles) (100dp)

**Access via**: `WdsTheme.shapes.double`

## 5. Components (`wds/components/`)

### Available Components
- **WDSButton** - Variants: FILLED, TONAL, OUTLINE, BORDERLESS; Actions: PRIMARY, SECONDARY, MEDIA
- **WDSChip** - Actions: PRIMARY, SECONDARY; Sizes: SMALL, MEDIUM, LARGE
- **WDSTextField** - Single-line and multi-line
- **WDSFab** - Floating action button
- **WDSDivider** - Divider line
- **WDSBottomSheet** - Bottom sheet
- **WdsCheckbox, WdsRadioButton, WdsSwitch, WdsDialog** - Form controls
