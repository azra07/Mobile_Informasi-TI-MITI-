package com.putrinadya.miti.data.repository

import android.content.Context
import com.putrinadya.miti.domain.repository.PreferenceRepository
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class PreferenceRepositoryImpl(private val context: Context) : PreferenceRepository {
    private val THEME_KEY = booleanPreferencesKey("is_dark_mode")
    private val ONBOARDING_KEY = booleanPreferencesKey("is_onboarding_done")

    override suspend fun saveThemeSetting(isDarkMode: Boolean) {
        context.dataStore.edit { it[THEME_KEY] = isDarkMode }
    }

    override fun getThemeSetting(): Flow<Boolean> = context.dataStore.data.map {
        it[THEME_KEY] ?: true // Default dark mode true
    }

    override suspend fun saveOnboardingStatus(isDone: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = isDone
        }
    }

    override fun getOnboardingStatus(): Flow<Boolean> = context.dataStore.data.map {
        it[ONBOARDING_KEY] ?: false // Default false jika belum pernah buka
    }
}