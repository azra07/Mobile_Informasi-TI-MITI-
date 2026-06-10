package com.putrinadya.miti.presentation.screens.admin.dashboard

data class AdminDashboardUiState(
    val totalEvents: Int = 0,
    val activeParticipants: Int = 643,
    val newAspirations: Int = 12,
    val showCreateEventDialog: Boolean = false,
    val currentSubScreen: String = "dashboard" // "dashboard" atau "profile"
)