package com.putrinadya.miti.domain.usecase.event

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventDetailUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(): Flow<List<Event>> {
        return repository.getEvents()
    }
}