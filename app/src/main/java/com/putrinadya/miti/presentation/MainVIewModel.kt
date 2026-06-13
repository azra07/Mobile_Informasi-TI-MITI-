package com.putrinadya.miti.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.repository.AuthRepository
import com.putrinadya.miti.domain.repository.PreferenceRepository
import com.putrinadya.miti.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefRepo: PreferenceRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val isDarkMode = prefRepo.getThemeSetting().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), true
    )

    val hasSeenOnboarding = prefRepo.getOnboardingStatus().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch { prefRepo.saveThemeSetting(isDark) }
    }

    suspend fun getStartDestination(): String {
        val user = authRepository.getFullCurrentUser()

        return if (user == null) {
            Screen.Login.route
        } else {
            val seen = prefRepo.getOnboardingStatus().first()
            if (!seen) {
                Screen.Onboarding.route
            } else {
                if (user.role == "admin") Screen.AdminDashboard.route else Screen.StudentDashboard.route
            }
        }
    }
}