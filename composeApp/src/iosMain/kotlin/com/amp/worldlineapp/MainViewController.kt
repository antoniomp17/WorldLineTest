package com.amp.worldlineapp

import androidx.compose.ui.window.ComposeUIViewController
import com.amp.worldlineapp.di.KoinInitializer

fun MainViewController() = ComposeUIViewController {
    KoinInitializer.initKoin()
}