package com.putrinadya.miti.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.putrinadya.miti.data.local.dao.MitiDao
import com.putrinadya.miti.data.local.entity.EventEntity
import com.putrinadya.miti.data.local.entity.NewsEntity

@Database(
    entities = [EventEntity::class, NewsEntity::class],
    version = 1,
    exportSchema = false
)

abstract class MitiDatabase : RoomDatabase() {
    abstract val dao: MitiDao
}