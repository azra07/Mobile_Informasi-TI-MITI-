package com.putrinadya.miti.presentation.screens.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun onNameChanged(newValue: String) {
        uiState = uiState.copy(name = newValue, error = null)
    }

    fun onEmailChanged(newValue: String) {
        uiState = uiState.copy(email = newValue, error = null)
    }

    fun onNimChanged(newValue: String) {
        uiState = uiState.copy(nim = newValue, error = null)
    }

    fun onPasswordChanged(newValue: String) {
        uiState = uiState.copy(password = newValue, error = null)
    }

    fun register() {
        if (uiState.name.isBlank() || uiState.email.isBlank() || uiState.nim.isBlank() || uiState.password.isBlank()) {
            uiState = uiState.copy(error = "Please fill in all fields")
            return
        }

        uiState = uiState.copy(isLoading = true, error = null)

        // Simulasi registrasi sukses
        uiState = uiState.copy(
            isLoading = false,
            isSuccess = true
        )
    }

    fun resetSuccessState() {
        uiState = uiState.copy(isSuccess = false)
    }
}