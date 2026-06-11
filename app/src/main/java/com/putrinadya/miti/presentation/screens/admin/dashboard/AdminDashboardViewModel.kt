package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import com.putrinadya.miti.domain.usecase.admin.AddEventUseCase
import com.putrinadya.miti.domain.usecase.admin.DeleteEventUseCase
import com.putrinadya.miti.domain.usecase.admin.UpdateEventUseCase
import com.putrinadya.miti.domain.usecase.event.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val repository: EventRepository,
    private val getEventsUseCase: GetEventsUseCase,
    private val addEventUseCase: AddEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase
) : ViewModel() {
    var uiState by mutableStateOf(AdminDashboardUiState())
        private set

    var eventsList = mutableStateListOf<Event>()
        private set

    init {
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            repository.getTotalParticipants().collect { participantCount ->
                uiState = uiState.copy(activeParticipants = participantCount)
            }
        }

        viewModelScope.launch {
            getEventsUseCase().collect { fetchedEvents ->
                eventsList.clear()
                eventsList.addAll(fetchedEvents)
                uiState = uiState.copy(totalEvents = eventsList.size)
            }
        }
    }

    fun createNewEvent(event: Event) {
        viewModelScope.launch {
            addEventUseCase.execute(event, eventsList)
            uiState = uiState.copy(totalEvents = eventsList.size)
        }
    }

    fun onEditEventClick(event: Event) {
        uiState = uiState.copy(
            selectedEventForEdit = event,
            showEditEventDialog = true
        )
    }

    fun updateEvent(newEvent: Event) {
        val oldEvent = uiState.selectedEventForEdit
        if (oldEvent != null) {
            viewModelScope.launch {
                val eventToUpdate = newEvent.copy(id = oldEvent.id)
                updateEventUseCase.execute(eventToUpdate, eventsList)
                uiState = uiState.copy(
                    showEditEventDialog = false,
                    selectedEventForEdit = null
                )
            }
        }
    }

    fun removeEvent(event: Event) {
        viewModelScope.launch {
            deleteEventUseCase.execute(event, eventsList)
            uiState = uiState.copy(totalEvents = eventsList.size)
        }
    }

    fun onShowCreateEventDialog(show: Boolean) {
        uiState = uiState.copy(showCreateEventDialog = show)
    }

    fun onCancelEdit() {
        uiState = uiState.copy(showEditEventDialog = false)
    }

    fun onNavigateToSubScreen(screen: String) {
        uiState = uiState.copy(currentSubScreen = screen)
    }
}