package com.putrinadya.miti.presentation.screens.student.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.domain.usecase.event.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    var uiState by mutableStateOf(CalendarUiState())
        private set

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
           getEventsUseCase().collect { fetchedEvents ->
               uiState = uiState.copy(
                   events = fetchedEvents,
                   isLoading = false
               )
           }
        }
    }

    fun onDaySelected(day: Int) {
        uiState = uiState.copy(selectedDay = day)
    }
}