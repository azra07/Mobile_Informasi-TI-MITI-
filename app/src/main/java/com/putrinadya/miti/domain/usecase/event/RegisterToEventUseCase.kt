package com.putrinadya.miti.domain.usecase.event

import com.putrinadya.miti.domain.model.Event

class RegisterToEventUseCase {
    fun execute(event: Event, registeredList: MutableList<Event>) {
        if (!registeredList.any { it.title == event.title }) {
            registeredList.add(event)
        }
    }
}