package com.putrinadya.miti.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : EventRepository {
    override fun getEvents(): Flow<List<Event>> { TODO() }
}