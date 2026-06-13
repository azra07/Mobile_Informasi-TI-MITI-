package com.putrinadya.miti.data.local.dao

import androidx.room.*
import com.putrinadya.miti.data.local.entity.EventEntity
import com.putrinadya.miti.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MitiDao {
    @Query("SELECT * FROM events WHERE isDraft = 0")
    fun getAllPublishedEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("DELETE FROM events WHERE isDraft = 0")
    suspend fun deleteAllPublishedEvents()

    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEventById(eventId: String)

    @Query("SELECT * FROM events WHERE isDraft = 1 LIMIT 1")
    suspend fun getAdminDraft(): EventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDraft(event: EventEntity)

    @Query("DELETE FROM events WHERE isDraft = 1")
    suspend fun deleteDraft()

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Transaction
    suspend fun updatePublishedEventsSync(events: List<EventEntity>) {
        deleteAllPublishedEvents()
        insertEvents(events)
    }

    @Query("DELETE FROM news")
    suspend fun clearNews()
}