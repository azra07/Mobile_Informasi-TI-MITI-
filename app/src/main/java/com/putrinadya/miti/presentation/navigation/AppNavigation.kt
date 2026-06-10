package com.putrinadya.miti.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.putrinadya.miti.presentation.screens.auth.login.LoginPage
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardPage
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardViewModel
import com.putrinadya.miti.presentation.screens.admin.dashboard.AdminDashboardPage
import com.putrinadya.miti.presentation.screens.admin.dashboard.AdminDashboardViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        //startDestination = Screen.Login.route
        //startDestination = Screen.StudentDashboard.route
        startDestination = Screen.AdminDashboard.route
    ) {
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
            val studentViewModel: StudentDashboardViewModel = viewModel()
            StudentDashboardPage(viewModel = studentViewModel)
        }

        // 3. Halaman Dashboard Admin
        composable(Screen.AdminDashboard.route) {
            val adminViewModel: AdminDashboardViewModel = viewModel()
            AdminDashboardPage(viewModel = adminViewModel)
        }
    }
}