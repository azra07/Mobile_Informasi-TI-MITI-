package com.putrinadya.miti.domain.repository

import com.putrinadya.miti.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    // Membaca daftar event secara real-time dari Firestore menggunakan Flow
    fun getEvents(): Flow<List<Event>>

    // Menyimpan data event baru ke Firestore
    suspend fun addEvent(event: Event): Result<Unit>

    // Memperbarui data event di Firestore dengan pengembalian Result untuk deteksi error
    suspend fun updateEvent(id: String, event: Event): Result<Unit>
    suspend fun deleteEvent(id: String): Result<Unit>
    suspend fun deleteAllEvents(): Result<Unit>
    // Mengambil total partisipan secara real-time
    fun getTotalParticipants(): Flow<Int>

    // Manajemen draft event (lokal)
    suspend fun saveDraft(event: Event)
    suspend fun getDraft(): Event?
    suspend fun clearDraft()
}