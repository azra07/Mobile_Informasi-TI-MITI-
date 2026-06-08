package com.putrinadya.miti.presentation.screens.auth.register

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val nim: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)