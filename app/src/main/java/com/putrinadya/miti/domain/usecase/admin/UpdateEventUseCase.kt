package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend fun execute(newEvent: Event, eventsList: MutableList<Event>) {
        val index = eventsList.indexOfFirst { it.id == newEvent.id }
        if (index != -1) {
            eventsList[index] = newEvent
        }
        repository.updateEvent(newEvent.id, newEvent)
    }
}