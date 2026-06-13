package com.putrinadya.miti.presentation.screens.onboarding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.model.OnboardingItem
import com.putrinadya.miti.R
import com.putrinadya.miti.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val prefRepo: PreferenceRepository
) : ViewModel() {
    var uiState by mutableStateOf(OnboardingUiState())
        private set

    val onboardPages = listOf(
        OnboardingItem(
            titleRes = R.string.onboarding_title_1,
            descRes = R.string.onboarding_desc_1,
            icon = Icons.Default.Notifications,
            iconBg = Color(0xFF0F3D8C)
        ),
        OnboardingItem(
            titleRes = R.string.onboarding_title_2,
            descRes = R.string.onboarding_desc_2,
            icon = Icons.Default.BusinessCenter,
            iconBg = Color(0xFF13B5B1) // Tosca
        ),
        OnboardingItem(
            titleRes = R.string.onboarding_title_3,
            descRes = R.string.onboarding_desc_3,
            icon = Icons.Default.BarChart,
            iconBg = Color(0xFF0F3D8C) // Biru Pekat
        )
    )

    val totalPages get() = onboardPages.size

    fun onNextPage() {
        val nextPage = uiState.currentPage + 1
       if (nextPage < totalPages) {
            uiState = uiState.copy(currentPage = nextPage)
        } else {
            viewModelScope.launch {
                prefRepo.saveOnboardingStatus(true)
                uiState.copy(isCompleted = true)
            }
        }
    }
    fun onPageSwiped(index: Int) {
        uiState = uiState.copy(currentPage = index)
    }

    fun onSkip() {
        uiState = uiState.copy(isCompleted = true)
    }
}