package com.putrinadya.miti.presentation.screens.student.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.usecase.history.GetActivityHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getActivityHistoryUseCase: GetActivityHistoryUseCase
) : ViewModel() {
    var uiState by mutableStateOf(HistoryUiState())
        private set

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            getActivityHistoryUseCase.execute().collect { historyList ->
                uiState = uiState.copy(
                    pastEvents = historyList,
                    isLoading = false
                )
            }
        }
    }
}