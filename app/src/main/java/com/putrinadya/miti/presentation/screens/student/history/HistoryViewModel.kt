package com.putrinadya.miti.presentation.screens.student.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.putrinadya.miti.utils.DummyData

class HistoryViewModel : ViewModel() {
    var uiState by mutableStateOf(HistoryUiState())
        private set

    init {
        loadHistory()
    }

    private fun loadHistory() {
        // Simulasi memuat riwayat kegiatan mahasiswa (mengambil 3 event pertama dari DummyData)
        uiState = uiState.copy(
            pastEvents = DummyData.dummyEvents.take(3),
            isLoading = false
        )
    }
}