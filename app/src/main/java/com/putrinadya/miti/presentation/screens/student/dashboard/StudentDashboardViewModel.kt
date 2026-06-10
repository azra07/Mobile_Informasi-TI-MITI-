package com.putrinadya.miti.presentation.screens.student.dashboard

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.usecase.event.GetEventsUseCase
import com.putrinadya.miti.domain.usecase.event.RegisterToEventUseCase

class StudentDashboardViewModel(
    private val getEventsUseCase: GetEventsUseCase = GetEventsUseCase(),
    private val registerToEventUseCase: RegisterToEventUseCase = RegisterToEventUseCase()
) : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var selectedTab by mutableStateOf(0)
        private set

    var selectedEventForRegistration by mutableStateOf<Event?>(null)
        private set

    val registeredEvents = mutableStateListOf<Event>()

    // Mengambil data melalui Use Case sesuai Clean Architecture
    val allEvents = getEventsUseCase.execute()

    val filteredEvents: List<Event>
        get() = if (searchQuery.isEmpty()) {
            allEvents
        } else {
            allEvents.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.category.contains(searchQuery, ignoreCase = true)
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