package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.repository.EventRepository
import javax.inject.Inject

class DeleteAllEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.deleteAllEvents()
    }
}