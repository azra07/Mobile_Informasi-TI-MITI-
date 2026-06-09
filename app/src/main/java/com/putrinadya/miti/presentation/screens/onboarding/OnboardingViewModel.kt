package com.putrinadya.miti.presentation.screens.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.putrinadya.miti.domain.model.OnboardingItem

class OnboardingViewModel : ViewModel() {
    var uiState by mutableStateOf(OnboardingUiState())
        private set

    val onboardPages = listOf(
        OnboardingItem(

        )
    )

    val totalPages get() = onboardPages.size

    fun onNextPage() {
        val nextPage = uiState.currentPage + 1
        if (nextPage < totalPages) {
            uiState = uiState.copy(currentPage = nextPage)
        } else {
            uiState = uiState.copy(isCompleted = true)
        }
    }

    fun onSkip() {
        uiState = uiState.copy(isCompleted = true)
    }
}