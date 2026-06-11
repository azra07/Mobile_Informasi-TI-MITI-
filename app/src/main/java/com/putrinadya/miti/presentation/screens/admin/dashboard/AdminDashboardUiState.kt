package com.putrinadya.miti.presentation.screens.admin.dashboard

import com.putrinadya.miti.domain.model.Event

data class AdminDashboardUiState(
    val totalEvents: Int = 0,
    val activeParticipants: Int = 0,
    val newAspirations: Int = 0,
    val showCreateEventDialog: Boolean = false,
    val showEditEventDialog: Boolean = false,
    val selectedEventForEdit: Event? = null,
    val currentSubScreen: String = "dashboard"
)