package com.putrinadya.miti.domain.usecase.admin

import com.putrinadya.miti.domain.model.Event
import javax.inject.Inject

class AddEventUseCase @Inject constructor(){
    fun execute(event: Event, eventsList: MutableList<Event>) {
        eventsList.add(0, event)
    }
}