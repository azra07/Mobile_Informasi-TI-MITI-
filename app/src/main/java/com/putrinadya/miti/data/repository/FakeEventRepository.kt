package com.putrinadya.miti.data.repository

import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import com.putrinadya.miti.utils.DummyData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeEventRepository : EventRepository {
    override fun getEvents(): Flow<List<Event>> = flow {
        // Simulasi loading data
        emit(DummyData.dummyEvents)
    }
}