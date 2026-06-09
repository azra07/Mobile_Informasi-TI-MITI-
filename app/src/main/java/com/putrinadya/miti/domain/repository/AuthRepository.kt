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
    fun getCurrentUser(): User?
    fun logout()
}