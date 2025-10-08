# Project Overview

This is an Android prototype built with Jetpack Compose and the WhatsApp Design System (WDS). It provides a foundation for rapid prototyping with a complete design system including colors, typography, spacing, corner radius tokens, and reusable components.

## Technology Stack

- **Android** (Kotlin)
- **Jetpack Compose** for UI
- **Room Database** for local storage
- **Hilt** for dependency injection
- **MVVM architecture** with Repository pattern

## Build Information

- Target SDK: 34
- Minimum SDK: 31
- Version catalogs: `gradle/libs.versions.toml`

## Build Commands

### Build the application
```bash
./gradlew assembleDebug
```

### Run tests
```bash
./gradlew test
./gradlew connectedAndroidTest  # For instrumented tests
```

### Clean and rebuild
```bash
./gradlew clean build
```

### Generate APK
```bash
./gradlew assembleRelease
```
