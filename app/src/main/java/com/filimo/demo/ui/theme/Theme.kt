package com.filimo.demo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = FilimoOrange,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onBackground = FilimoWhite,
    onSurface = FilimoWhite,
    onSurfaceVariant = FilimoGrey
)

private val LightColorScheme = lightColorScheme(
    primary = FilimoOrange,
    background = Color.White,
    surface = LightSurface,
    onPrimary = Color.White,
    onBackground = FilimoBlack,
    onSurface = FilimoBlack,
    onSurfaceVariant = Color.DarkGray
)

@Composable
fun FilimoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}