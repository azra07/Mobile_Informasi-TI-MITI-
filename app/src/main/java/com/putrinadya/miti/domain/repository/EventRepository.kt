package com.putrinadya.miti.domain.repository

import com.putrinadya.miti.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
}