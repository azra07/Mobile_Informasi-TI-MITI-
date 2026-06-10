package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.model.Event

class DeleteEventUseCase {
    fun execute(event: Event, eventList: MutableList<Event>) {
        eventList.removeIf { it.title == event.title }
    }
}