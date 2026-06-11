package com.putrinadya.miti.domain.usecase.history

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActivityHistoryUseCase @Inject constructor(
    private val repository: EventRepository
){
    fun execute(): Flow<List<Event>> {
        return repository.getEvents()
    }
}