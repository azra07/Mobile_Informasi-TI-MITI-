package com.putrinadya.miti.presentation.screens.admin.dashboard

import com.putrinadya.miti.domain.model.Event

data class AdminDashboardUiState(
    val totalEvents: Int = 8,
    val activeParticipants: Int = 643,
    val newAspirations: Int = 12,
    val showCreateEventDialog: Boolean = false,
    val currentSubScreen: String = "dashboard" // "dashboard" atau "profile"
)