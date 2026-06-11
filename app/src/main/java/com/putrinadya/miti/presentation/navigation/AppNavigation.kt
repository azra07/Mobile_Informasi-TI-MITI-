package com.putrinadya.miti.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.putrinadya.miti.presentation.navigation.Screen
import com.putrinadya.miti.presentation.screens.admin.dashboard.AdminDashboardPage
import com.putrinadya.miti.presentation.screens.admin.dashboard.AdminDashboardViewModel
import com.putrinadya.miti.presentation.screens.auth.login.LoginPage
import com.putrinadya.miti.presentation.screens.news.NewsDetailScreen
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardPage
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardViewModel
import com.putrinadya.miti.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // 0. Halaman Splash
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        // 1. Halaman Login
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

        // 2. Halaman Dashboard Mahasiswa
        composable(Screen.StudentDashboard.route) {
            val studentViewModel: StudentDashboardViewModel = hiltViewModel()
            StudentDashboardPage(
                viewModel = studentViewModel,
                navController = navController
            )
        }

        composable(
            "news_detail/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            NewsDetailScreen(
                url = url,
                onBackClick = { navController.popBackStack() }
            )
        }

        // 3. Halaman Dashboard Admin
        composable(Screen.AdminDashboard.route) {
            val adminViewModel: AdminDashboardViewModel = hiltViewModel()
            AdminDashboardPage(viewModel = adminViewModel)
        }
    }
}