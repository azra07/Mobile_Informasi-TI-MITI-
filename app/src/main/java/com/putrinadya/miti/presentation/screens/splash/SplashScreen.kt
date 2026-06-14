package com.putrinadya.miti.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image // Import untuk memuat gambar logo asli Anda
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource // Import untuk memanggil logo drawable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.putrinadya.miti.R

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        delay(1500)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Otomatis Putih di Light Mode
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnim.value)
        ) {
            // 1. Memanggil Gambar logo.jpeg asli Anda menggantikan teks "MITI" lama
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo MITI",
                modifier = Modifier
                    .size(150.dp) // Ukuran besar proporsional untuk Splash Screen Anda
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 2. PERBAIKAN: Tagline otomatis kontras di kedua mode tema gelap maupun terang
            Text(
                text = stringResource(id = R.string.app_tagline),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), // Otomatis Abu Gelap di Light Mode, Putih di Dark Mode
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
