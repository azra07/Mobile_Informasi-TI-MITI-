package com.putrinadya.miti.presentation.screens.student.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.putrinadya.miti.domain.usecase.event.GetEventsUseCase

class CalendarViewModel(
    private val getEventsUseCase: GetEventsUseCase = GetEventsUseCase()
) : ViewModel() {
    var uiState by mutableStateOf(CalendarUiState())
        private set

    init {
        loadEvents()
    }

    private fun loadEvents() {
        uiState = uiState.copy(isLoading = true)
        val fetchedEvents = getEventsUseCase.execute()
        uiState = uiState.copy(
            events = fetchedEvents,
            isLoading = false
        )
    }

    fun onDaySelected(day: Int) {
        uiState = uiState.copy(selectedDay = day)
    }
}