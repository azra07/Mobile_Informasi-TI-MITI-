package com.putrinadya.miti.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.putrinadya.miti.presentation.screens.splash.SplashScreen
import com.putrinadya.miti.presentation.screens.onboarding.OnboardingPage
import com.putrinadya.miti.presentation.screens.auth.login.LoginPage
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardPage
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardViewModel
import com.putrinadya.miti.presentation.screens.admin.dashboard.AdminDashboardPage
import com.putrinadya.miti.presentation.screens.admin.dashboard.AdminDashboardViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route // 1. MULAI UTAMA DARI SPLASH SCREEN
    ) {
        // 1. Splash Screen Route
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashComplete = {
                    // Setelah Splash selesai, lompat ke Onboarding & bersihkan Splash dari tumpukan memori
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Onboarding Page Route
        composable(Screen.Onboarding.route) {
            OnboardingPage(
                onOnboardingComplete = {
                    // Setelah Onboarding selesai, lompat ke Login & bersihkan Onboarding dari tumpukan memori
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // 3. Login Page Route
        composable(Screen.Login.route) {
            LoginPage(
                onNavigateToStudentDashboard = {
                    navController.navigate(Screen.StudentDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToAdminDashboard = {
                    navController.navigate(Screen.AdminDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // 4. Dashboard Mahasiswa Route
        composable(Screen.StudentDashboard.route) {
            val studentViewModel: StudentDashboardViewModel = hiltViewModel()
            StudentDashboardPage(
                viewModel = studentViewModel,
                navController = navController // Passed the navController here
            )
        }

        // 5. Dashboard Admin Route
        composable(Screen.AdminDashboard.route) {
            val adminViewModel: AdminDashboardViewModel = hiltViewModel()
            AdminDashboardPage(
                viewModel = adminViewModel,
                navController = navController // <--- Tambahkan parameter ini
            )
        }
    }
}