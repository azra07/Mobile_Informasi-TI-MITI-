package com.putrinadya.miti.domain.repository

import com.putrinadya.miti.domain.model.Aspirations
import kotlinx.coroutines.flow.Flow

interface AspirationRepository {
    suspend fun sendAspiration(aspiration: Aspirations): Result<Unit>
    fun getAllAspirations(): Flow<List<Aspirations>>
    fun getAspirationCount(): Flow<Int>
}