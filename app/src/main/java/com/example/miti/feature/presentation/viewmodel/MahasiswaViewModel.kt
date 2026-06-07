package com.example.miti.feature.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.miti.feature.domain.model.Event

class MahasiswaViewModel : ViewModel() {
    // State pencarian
    var searchQuery by mutableStateOf("")
        private set

    // State tab navigasi (0: Home, 1: Calendar, 2: Profile)
    var selectedTab by mutableStateOf(0)
        private set

    // State pop-up pendaftaran event
    var selectedEventForRegistration by mutableStateOf<Event?>(null)
        private set

    // Daftar kegiatan yang diikuti secara dinamis (State global mahasiswa)
    val registeredEvents = mutableStateListOf<Event>()

    // Daftar dummy events
    val allEvents = listOf(
        Event("UI/UX Design Workshop", "Workshop", "Apr", "15", "10:00 AM", "Tech Hub, Room 301", 42, 50, "Learn the fundamentals of user interface and experience design...", Color(0xFFC583FF)),
        Event("Annual Hackathon 2026", "Hackathon", "Apr", "20", "9:00 AM", "Innovation Center", 156, 200, "Join the biggest competitive hackathon of the year...", Color(0xFF00E676)),
        Event("Cloud Computing Seminar", "Seminar", "Apr", "18", "2:00 PM", "Auditorium A", 78, 100, "Explore the future of scalable cloud infrastructure...", Color(0xFF00B0FF)),
        Event("Cybersecurity Webinar", "Webinar", "Apr", "22", "3:00 PM", "Online", 189, 300, "Learn practical techniques to defend systems from cyber threats...", Color(0xFF2979FF)),
        Event("AI/ML Competition", "Competition", "Apr", "25", "10:00 AM", "Data Science Lab", 65, 80, "Showcase your artificial intelligence models in this competition...", Color(0xFFFFD600)),
        Event("Mobile App Development", "Workshop", "Apr", "28", "1:00 PM", "Tech Hub, Room 205", 38, 40, "Build cross-platform applications using modern frameworks...", Color(0xFFC583FF)),
        Event("Blockchain Fundamentals", "Seminar", "May", "02", "11:00 AM", "Seminar Hall B", 45, 120, "An in-depth session on decentralized ledger technologies...", Color(0xFF00B0FF)),
        Event("Badminton", "Fun Match", "May", "05", "8:00 AM", "Open Space", 6, 100, "Friendly weekend sports matches with fellow tech students...", Color(0xFFFF8A80))
    )

    // Filter events berdasarkan query pencarian secara aktif
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