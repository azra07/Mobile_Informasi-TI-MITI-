package com.putrinadya.miti.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.putrinadya.miti.data.local.dao.MitiDao
import com.putrinadya.miti.data.mapper.toDomain
import com.putrinadya.miti.data.mapper.toEntity
import com.putrinadya.miti.data.remote.dto.EventDto
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.domain.repository.EventRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dao: MitiDao
) : EventRepository {

    override fun getEvents(): Flow<List<Event>> = callbackFlow {
        // 1. Ambil data secara real-time dari Database Lokal (Room) dan langsung kirim ke UI
        val roomJob = launch {
            try {
                dao.getAllPublishedEvents().collect { localEntities ->
                    trySend(localEntities.map { it.toDomain() })
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 2. Ambil data secara real-time dari Firestore, lalu sinkronisasi (simpan) ke Database Lokal
        val subscription = firestore.collection("events")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    launch {
                        try {
                            val entitiesToCache = snapshot.documents.mapNotNull { doc ->
                                val dto = doc.toObject(EventDto::class.java)
                                dto?.toDomain(id = doc.id)?.toEntity(
                                    id = doc.id,
                                    isDraft = false
                                )
                            }
                            // Hapus data lama di Room, timpa dengan data real-time terbaru dari Firestore
                            dao.deleteAllPublishedEvents()
                            dao.insertEvents(entitiesToCache)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

        awaitClose {
            roomJob.cancel()
            subscription.remove()
        }
    }

    override suspend fun addEvent(event: Event): Result<Unit> = try {
        // Buat referensi dokumen kosong untuk mendapatkan auto-ID unik dari Firestore
        val documentRef = firestore.collection("events").document()
        val newEvent = event.copy(id = documentRef.id) // Pasang ID unik ke dalam data event
        documentRef.set(newEvent).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateEvent(id: String, event: Event): Result<Unit> = try {
        firestore.collection("events").document(id)
            .set(event)
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteEvent(id: String): Result<Unit> = try {
        firestore.collection("events").document(id).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getTotalParticipants(): Flow<Int> = callbackFlow {
        val subscription = firestore.collection("events")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val total = snapshot?.documents?.sumOf { doc ->
                    doc.getLong("currentParticipants")?.toInt() ?: 0
                } ?: 0

                trySend(total)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun saveDraft(event: Event) {
        dao.saveDraft(
            event.toEntity(
                id = "ADMIN_DRAFT_ID",
                isDraft = true
            )
        )
    }

    override suspend fun getDraft(): Event? {
        return dao.getAdminDraft()?.toDomain()
    }

    override suspend fun clearDraft() {
        dao.deleteDraft()
    }
}