package com.putrinadya.miti.domain.repository

import com.putrinadya.miti.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(
        email: String,
        password: String
    ): Flow<Result<User>>

    fun register(
        name: String,
        email: String,
        password: String,
        role: String,
        nim: String
    ): Flow<Result<Unit>>

    // Menyimpan versi SUSPEND saja yang digunakan oleh AuthRepositoryImpl.kt
    suspend fun registerStudent(
        name: String,
        email: String,
        pass: String,
        nim: String
    ): Result<Unit>

    suspend fun updateUserProfile(
        name: String,
        nim: String,
        email: String
    ): Result<Unit>

    fun getCurrentUser(): User?
    suspend fun getFullCurrentUser(): User?
    fun getStudentCount(): Flow<Int>
    fun sendPasswordResetEmail(email: String): Flow<Result<Unit>>
    fun logout()
}