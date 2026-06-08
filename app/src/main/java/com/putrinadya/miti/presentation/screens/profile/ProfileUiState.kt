package com.putrinadya.miti.presentation.screens.profile

import com.putrinadya.miti.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)