package com.putrinadya.miti.presentation.screens.admin.event_management

import androidx.lifecycle.ViewModel
import com.putrinadya.miti.domain.usecase.admin.AddEventUseCase
import com.putrinadya.miti.domain.usecase.admin.UpdateEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddEditEventViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEditEventUiState())
    val uiState: StateFlow<AddEditEventUiState> = _uiState.asStateFlow()
}