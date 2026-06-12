package com.putrinadya.miti.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putrinadya.miti.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    // Animasi Fade-In logo memudar masuk yang halus
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        // Logo memudar masuk selama 1 detik
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        // Tahan tampilan logo selama 1.5 detik
        delay(1500)
        // Pindah rute secara otomatis setelah durasi selesai
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MitiNavy),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnim.value)
        ) {
            Text(
                text = "MITI",
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold,
                color = MitiCyan,
                letterSpacing = 4.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "IT Activity Hub",
                fontSize = 16.sp,
                color = MitiWhite,
                letterSpacing = 1.sp
            )
        }
    }
}