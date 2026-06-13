package com.putrinadya.miti.domain.usecase.auth

import com.putrinadya.miti.domain.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String) = repository.sendPasswordResetEmail(email)
}