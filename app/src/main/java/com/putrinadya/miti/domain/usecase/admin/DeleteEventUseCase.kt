package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(event: Event): Result<Unit> {
        return repository.deleteEvent(event.id)
    }
}