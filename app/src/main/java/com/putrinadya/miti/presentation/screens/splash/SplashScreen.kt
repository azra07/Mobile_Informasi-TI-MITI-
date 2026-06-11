package com.putrinadya.miti.presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.putrinadya.miti.presentation.navigation.Screen
import com.putrinadya.miti.presentation.screens.auth.login.LoginViewModel
import com.putrinadya.miti.ui.theme.MitiNavy
import com.putrinadya.miti.ui.theme.MitiCyan
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        delay(1500) // Memberi waktu 1.5 detik agar logo terlihat
        val destination = viewModel.checkUserSession()
        navController.navigate(destination) {
            popUpTo("splash") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MitiNavy),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "MITI", color = MitiCyan, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text(text = "IT Activity Hub", color = Color.White, fontSize = 16.sp)
        }
    }
}