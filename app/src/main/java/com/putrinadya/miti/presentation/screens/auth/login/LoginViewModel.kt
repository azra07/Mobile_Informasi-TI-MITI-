package com.putrinadya.miti.presentation.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailOrNimChanged(newValue: String) {
        uiState = uiState.copy(emailOrNim = newValue, error = null)
    }

    fun onPasswordChanged(newValue: String) {
        uiState = uiState.copy(password = newValue, error = null)
    }

    fun login(role: String) {
        if (uiState.emailOrNim.isBlank() || uiState.password.isBlank()) {
            uiState = uiState.copy(error = "Please fill in all fields")
            return
        }

        uiState = uiState.copy(isLoading = true, error = null)

        // Simulasi login sukses berdasarkan role yang dipilih
        uiState = uiState.copy(
            isLoading = false,
            isSuccess = true,
            userRole = role
        )
    }

    fun resetSuccessState() {
        uiState = uiState.copy(isSuccess = false)
    }
}