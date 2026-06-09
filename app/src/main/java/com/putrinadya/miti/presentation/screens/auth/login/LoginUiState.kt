package com.putrinadya.miti.presentation.screens.auth.login

data class LoginUiState(
    val emailOrNim: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val userRole: String? = null
)