package com.putrinadya.miti.domain.usecase.auth

import com.putrinadya.miti.domain.model.User
import com.putrinadya.miti.domain.repository.AuthRepository
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): User? {
        return repository.getFullCurrentUser()
    }
}