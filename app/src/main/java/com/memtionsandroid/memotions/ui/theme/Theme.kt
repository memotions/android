package com.memtionsandroid.memotions.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

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
    val onSecondBackgroundColor: Color
)

private val LightCustomColor = CustomColors(
    barColor = Charcoal,
    onBarColor = PureWhite,
    onBarSecondColor = LightGrey,
    outlineColor = Taupe,
    processColor = Charcoal,
    processBackgroundColor = Silver,
    backgroundColor = OffWhite,
    secondBackgroundColor = Ivory,
    onBackgroundColor = Charcoal,
    onSecondBackgroundColor = SteelGrey,
)

private val DarkCustomColor = CustomColors(
    barColor = Charcoal,
    onBarColor = PureWhite,
    onBarSecondColor = LightGrey,
    outlineColor = Taupe,
    processColor = LightGrey,
    processBackgroundColor = OnyxGrey,
    backgroundColor = SlateBLue,
    secondBackgroundColor = OnyxGrey,
    onBackgroundColor = PureWhite,
    onSecondBackgroundColor = LightGrey,
)


@Composable
fun MemotionsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val customColor = if (darkTheme) DarkCustomColor else LightCustomColor
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}