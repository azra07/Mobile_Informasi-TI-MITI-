package com.putrinadya.miti.domain.usecase.auth

import com.putrinadya.miti.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String) = repository.login(email, password)
}