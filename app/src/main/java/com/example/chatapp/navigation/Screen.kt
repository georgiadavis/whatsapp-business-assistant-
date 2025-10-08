package com.example.chatapp.navigation

sealed class Screen(val route: String) {
    data object DesignSystemLibrary : Screen("design_system_library")
    data object Colors : Screen("colors")
    data object Type : Screen("type")
    data object Components : Screen("components")
    data object Icons : Screen("icons")
}
