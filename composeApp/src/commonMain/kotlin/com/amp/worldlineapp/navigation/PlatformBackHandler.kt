package com.amp.worldlineapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
expect fun PlatformBackHandler(
    navController: NavHostController,
    onBack: () -> Unit
)