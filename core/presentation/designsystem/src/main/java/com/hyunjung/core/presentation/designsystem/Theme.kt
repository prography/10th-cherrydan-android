package com.hyunjung.core.presentation.designsystem

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LocalCherrydanColors = staticCompositionLocalOf { CherrydanColors }
val LocalCherrydanTypography = staticCompositionLocalOf { CherrydanTypography }

object CherrydanTheme {
    val colors: CherrydanColors
        @Composable
        get() = LocalCherrydanColors.current

    val typography: CherrydanTypography
        @Composable
        get() = LocalCherrydanTypography.current
}

@Composable
fun CherrydanTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = CherrydanColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    CompositionLocalProvider(
        LocalCherrydanColors provides colorScheme,
        LocalCherrydanTypography provides CherrydanTypography,
        content = content
    )
}