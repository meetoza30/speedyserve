package com.example.speedyserve.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Brand Colors
private val BrandOrange = Color(0xFFFF6B35)
private val BrandAmber = Color(0xFFFFA726)
private val BrandGreen = Color(0xFF4CAF50)
private val BrandRed = Color(0xFFE53935)
private val DarkBlue = Color(0xFF1A1B2E)

// Neutral Colors
private val NeutralWhite = Color(0xFFFFFFFF)
private val NeutralBlack = Color(0xFF000000)
private val WarmWhite = Color(0xFFFFFBFF)
private val WarmBlack = Color(0xFF1F1B16)
private val LightGray = Color(0xFFF5F1EB)
private val MediumGray = Color(0xFF857468)
private val DarkGray = Color(0xFF524639)

// Social Colors (for auth screens)
private val FacebookBlue = Color(0xFF3B5998)
private val TwitterBlue = Color(0xFF1DA1F2)
private val GoogleRed = Color(0xFFDB4437)

// Unified Light Color Scheme
private val SpeedyServeLightColorScheme = lightColorScheme(
    // Primary Brand Colors
    primary = BrandOrange,
    onPrimary = NeutralWhite,
    primaryContainer = Color(0xFFFFE8E0),
    onPrimaryContainer = Color(0xFF8B2500),

    // Secondary Colors (for buttons, accents)
    secondary = BrandAmber,
    onSecondary = NeutralBlack,
    secondaryContainer = Color(0xFFFFF3C4),
    onSecondaryContainer = Color(0xFF663C00),

    // Tertiary Colors (for success states, delivery status)
    tertiary = BrandGreen,
    onTertiary = NeutralWhite,
    tertiaryContainer = Color(0xFFE8F5E8),
    onTertiaryContainer = Color(0xFF1B5E20),

    // Error Colors (for validation, alerts)
    error = BrandRed,
    onError = NeutralWhite,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFFB71C1C),

    // Background Colors
    background = WarmWhite,
    onBackground = WarmBlack,

    // Surface Colors (cards, sheets, dialogs)
    surface = NeutralWhite,
    onSurface = WarmBlack,
    surfaceVariant = LightGray,
    onSurfaceVariant = DarkGray,

    // Outline Colors (borders, dividers)
    outline = MediumGray,
    outlineVariant = Color(0xFFD7C2B8),

    // Inverse Colors (for contrast elements)
    inverseSurface = Color(0xFF352F2A),
    inverseOnSurface = Color(0xFFF9EFE6),
    inversePrimary = Color(0xFFFFB599),

    // Surface Tint (for elevated surfaces)
    surfaceTint = BrandOrange,

    // Surface Colors for different elevations
    surfaceBright = NeutralWhite,
    surfaceDim = Color(0xFFE7E0D9),
    surfaceContainer = Color(0xFFF3ECE4),
    surfaceContainerHigh = Color(0xFFEDE6DE),
    surfaceContainerHighest = Color(0xFFE7E0D9),
    surfaceContainerLow = Color(0xFFF9F2EA),
    surfaceContainerLowest = NeutralWhite,

)

// Unified Dark Color Scheme
private val SpeedyServeDarkColorScheme = darkColorScheme(
    // Primary Brand Colors
    primary = Color(0xFFFFB599),
    onPrimary = Color(0xFF552100),
    primaryContainer = Color(0xFF7A3200),
    onPrimaryContainer = Color(0xFFFFE8E0),

    // Secondary Colors
    secondary = Color(0xFFFFD54F),
    onSecondary = Color(0xFF3D2F00),
    secondaryContainer = Color(0xFF5A4600),
    onSecondaryContainer = Color(0xFFFFF3C4),

    // Tertiary Colors
    tertiary = Color(0xFF81C784),
    onTertiary = Color(0xFF003D06),
    tertiaryContainer = Color(0xFF1B5E20),
    onTertiaryContainer = Color(0xFFE8F5E8),

    // Error Colors
    error = Color(0xFFEF5350),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFEBEE),

    // Background Colors
    background = WarmBlack,
    onBackground = Color(0xFFF9EFE6),

    // Surface Colors
    surface = Color(0xFF181410),
    onSurface = Color(0xFFF9EFE6),
    surfaceVariant = DarkGray,
    onSurfaceVariant = Color(0xFFD7C2B8),

    // Outline Colors
    outline = Color(0xFF9F8D80),
    outlineVariant = DarkGray,

    // Inverse Colors
    inverseSurface = Color(0xFFF9EFE6),
    inverseOnSurface = Color(0xFF352F2A),
    inversePrimary = BrandOrange,

    // Surface Tint
    surfaceTint = Color(0xFFFFB599),

    // Surface Colors for different elevations
    surfaceBright = Color(0xFF3E342B),
    surfaceDim = Color(0xFF181410),
    surfaceContainer = Color(0xFF231F1A),
    surfaceContainerHigh = Color(0xFF2E2924),
    surfaceContainerHighest = Color(0xFF39332E),
    surfaceContainerLow = Color(0xFF1F1B16),
    surfaceContainerLowest = Color(0xFF0F0D0A),
)

// Custom Typography
private val SpeedyServeTypography = Typography(
    // Display Text (for large headings, splash screens)
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),

    // Headlines (for screen titles, section headers)
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // Titles (for card titles, list items)
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // Body Text (for descriptions, content)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // Labels (for buttons, tabs, captions)
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

@Composable
fun SpeedyServeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ but disabled by default for brand consistency
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> SpeedyServeDarkColorScheme
        else -> SpeedyServeLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SpeedyServeTypography,
        content = content
    )
}

// Extension Colors for specific use cases
object SpeedyServeColors {
    // Social Media Colors
    val Facebook = FacebookBlue
    val Twitter = TwitterBlue
    val Google = GoogleRed

    // Status Colors
    val Success = BrandGreen
    val Warning = BrandAmber
    val Error = BrandRed
    val Info = Color(0xFF2196F3)

    // Food Category Colors
    val Pizza = Color(0xFFFF5722)
    val Burger = Color(0xFFFF9800)
    val Dessert = Color(0xFFE91E63)
    val Drinks = Color(0xFF00BCD4)
    val Healthy = Color(0xFF8BC34A)

    // Delivery Status Colors
    val Preparing = BrandAmber
    val OnTheWay = Color(0xFF2196F3)
    val Delivered = BrandGreen
    val Cancelled = BrandRed

    // Rating Colors
    val StarFilled = Color(0xFFFFC107)
    val StarEmpty = Color(0xFFE0E0E0)
}


//package com.example.speedyserve.ui.theme
//
//import android.app.Activity
//import android.os.Build
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Typography
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.dynamicDarkColorScheme
//import androidx.compose.material3.dynamicLightColorScheme
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.sp
//
//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
//
//// Custom Color Scheme
//private val FoodDeliveryLightColors = lightColorScheme(
//    primary = Color(0xFFFF6B35), // Vibrant Orange
//    onPrimary = Color.White,
//    primaryContainer = Color(0xFFFFE8E0), // Light Orange
//    onPrimaryContainer = Color(0xFF8B2500), // Dark Orange
//
//    secondary = Color(0xFFFFA726), // Amber
//    onSecondary = Color.Black,
//    secondaryContainer = Color(0xFFFFF3C4), // Light Amber
//    onSecondaryContainer = Color(0xFF663C00), // Dark Amber
//
//    tertiary = Color(0xFF4CAF50), // Green for success states
//    onTertiary = Color.White,
//    tertiaryContainer = Color(0xFFE8F5E8),
//    onTertiaryContainer = Color(0xFF1B5E20),
//
//    error = Color(0xFFE53935),
//    onError = Color.White,
//    errorContainer = Color(0xFFFFEBEE),
//    onErrorContainer = Color(0xFFB71C1C),
//
//    background = Color(0xFFFFFBFF), // Slightly warm white
//    onBackground = Color(0xFF1F1B16), // Dark brown
//
//    surface = Color.White,
//    onSurface = Color(0xFF1F1B16),
//    surfaceVariant = Color(0xFFF5F1EB), // Warm light gray
//    onSurfaceVariant = Color(0xFF524639), // Warm dark gray
//
//    outline = Color(0xFF857468), // Warm gray outline
//    outlineVariant = Color(0xFFD7C2B8), // Light warm outline
//
//    inverseSurface = Color(0xFF352F2A), // Dark warm surface
//    inverseOnSurface = Color(0xFFF9EFE6), // Light warm text
//    inversePrimary = Color(0xFFFFB599), // Light orange for dark theme
//)
//
//private val FoodDeliveryDarkColors = darkColorScheme(
//    primary = Color(0xFFFFB599), // Light Orange for dark
//    onPrimary = Color(0xFF552100), // Dark Orange
//    primaryContainer = Color(0xFF7A3200), // Medium Orange
//    onPrimaryContainer = Color(0xFFFFE8E0),
//
//    secondary = Color(0xFFFFD54F), // Light Amber for dark
//    onSecondary = Color(0xFF3D2F00), // Dark Amber
//    secondaryContainer = Color(0xFF5A4600),
//    onSecondaryContainer = Color(0xFFFFF3C4),
//
//    tertiary = Color(0xFF81C784), // Light Green
//    onTertiary = Color(0xFF003D06),
//    tertiaryContainer = Color(0xFF1B5E20),
//    onTertiaryContainer = Color(0xFFE8F5E8),
//
//    error = Color(0xFFEF5350),
//    onError = Color(0xFF690005),
//    errorContainer = Color(0xFF93000A),
//    onErrorContainer = Color(0xFFFFEBEE),
//
//    background = Color(0xFF1F1B16), // Dark warm background
//    onBackground = Color(0xFFF9EFE6), // Light warm text
//
//    surface = Color(0xFF1F1B16),
//    onSurface = Color(0xFFF9EFE6),
//    surfaceVariant = Color(0xFF524639), // Dark warm gray
//    onSurfaceVariant = Color(0xFFD7C2B8), // Light warm gray
//
//    outline = Color(0xFF9F8D80), // Medium warm gray
//    outlineVariant = Color(0xFF524639),
//
//    inverseSurface = Color(0xFFF9EFE6),
//    inverseOnSurface = Color(0xFF352F2A),
//    inversePrimary = Color(0xFFFF6B35),
//)
//
//@Composable
//fun SpeedyServeTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> FoodDeliveryDarkColors
//        else -> FoodDeliveryLightColors
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}
//
//
//// Custom Colors
//private val DarkBlue = Color(0xFF1A1B2E)
//private val Orange = Color(0xFFFF6B35)
//private val LightGray = Color(0xFFF5F5F5)
//private val DarkGray = Color(0xFF666666)
//private val White = Color(0xFFFFFFFF)
//private val Black = Color(0xFF000000)
//private val FacebookBlue = Color(0xFF3B5998)
//private val TwitterBlue = Color(0xFF1DA1F2)
//
//// Light Theme Colors
//private val AuthLightColorScheme = lightColorScheme(
//    primary = Orange,
//    onPrimary = White,
//    secondary = DarkBlue,
//    onSecondary = White,
//    background = White,
//    onBackground = Black,
//    surface = LightGray,
//    onSurface = Black,
//    surfaceVariant = LightGray,
//    onSurfaceVariant = DarkGray
//)
//
//// Dark Theme Colors
//private val AuthDarkColorScheme = darkColorScheme(
//    primary = Orange,
//    onPrimary = White,
//    secondary = DarkBlue,
//    onSecondary = White,
//    background = DarkBlue,
//    onBackground = White,
//    surface = Color(0xFF2A2B3E),
//    onSurface = White,
//    surfaceVariant = Color(0xFF2A2B3E),
//    onSurfaceVariant = Color(0xFFCCCCCC)
//)
//
//// Custom Typography
//private val AppTypography = Typography(
//    headlineLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Bold,
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        letterSpacing = 0.sp
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    ),
//    bodyMedium = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.25.sp
//    ),
//    labelLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp
//    )
//)
//
//@Composable
//fun AuthScreenTheme( darkTheme: Boolean = isSystemInDarkTheme(),
//                      content: @Composable () -> Unit,
//) {
//    val colorScheme = if (darkTheme) AuthDarkColorScheme else AuthLightColorScheme
//    val typography = AppTypography
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = typography,
//        content = content
//    )
//
//}