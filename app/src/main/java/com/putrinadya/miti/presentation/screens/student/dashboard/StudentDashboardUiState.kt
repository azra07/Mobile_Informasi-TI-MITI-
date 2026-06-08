package com.putrinadya.miti.presentation.screens.student.dashboard

import com.putrinadya.miti.domain.model.Event

data class StudentDashboardUiState(
    val searchQuery: String = "",
    val selectedTab: Int = 0,
    val selectedEventForRegistration: Event? = null,
    val registeredEvents: List<Event> = emptyList(),
    val allEvents: List<Event> = emptyList(),
    val filteredEvents: List<Event> = emptyList()
)