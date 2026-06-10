package com.putrinadya.miti.presentation.screens.student.calendar

import com.putrinadya.miti.domain.model.Event

data class CalendarUiState(
    val currentMonth: String = "April 2026",
    val selectedDay: Int = 10,
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false
)