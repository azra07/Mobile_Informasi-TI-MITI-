package com.putrinadya.miti.domain.usecase.event

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import javax.inject.Inject

class RegisterToEventUseCase @Inject constructor(
    private val repository: EventRepository
){
    fun execute(event: Event, registeredList: MutableList<Event>) {
        if (!registeredList.any { it.id == event.id }) {
            registeredList.add(event)
        }
    }
}