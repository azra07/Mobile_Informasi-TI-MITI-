package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.model.Event
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(){
    fun execute(event: Event, eventsList: MutableList<Event>) {
        eventsList.removeIf { it.id == event.id }
    }
}