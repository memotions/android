package com.memtionsandroid.memotions.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

// Define default Color Schemes
private val DarkColorScheme = darkColorScheme(
    primary = PureWhite,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = DarkBlue
)

private val LightColorScheme = lightColorScheme(
    primary = Charcoal,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = OffWhite
)

// Define CustomColors
data class CustomColors(
    val barColor: Color,
    val onBarColor: Color,
    val onBarSecondColor: Color,
    val outlineColor: Color,
    val processColor: Color,
    val processBackgroundColor: Color,
    val backgroundColor: Color,
    val secondBackgroundColor: Color,
    val onBackgroundColor: Color,
    val TextOnBackgroundColor: Color,
    val onSecondBackgroundColor: Color,
    val errorRed: Color,
    val success: Color
)

// Define Light and Dark Custom Colors
private val LightCustomColor = CustomColors(
    barColor = Charcoal,
    onBarColor = PureWhite,
    onBarSecondColor = SteelGrey,
    outlineColor = Taupe,
    processColor = Charcoal,
    processBackgroundColor = Silver,
    backgroundColor = OffWhite,
    secondBackgroundColor = Ivory,
    onBackgroundColor = Charcoal,
    TextOnBackgroundColor = Charcoal,
    onSecondBackgroundColor = SteelGrey,
    errorRed = errorRed,
    success = success
)

private val DarkCustomColor = CustomColors(
    barColor = SoftDarkBlue,
    onBarColor = PureWhite,
    onBarSecondColor = SteelGrey,
    outlineColor = Taupe,
    processColor = LightGrey,
    processBackgroundColor = OnyxGrey,
    backgroundColor = DarkBlueSo,
//    backgroundColor = DarkBlue,
    secondBackgroundColor = OnyxBlue,
//    secondBackgroundColor = OnyxGrey,
    onBackgroundColor = PureWhite,
    TextOnBackgroundColor = LightGrey,
    onSecondBackgroundColor = SteelGrey,
    errorRed = errorRed,
    success = success
)

// CompositionLocal for CustomColors
val LocalCustomColors = compositionLocalOf {
    LightCustomColor
}

@Composable
fun MemotionsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val customColor = if (darkTheme) DarkCustomColor else LightCustomColor
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }


    // Provide both ColorScheme and CustomColors
    CompositionLocalProvider(LocalCustomColors provides customColor) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// Extension property to access CustomColors from MaterialTheme
val MaterialTheme.customColors: CustomColors
    @Composable
    get() = LocalCustomColors.current
