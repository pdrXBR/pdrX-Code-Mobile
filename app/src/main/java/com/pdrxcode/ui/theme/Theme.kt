package com.pdrxcode.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = VSCodePrimary,
    secondary = VSCodeSecondary,
    background = VSCodeBackground,
    surface = VSCodeSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = VSCodeTextPrimary,
    onSurface = VSCodeTextPrimary,
)

private val LightColorScheme = lightColorScheme(
    primary = VSCodePrimary,
    secondary = VSCodeSecondary,
    background = Color(0xFFF3F3F3),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun PdrXCodeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}