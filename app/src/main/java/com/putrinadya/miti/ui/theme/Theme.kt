package com.putrinadya.miti.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MitiCyan,
    background = MitiNavy,
    surface = MitiCard,
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = MitiGray // Memetakan warna sekunder ke MitiGray secara otomatis di Mode Gelap
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00838F),
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onBackground = Color.Black, // Memastikan teks berwarna Hitam di atas background Putih
    onSurface = Color.Black,     // Memastikan teks berwarna Hitam di atas card/surface Putih
    onSurfaceVariant = Color.Gray
)

@Composable
fun MITITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // UBAH KE FALSE agar warna brand MITI Anda tidak tertimpa warna wallpaper HP
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}