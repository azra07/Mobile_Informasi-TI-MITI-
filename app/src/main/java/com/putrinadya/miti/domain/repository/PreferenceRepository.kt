package com.putrinadya.miti.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    suspend fun saveOnboardingStatus(isDone: Boolean)
    fun getOnboardingStatus(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkMode: Boolean)
    fun getThemeSetting(): Flow<Boolean>
}