package com.putrinadya.miti.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object StudentDashboard : Screen("student_dashboard")
    object AdminDashboard : Screen("admin_dashboard")
}