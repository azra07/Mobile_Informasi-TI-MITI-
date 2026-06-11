package com.putrinadya.miti.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val source: String,
    val publishedAt: String
)