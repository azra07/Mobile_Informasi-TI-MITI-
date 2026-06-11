package com.putrinadya.miti.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val date: String,
    val location: String,
    val description: String,
    val currentParticipants: Int,
    val maxParticipants: Int,
    val categoryColor: Int,
    val isDraft: Boolean = false
)