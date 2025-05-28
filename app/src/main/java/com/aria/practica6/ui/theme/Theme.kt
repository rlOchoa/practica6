package com.aria.practica6.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 1. Colores IPN Guinda
private val GuindaLightColors = lightColorScheme(
    primary = Color(0xFF7B1B39), // Guinda IPN
    secondary = Color(0xFFB71C1C),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val GuindaDarkColors = darkColorScheme(
    primary = Color(0xFFFF8A80),
    secondary = Color(0xFFEF9A9A),
    background = Color(0xFF330019),
    surface = Color(0xFF4B0A28),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

// 2. Colores ESCOM Azul
private val EscomLightColors = lightColorScheme(
    primary = Color(0xFF0047BA), // Azul ESCOM
    secondary = Color(0xFF1976D2),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val EscomDarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),
    secondary = Color(0xFF64B5F6),
    background = Color(0xFF001A40),
    surface = Color(0xFF002D6B),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

// 3. Enum del tema
enum class AppColorTheme {
    ESCOM, IPN
}

// 4. Theme composable principal
@Composable
fun Practica6Theme(
    colorTheme: AppColorTheme = AppColorTheme.ESCOM,
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    val colorScheme = when (colorTheme) {
        AppColorTheme.ESCOM -> if (darkTheme) EscomDarkColors else EscomLightColors
        AppColorTheme.IPN -> if (darkTheme) GuindaDarkColors else GuindaLightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
