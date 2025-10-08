# SimpleWhatsApp

A fully functional WhatsApp-style Android chat application built with Jetpack Compose and the WhatsApp Design System (WDS). This app demonstrates modern Android development practices with a complete chat experience, local database storage, and a comprehensive design system library.

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)

## 🙏 Thanks

Many thanks to Dan Lebowitz for inspiring this!

## 🎯 About This App

SimpleWhatsApp is a **fully functional chat application** that replicates the core WhatsApp experience. Built with production-quality architecture and the WhatsApp Design System, it includes:

- ✅ Complete chat messaging interface
- ✅ Chat list with search, filters, and archived chats
- ✅ Room database with user, conversation, and message entities
- ✅ MVVM architecture with Hilt dependency injection
- ✅ WhatsApp Design System with light and dark mode
- ✅ Interactive Design System Library showcase

## 🚀 Quick Start

### Prerequisites

- **Android Studio** (latest stable version)
- **JDK 17** or higher

### Installation

1. **Clone or download** the project
2. **Open Android Studio** and select "Open an Existing Project"
3. Navigate to the **SimpleWhatsApp** folder
4. Wait for Gradle sync to complete
5. **Click ▶️ to run the app** on an emulator or device

## 🎨 Vibe Coding with AI

### In Cursor

1. **Join [Cursor Feedback](https://fb.workplace.com/groups/725594866994618)** to get access to Cursor
2. **Download** and **install Cursor**
3. **Open the SimpleWhatsApp folder** in Cursor
4. The AI assistant will automatically understand the project structure and design system rules
5. **Prompt Cursor** to make changes - it will follow WDS guidelines
6. **Return to Android Studio** and **click ▶️ to rebuild the app**

### In VS Code @ FB

1. **Open the SimpleWhatsApp folder** in **VS Code @ FB**
2. Select **Devmate** in the left navigation bar
3. **Prompt Devmate** to make changes
4. **Return to Android Studio** and **click ▶️ to rebuild the app**

### AI Development Guidelines

The project includes comprehensive AI rules in `CLAUDE.md` that help AI assistants understand:
- The WhatsApp Design System architecture
- Mandatory coding patterns and best practices
- Component usage guidelines
- Database and repository patterns

## 📱 Features

### Chat Experience
- **Chat List Screen**: Browse all conversations with search and filtering
- **Message Types**: Text, photo, video, audio, file, location, voice notes
- **Conversation Features**: Pin, mute, archive, unread counts
- **Real-time Updates**: Message status (sent, delivered, read)
- **Group & Individual Chats**: Full support for both conversation types

### Design System Library
Access via the 3-dot menu → Design Library:
- **Colors Screen**: All 200+ semantic and base colors with hex values
- **Type Screen**: Complete typography scale with 16 text styles
- **Components Screen**: Interactive showcase of 10+ WDS components

## 🏗️ Architecture

### Tech Stack
- **Kotlin** - Modern, concise programming language
- **Jetpack Compose** - Declarative UI toolkit
- **Room Database** - Local data persistence
- **Hilt** - Dependency injection
- **Kotlin Coroutines & Flow** - Asynchronous programming
- **Navigation Compose** - Screen navigation
- **MVVM Pattern** - Clean architecture

### Database Schema
```
UserEntity
├── id, name, phoneNumber, avatarUrl
├── online status tracking
└── last seen timestamp

ConversationEntity
├── id, name, type (individual/group)
├── lastMessageText, lastMessageTimestamp
├── isArchived, isPinned, isMuted
└── unreadCount

MessageEntity
├── id, conversationId, senderId
├── content, type (text/photo/video/audio/file/location/voice)
├── timestamp, status (sent/delivered/read)
└── isStarred, replyToMessageId

ConversationParticipantEntity
└── Links users to conversations (many-to-many)
```

### Project Structure

```
app/src/main/java/com/example/chatapp/
├── MainActivity.kt                    # Main entry point with navigation
├── navigation/
│   └── Screen.kt                      # Navigation routes
├── data/
│   ├── local/                         # Room database
│   │   ├── ChatDatabase.kt           # Main database
│   │   ├── dao/                      # Data access objects
│   │   └── entities/                 # Database entities
│   ├── repository/
│   │   └── ChatRepository.kt         # Data repository
│   └── generator/
│       └── ChatDataGenerator.kt      # Sample data generation
├── di/
│   └── DatabaseModule.kt             # Hilt dependency injection
├── features/
│   └── chatlist/
│       ├── ChatListScreen.kt         # Main chat list UI
│       └── ChatListViewModel.kt      # Business logic
├── ui/
│   ├── screens/                      # Design system showcase screens
│   │   ├── ColorsScreen.kt
│   │   ├── TypeScreen.kt
│   │   ├── ComponentsScreen.kt
│   │   └── DesignSystemLibraryScreen.kt
│   └── theme/
│       └── wds/                      # WhatsApp Design System
│           ├── BaseColors.kt         # Color primitives
│           ├── WdsColorScheme.kt     # Semantic color tokens
│           ├── WdsSemanticLightColors.kt
│           ├── WdsSemanticDarkColors.kt
│           ├── WdsTypography.kt      # Typography system
│           ├── WdsDimensions.kt      # Spacing tokens
│           ├── WdsShapes.kt          # Corner radius tokens
│           ├── WdsTheme.kt           # Main theme provider
│           ├── components/           # WDS components
│           └── README.md             # Design system docs
```

## 🎨 Design System Overview

The WhatsApp Design System (WDS) ensures consistency, accessibility, and quality throughout the app.

### Design Tokens

#### 🎨 Colors
- **Semantic colors** that adapt to light/dark mode
- **Base color primitives** for edge cases
- Organized by purpose: Content, Surface, Accent, Feedback

```kotlin
// Always use semantic colors
Text(
    text = "Hello",
    color = WdsTheme.colors.colorContentDefault
)
```

#### ✍️ Typography
- **Complete type scale** from large titles to captions
- **Chat-specific styles** for messaging interfaces
- Font weights, sizes, line heights, and letter spacing defined

```kotlin
Text(
    text = "Heading",
    style = WdsTheme.typography.headline1
)
```

#### 📏 Spacing
- **Consistent spacing scale** from 2dp to 40dp
- Named tokens: Quarter, Half, Single, Double, Triple, Quad, Quint
- Ensures visual rhythm and alignment

```kotlin
Column(
    modifier = Modifier.padding(WdsTheme.dimensions.wdsSpacingDouble)
)
```

#### ⭕ Corner Radius
- **Rounded corner tokens** from subtle to fully rounded
- Pill shapes for buttons and chips
- Consistent rounding across components

```kotlin
Box(
    modifier = Modifier.clip(
        RoundedCornerShape(WdsTheme.shapes.single)
    )
)
```

### Components

Pre-built, accessible components that follow WDS guidelines:

| Component | Description | Variants |
|-----------|-------------|----------|
| **WDSButton** | Primary action button | Filled, Tonal, Outline, Borderless |
| **WDSChip** | Filter and input chips | Default, Input, Close, Dropdown |
| **WDSTextField** | Text input fields | Single-line, Multi-line |
| **WDSFab** | Floating action button | Primary, Secondary |
| **WDSBottomSheet** | Modal bottom sheet | With image, list, CTAs |
| **WDSContextMenu** | Popup context menu | With icons and text |
| **WDSDivider** | Horizontal divider | Standard, Inset |
| **WdsCheckbox** | Checkbox control | Checked, Unchecked |
| **WdsRadioButton** | Radio button control | Selected, Unselected |
| **WdsSwitch** | Toggle switch | On, Off |
| **WdsDialog** | Modal dialog | Title, message, actions |

### Icons

The app includes **Material Icons Extended**, providing access to **~2000 Material Design icons** in both filled and outlined styles.

#### For Designers

**Icon Library Reference:**
- 📦 **Browse all icons**: [Material Symbols and Icons](https://fonts.google.com/icons)
- 🎨 **Two styles available**: Filled and Outlined
- 📏 **Standard size**: 24dp (can be scaled)
- 🎯 **Categories**: Action, Communication, Content, Device, Editor, File, Image, Maps, Navigation, Social, and more

**Using Icons in Designs:**
When designing in Figma or other tools, reference icons by their Material name (e.g., "Settings", "Palette", "Share"). The development team can implement the exact icon using the Material Icons library.

**Common Icons in SimpleWhatsApp:**
- `Palette` - Design Library menu
- `Settings` - App settings
- `Share` - Share functionality
- `ArrowBack` - Navigation back button
- `Search` - Search functionality
- `MoreVert` - More options menu

#### For Developers

```kotlin
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*

// Outlined icons (preferred for UI consistency)
Icon(
    imageVector = Icons.Outlined.Settings,
    contentDescription = "Settings",
    tint = WdsTheme.colors.colorContentDefault
)

// Filled icons
Icon(
    imageVector = Icons.Filled.Favorite,
    contentDescription = "Favorite",
    tint = WdsTheme.colors.colorAccentEmphasized
)

// Auto-mirrored icons (for RTL support)
Icon(
    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
    contentDescription = "Back"
)
```

**Icon Guidelines:**
- ✅ Always provide `contentDescription` for accessibility
- ✅ Use `WdsTheme.colors` for icon tints
- ✅ Prefer `Outlined` style for consistency with WDS
- ✅ Use standard 24dp size (default)
- ✅ Use `AutoMirrored` icons for directional elements in RTL layouts

## 💻 Development

### Design System Rules

**Golden Rules:**
1. ✅ **Always use design tokens** - Never hardcode colors, spacing, or typography
2. ✅ **Cache theme lookups** - Store `WdsTheme.colors` etc. in local variables
3. ✅ **Use semantic colors** - `colorContentDefault` not `Color.Black`
4. ✅ **Test both themes** - Always preview light and dark modes
5. ✅ **Follow patterns** - Look at existing screens for guidance

See `CLAUDE.md` for comprehensive development guidelines.

### Creating a New Screen

1. **Create a new file** in `ui/screens/`:

```kotlin
@Composable
fun MyNewScreen(
    onNavigateBack: () -> Unit
) {
    val colors = WdsTheme.colors
    val dimensions = WdsTheme.dimensions
    val typography = WdsTheme.typography
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Screen", style = typography.headline2) },
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
                    containerColor = colors.colorSurfaceDefault
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(dimensions.wdsSpacingDouble)
        ) {
            // Your content here
        }
    }
}
```

2. **Add navigation route** in `Screen.kt`:

```kotlin
object MyNewScreen : Screen("my_new_screen")
```

3. **Register in navigation** in `MainActivity.kt`:

```kotlin
composable(Screen.MyNewScreen.route) {
    MyNewScreen(
        onNavigateBack = { navController.popBackStack() }
    )
}
```

### Using Components

Always use WDS components instead of Material3 components:

```kotlin
// ✅ Good - Uses WDS component
WDSButton(
    onClick = { },
    text = "Submit",
    variant = WDSButtonVariant.FILLED
)

// ❌ Avoid - Raw Material3 component
Button(onClick = { }) {
    Text("Submit")
}
```

## 🔧 Technical Details

### Built With
- **Kotlin 2.0.21** - Modern language features
- **Jetpack Compose** - Declarative UI (Compose BOM 2024.10.01)
- **Material 3** - Base component library
- **Navigation Compose 2.8.4** - Type-safe navigation
- **Room 2.6.1** - Local database
- **Hilt 2.52** - Dependency injection
- **Kotlin Coroutines 1.9.0** - Asynchronous programming
- **Coil 2.7.0** - Image loading

### Requirements
- **Min SDK**: 31 (Android 12)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35
- **JDK**: 17
- **Gradle**: 8.9
- **Android Gradle Plugin**: 8.7.3

### Build Commands

```bash
# Clean and build
./gradlew clean assembleDebug

# Install on device/emulator
./gradlew installDebug

# Run tests
./gradlew test

# Generate release APK
./gradlew assembleRelease
```

## 📚 Additional Resources

- **WDS Design System Docs**: `app/src/main/java/com/example/chatapp/wds/README.md`
- **AI Development Rules**: `CLAUDE.md`
- **Example Screens**: `ui/screens/ColorsScreen.kt`, `TypeScreen.kt`, `ComponentsScreen.kt`
- **Database Schema**: `app/schemas/`

## 🎯 Key Features Showcase

### Main Screens
- **Chat List**: Search, filter (All/Unread/Groups), pin, archive conversations
- **Design System Library**: Access via 3-dot menu → Design Library
  - Colors screen with 200+ colors and hex values
  - Typography screen with all text styles
  - Components screen with interactive examples

### Database Features
- Room database with automatic schema exports
- Reactive queries with Kotlin Flow
- Type converters for complex types (Instant, MessageType, etc.)
- Foreign key relationships for data integrity
- Sample data generator for testing

### Architecture Highlights
- MVVM pattern with clear separation of concerns
- Repository pattern for data abstraction
- Hilt for compile-time dependency injection
- StateFlow for reactive UI updates
- Navigation Compose for type-safe routing

## 💡 Tips & Best Practices

### Performance
- Cache theme lookups in composables
- Use `remember` for static data
- Add keys to `LazyColumn` items
- Extract large composables into separate functions

### Code Quality
- Follow existing patterns in the codebase
- Use explicit imports (no wildcards)
- Add preview functions for all composables
- Test both light and dark themes

### Design Consistency
- Always use design tokens
- Reference the Design System Library in-app
- Maintain visual hierarchy
- Ensure proper contrast ratios

## 🤝 Contributing

Feel free to:
- Extend functionality with new features
- Add new WDS components
- Improve the database schema
- Enhance the UI/UX

## 📄 License

This project is provided as-is for educational and prototyping purposes.

---

## 🙏 Acknowledgments

**WA Vibe Coding Pioneers:** Benjamin Dauer, John Dasta, John Neumann, Rory McCawl, Vignesh Sachidanandam, and Will Trickey

**Happy Coding! 🎉**

---

*Built with ❤️ using the WhatsApp Design System*