package com.putrinadya.miti.domain.repository

import com.putrinadya.miti.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
    suspend fun updateEvent(id: String, event: Event)
    fun getTotalParticipants(): Flow<Int>
    suspend fun saveDraft(event: Event)
    suspend fun getDraft(): Event?
    suspend fun clearDraft()
}