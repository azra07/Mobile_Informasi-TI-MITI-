package com.putrinadya.miti.presentation.screens.student.calendar

import com.putrinadya.miti.domain.model.Event
import java.util.Calendar

data class CalendarUiState(
    val displayDate: Calendar = Calendar.getInstance(),
    val currentMonthName: String = "",
    val selectedDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    val events: List<Event> = emptyList(),
    val selectedDayEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val daysInMonth: Int = 30,
    val firstDayOfWeek: Int = 1
)