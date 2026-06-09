package com.putrinadya.miti.domain.usecase.auth

import com.putrinadya.miti.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        password: String,
        role: String,
        nim: String
    ): Flow<Result<Unit>> {
        return repository.register(name, email, password, role, nim)
    }
}