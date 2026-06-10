package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.usecase.event.GetEventsUseCase

class AdminDashboardViewModel(
    private val getEventsUseCase: GetEventsUseCase = GetEventsUseCase()
) : ViewModel() {
    var uiState by mutableStateOf(AdminDashboardUiState())
        private set

    val eventsList = mutableStateListOf<Event>()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        val fetchedEvents = getEventsUseCase.execute()
        eventsList.clear()
        eventsList.addAll(fetchedEvents)
        uiState = uiState.copy(totalEvents = eventsList.size)
    }

    fun onShowCreateEventDialog(show: Boolean) {
        uiState = uiState.copy(showCreateEventDialog = show)
    }

    fun onNavigateToSubScreen(screen: String) {
        uiState = uiState.copy(currentSubScreen = screen)
    }

    fun createNewEvent(event: Event) {
        eventsList.add(0, event)
        uiState = uiState.copy(totalEvents = eventsList.size)
    }
}