package com.putrinadya.miti.presentation.screens.student.dashboard

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import com.putrinadya.miti.domain.usecase.event.RegisterToEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentDashboardViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val registerToEventUseCase: RegisterToEventUseCase
) : ViewModel() {

    private val _allEvents = MutableStateFlow<List<Event>>(emptyList())
    val allEvents: StateFlow<List<Event>> = _allEvents.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    var selectedTab by mutableStateOf(0)
        private set

    var selectedEventForRegistration by mutableStateOf<Event?>(null)
        private set

    val registeredEvents = mutableStateListOf<Event>()

    // Mengambil data melalui Use Case sesuai Clean Architecture
    init {
        loadEventsFromRepository()
    }

    private fun loadEventsFromRepository() {
        viewModelScope.launch {
            eventRepository.getEvents().collectLatest { list ->
                _allEvents.value = list
            }
        }
    }

    val filteredEvents: List<Event>
        get() = if (searchQuery.isEmpty()) {
            _allEvents.value
        } else {
            _allEvents.value.filter {
                it.title.contains(searchQuery, ignoreCase = true) || it.category.contains(searchQuery, ignoreCase = true)
            }
        }

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
    }

    fun onTabSelected(tabIndex: Int) {
        selectedTab = tabIndex
    }

    fun onEventSelected(event: Event?) {
        selectedEventForRegistration = event
    }

    fun registerForEvent(event: Event) {
        registerToEventUseCase.execute(event, registeredEvents)
    }

    fun unregisterForEvent(event: Event) {
        registeredEvents.removeIf { it.title == event.title }
    }
}