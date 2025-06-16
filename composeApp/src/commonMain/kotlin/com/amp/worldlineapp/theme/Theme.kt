// File: ui/theme/Theme.kt
package com.amp.worldlineapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val lightScheme = lightColorScheme(
    primary = Color(0xFF1A1A1A),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF5F5F5),
    onPrimaryContainer = Color(0xFF1A1A1A),

    secondary = Color(0xFF424242),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8E8E8),
    onSecondaryContainer = Color(0xFF1A1A1A),

    tertiary = Color(0xFFFF6B35),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE8DC),
    onTertiaryContainer = Color(0xFF8B2500),

    error = Color(0xFFD32F2F),
    onError = Color.White,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFF8B0000),

    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF1A1A1A),
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF424242)
)

private val darkScheme = darkColorScheme(
    primary = Color(0xFFFF6B35),
    onPrimary = Color(0xFF1A1A1A),
    primaryContainer = Color(0xFF8B2500),
    onPrimaryContainer = Color(0xFFFFE8DC),

    secondary = Color(0xFFBDBDBD),
    onSecondary = Color(0xFF1A1A1A),
    secondaryContainer = Color(0xFF424242),
    onSecondaryContainer = Color(0xFFE8E8E8),

    tertiary = Color(0xFF81C784),
    onTertiary = Color(0xFF1A1A1A),
    tertiaryContainer = Color(0xFF2E7D32),
    onTertiaryContainer = Color(0xFFC8E6C9),

    error = Color(0xFFEF5350),
    onError = Color(0xFF1A1A1A),
    errorContainer = Color(0xFFD32F2F),
    onErrorContainer = Color(0xFFFFEBEE),

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFBDBDBD),

    outline = Color(0xFF424242),
    outlineVariant = Color(0xFF2C2C2C),
    inverseSurface = Color(0xFFE0E0E0),
    inverseOnSurface = Color(0xFF1A1A1A)
)

@Composable
fun getColorScheme(
    darkTheme: Boolean = isSystemInDarkTheme()
): ColorScheme {
    return if (darkTheme) darkScheme else lightScheme
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = getColorScheme(darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}