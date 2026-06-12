package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val repository: EventRepository
) {
    // Fungsi 'suspend' untuk proses asynchronous saving ke Firestore
    suspend operator fun invoke(event: Event): Result<Unit> {
        return repository.addEvent(event)
    }
}