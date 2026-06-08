package com.putrinadya.miti.presentation.screens.admin.event_management

import com.putrinadya.miti.domain.model.Event

data class AddEditEventUiState(
    val event: Event? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)