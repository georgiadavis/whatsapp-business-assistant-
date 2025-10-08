# WDS Component Usage Examples

## Button

```kotlin
WDSButton(
    onClick = { /* action */ },
    text = "Primary Action",
    variant = WDSButtonVariant.FILLED // or TONAL, OUTLINE, BORDERLESS
)
```

## Chip

```kotlin
WDSChip(
    text = "Filter",
    selected = isSelected,
    onClick = { /* action */ },
    action = WDSChipAction.PRIMARY,
    size = WDSChipSize.MEDIUM
)
```

## Bottom Sheet

```kotlin
WDSBottomSheet(
    onDismissRequest = { /* dismiss */ },
    headline = "Sheet Title",
    bodyText = "Description text",
    items = listOf(
        WDSBottomSheetItem(
            icon = Icons.Outlined.Info,
            text = "Item description"
        )
    ),
    primaryButtonText = "Confirm",
    onPrimaryClick = { /* action */ }
)
```

## TextField

```kotlin
WDSTextField(
    value = textValue,
    onValueChange = { textValue = it },
    placeholder = "Enter text",
    modifier = Modifier.fillMaxWidth()
)
```

## Checkbox

```kotlin
WdsCheckbox(
    checked = isChecked,
    onCheckedChange = { isChecked = it }
)
```

## Radio Button

```kotlin
WdsRadioButton(
    selected = isSelected,
    onClick = { /* select */ }
)
```

## Switch

```kotlin
WdsSwitch(
    checked = isEnabled,
    onCheckedChange = { isEnabled = it }
)
```

## Dialog

```kotlin
WdsDialog(
    onDismissRequest = { /* dismiss */ },
    title = "Dialog Title",
    message = "Dialog message",
    confirmText = "Confirm",
    onConfirm = { /* action */ },
    dismissText = "Cancel",
    onDismiss = { /* dismiss */ }
)
```

## Divider

```kotlin
WDSDivider()
```

## Floating Action Button

```kotlin
WDSFab(
    onClick = { /* action */ },
    icon = Icons.Default.Add,
    contentDescription = "Add"
)
```
