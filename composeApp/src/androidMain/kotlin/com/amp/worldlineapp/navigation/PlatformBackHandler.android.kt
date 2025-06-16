package com.amp.worldlineapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
actual fun PlatformBackHandler(
    navController: NavHostController,
    onBack: () -> Unit
) {
    BackHandler(enabled = navController.canGoBack) {
        onBack()
    }
}

private val NavHostController.canGoBack: Boolean
    get() = currentBackStackEntry != null && previousBackStackEntry != null