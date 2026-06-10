package com.putrinadya.miti.domain.usecase.event

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.utils.DummyData

class GetEventsUseCase {
    fun execute(): List<Event> {
        return DummyData.dummyEvents
    }
}