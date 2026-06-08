package com.putrinadya.miti.presentation.screens.student.history

import com.putrinadya.miti.domain.model.Event

data class HistoryUiState(
    val pastEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false
)