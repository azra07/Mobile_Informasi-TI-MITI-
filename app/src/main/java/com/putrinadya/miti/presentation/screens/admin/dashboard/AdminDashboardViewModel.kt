package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.utils.DummyData

class AdminDashboardViewModel : ViewModel() {
    var uiState by mutableStateOf(AdminDashboardUiState())
        private set

    // Mengambil data awal dari DummyData secara dinamis
    val eventsList = mutableStateListOf<Event>().apply {
        addAll(DummyData.dummyEvents)
    }

    fun onShowCreateEventDialog(show: Boolean) {
        uiState = uiState.copy(showCreateEventDialog = show)
    }

    fun onNavigateToSubScreen(screen: String) {
        uiState = uiState.copy(currentSubScreen = screen)
    }

    fun createNewEvent(event: Event) {
        eventsList.add(0, event) // Tambahkan event baru di baris teratas list
        uiState = uiState.copy(totalEvents = eventsList.size)
    }
}