package com.putrinadya.miti.data.mapper

import com.putrinadya.miti.data.local.entity.EventEntity
import com.putrinadya.miti.data.remote.dto.EventDto
import com.putrinadya.miti.domain.model.Event
import java.text.SimpleDateFormat
import java.util.*

fun EventDto.toDomain(id: String): Event {
    return Event(
        id = id,
        title = this.title ?: "",
        category = this.category ?: "",
        dayMonth = this.dayMonth ?: "Event",
        year = this.year ?: "",
        fullDate = this.fullDate ?: "",
        time = this.time ?: "", // Membaca jam dinamis dari Firestore
        location = this.location ?: "",
        currentParticipants = this.currentParticipants ?: 0,
        maxParticipants = this.maxParticipants ?: 0,
        description = this.description ?: "",
        categoryColorHex = this.categoryColorHex ?: "#C583FF"
    )
}

fun Event.toEntity(id: String, isDraft: Boolean = false): EventEntity {
    val colorInt = try {
        android.graphics.Color.parseColor(categoryColorHex)
    } catch (e: Exception) {
        android.graphics.Color.GRAY
    }

    return EventEntity(
        id = id,
        title = title,
        category = category,
        date = "$fullDate|$time", // SOLUSI: Menggabungkan Tanggal dan Waktu agar tersimpan di SQLite Room
        location = location,
        description = description,
        currentParticipants = currentParticipants,
        maxParticipants = maxParticipants,
        categoryColor = colorInt,
        isDraft = isDraft
    )
}

fun EventEntity.toDomain(): Event {
    val colorHex = String.format("#%06X", 0xFFFFFF and categoryColor)

    // Memecah kembali data Tanggal dan Waktu yang digabung tadi
    val dateParts = date.split("|")
    val rawDate = dateParts.getOrNull(0) ?: date
    val rawTime = dateParts.getOrNull(1) ?: "10:00 WITA" // Fallback jika data lama kosong

    // Logika Parser tanggal dinamis dari rawDate ("MM/DD/YYYY")
    val rawDateParts = rawDate.split("/")
    val parsedMonth = if (rawDateParts.isNotEmpty()) {
        when (rawDateParts[0].toIntOrNull()) {
            1 -> "Jan"; 2 -> "Feb"; 3 -> "Mar"; 4 -> "Apr"; 5 -> "May"; 6 -> "Jun"
            7 -> "Jul"; 8 -> "Aug"; 9 -> "Sep"; 10 -> "Oct"; 11 -> "Nov"; 12 -> "Dec"
            else -> "Event"
        }
    } else "Event"

    val parsedDay = if (rawDateParts.size >= 2) rawDateParts[1] else "1"

    return Event(
        id = id,
        title = title,
        category = category,
        dayMonth = parsedMonth,
        year = parsedDay,
        fullDate = rawDate,
        time = rawTime, // Waktu kini berhasil dimuat secara dinamis dari database Room!
        location = location,
        currentParticipants = currentParticipants,
        maxParticipants = maxParticipants,
        description = description,
        categoryColorHex = colorHex
    )
}