package com.putrinadya.miti.presentation.screens.student.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.putrinadya.miti.domain.usecase.history.GetActivityHistoryUseCase

class HistoryViewModel(
    private val getActivityHistoryUseCase: GetActivityHistoryUseCase = GetActivityHistoryUseCase()
) : ViewModel() {
    var uiState by mutableStateOf(HistoryUiState())
        private set

    init {
        loadHistory()
    }

    private fun loadHistory() {
        uiState = uiState.copy(isLoading = true)
        val historyList = getActivityHistoryUseCase.execute()
        uiState = uiState.copy(
            pastEvents = historyList,
            isLoading = false
        )
    }
}