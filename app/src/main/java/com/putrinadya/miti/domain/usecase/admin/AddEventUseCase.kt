package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.model.Event

class AddEventUseCase {
    fun execute(event: Event, eventList: MutableList<Event>) {
        eventList.add(0, event) // Menambahkan ke baris teratas list
    }
}