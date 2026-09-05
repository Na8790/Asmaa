package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = WarmGoldSecondary,
    onPrimary = DeepBluePrimary,
    secondary = WarmGoldLight,
    onSecondary = DeepBluePrimary,
    tertiary = EmeraldGreenSuccess,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    error = CoralRedError,
    onError = Color.White
  )

private val LightColorScheme =
  lightColorScheme(
    primary = DeepBluePrimary,
    onPrimary = Color.White,
    secondary = WarmGoldSecondary,
    onSecondary = DeepBluePrimary,
    tertiary = EmeraldGreenSuccess,
    background = OffWhiteBackground,
    surface = SurfacePureWhite,
    onBackground = TextDarkGray,
    onSurface = TextDarkGray,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = TextMutedGray,
    outline = BorderLight,
    error = CoralRedError,
    onError = Color.White
  )

@Composable
fun TajribahTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

