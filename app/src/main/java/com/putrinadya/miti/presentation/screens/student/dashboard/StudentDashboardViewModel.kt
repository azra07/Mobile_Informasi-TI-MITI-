package com.putrinadya.miti.presentation.screens.student.dashboard

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.utils.DummyData // Mengimpor data bersama yang sudah aman

class StudentDashboardViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var selectedTab by mutableStateOf(0)
        private set

    var selectedEventForRegistration by mutableStateOf<Event?>(null)
        private set

    // Daftar kegiatan terdaftar yang bersifat dinamis & real-time
    val registeredEvents = mutableStateListOf<Event>()

    // Memanggil langsung dari DummyData (Sangat ringkas dan 100% aman dari eror parameter!)
    val allEvents = DummyData.dummyEvents

    // Logika pencarian event secara aktif
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
        if (!registeredEvents.any { it.title == event.title }) {
            registeredEvents.add(event)
        }
    }

    fun unregisterForEvent(event: Event) {
        registeredEvents.removeIf { it.title == event.title }
    }
}