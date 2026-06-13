package com.putrinadya.miti.presentation.screens.student.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.usecase.event.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    var uiState by mutableStateOf(CalendarUiState())
        private set

    init {
        updateCalendarDisplay()
        loadEvents()
    }

    fun nextMonth() {
        uiState.displayDate.add(Calendar.MONTH, 1)
        updateCalendarDisplay()
    }

    fun previousMonth() {
        uiState.displayDate.add(Calendar.MONTH, -1)
        updateCalendarDisplay()
    }

    private fun updateCalendarDisplay() {
        val cal = uiState.displayDate
        val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(cal.time)
        val daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDay = cal.clone() as Calendar
        firstDay.set(Calendar.DAY_OF_MONTH, 1)
        val dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK) - 1

        uiState = uiState.copy(
            currentMonthName = monthName,
            daysInMonth = daysCount,
            firstDayOfWeek = dayOfWeek
        )
    }

    private fun loadEvents() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            getEventsUseCase().collect { fetchedEvents ->
                uiState = uiState.copy(events = fetchedEvents)
                updateSelectedDayEvents(uiState.selectedDay)
           }
        }
    }

    fun onDaySelected(day: Int) {
        uiState = uiState.copy(selectedDay = day)
        updateSelectedDayEvents(day)
    }

    private fun updateSelectedDayEvents(day: Int) {
        val month = uiState.displayDate.get(Calendar.MONTH) + 1
        val year = uiState.displayDate.get(Calendar.YEAR)
        val dateToMatch = "$month/$day/$year"

        val filtered = uiState.events.filter { it.fullDate == dateToMatch }
        uiState = uiState.copy(selectedDayEvents = filtered)
    }

    fun getEventsForDate(day: Int): List<Event> {
        val month = uiState.displayDate.get(Calendar.MONTH) + 1
        val year = uiState.displayDate.get(Calendar.YEAR)
        val dateToMatch = "$month/$day/$year"
        return uiState.events.filter { it.fullDate == dateToMatch }
    }
}