package com.putrinadya.miti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.putrinadya.miti.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.putrinadya.miti.presentation.navigation.AppNavigation
import com.putrinadya.miti.ui.theme.MITITheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val isDarkTheme by mainViewModel.isDarkMode.collectAsState()
            MITITheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Menjalankan Navigasi Utama Aplikasi dari Start (Login)
                    AppNavigation()
                }
            }
        }
    }
}