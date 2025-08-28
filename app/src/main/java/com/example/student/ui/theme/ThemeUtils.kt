package com.example.student.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp

/**
 * A utility object for theming and UI-related functions
 */
object ThemeUtils {
    
    /**
     * Darken a color by a specified factor
     */
    fun Color.darken(factor: Float): Color {
        return this.copy(alpha = 1f).compositeOver(
            Color.Black.copy(alpha = 1f - factor)
        )
    }
    
    /**
     * Lighten a color by a specified factor
     */
    fun Color.lighten(factor: Float): Color {
        return this.copy(alpha = 1f).compositeOver(
            Color.White.copy(alpha = factor)
        )
    }
    
    /**
     * Check if the current theme is dark
     */
    @Composable
    fun isDarkTheme(): Boolean {
        return isSystemInDarkTheme()
    }
    
    /**
     * Get the current color scheme based on the theme
     */
    @Composable
    fun currentColorScheme(): ColorScheme {
        return if (isDarkTheme()) {
            DarkColorScheme
        } else {
            LightColorScheme
        }
    }
    
    /**
     * Get the current typography
     */
    @Composable
    fun currentTypography(): Typography {
        return Typography
    }
    
    /**
     * Get the current shapes
     */
    @Composable
    fun currentShapes(): Shapes {
        return Shapes
    }
    
    /**
     * A composable that provides the current theme values
     */
    @Composable
    fun ProvideAppTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
        
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
    
    /**
     * Get the appropriate surface color based on elevation
     */
    @Composable
    fun surfaceColorAtElevation(
        elevation: androidx.compose.ui.unit.Dp = 0.dp,
        color: Color = MaterialTheme.colorScheme.surface
    ): Color {
        return MaterialTheme.colorScheme.surfaceColorAtElevation(elevation, color)
    }
    
    /**
     * Get the appropriate text color for a background color
     */
    @Composable
    fun textColorForBackground(backgroundColor: Color): Color {
        return if (backgroundColor.contrast(Color.Black) > backgroundColor.contrast(Color.White)) {
            Color.Black
        } else {
            Color.White
        }
    }
    
    /**
     * Calculate the contrast ratio between two colors
     */
    private fun Color.contrast(other: Color): Float {
        val thisLuminance = 0.2126f * red + 0.7152f * green + 0.0722f * blue
        val otherLuminance = 0.2126f * other.red + 0.7152f * other.green + 0.0722f * other.blue
        
        val l1 = if (thisLuminance > otherLuminance) thisLuminance else otherLuminance
        val l2 = if (thisLuminance > otherLuminance) otherLuminance else thisLuminance
        
        return (l1 + 0.05f) / (l2 + 0.05f)
    }
    
    /**
     * Get a color with alpha applied
     */
    fun Color.withAlpha(alpha: Float): Color {
        return this.copy(alpha = alpha)
    }
    
    /**
     * Generate a color based on a string (useful for avatars)
     */
    fun colorFromString(str: String): Color {
        var hash = 0
        for (i in str.indices) {
            hash = str[i].code + ((hash shl 5) - hash)
        }
        
        val hue = (hash % 360).toFloat()
        return androidx.compose.ui.graphics.Color.hsl(hue, 0.7f, 0.6f)
    }
}
