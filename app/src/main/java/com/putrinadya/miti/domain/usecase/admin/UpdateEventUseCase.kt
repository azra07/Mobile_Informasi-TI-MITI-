package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.model.Event

class UpdateEventUseCase {
    fun execute(oldEvent: Event, newEvent: Event, eventList: MutableList<Event>) {
        val index = eventList.indexOfFirst { it.title == oldEvent.title }
        if (index != -1) {
            eventList[index] = newEvent
        }
    }
}