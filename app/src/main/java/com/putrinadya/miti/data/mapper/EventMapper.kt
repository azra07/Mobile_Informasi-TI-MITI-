package com.putrinadya.miti.data.mapper

import com.putrinadya.miti.data.local.entity.EventEntity
import com.putrinadya.miti.data.remote.dto.EventDto
import com.putrinadya.miti.domain.model.Event
import java.util.*

fun EventDto.toDomain(id: String): Event {
    return Event(
        id = id.ifEmpty { UUID.randomUUID().toString() },
        title = this.title ?: "",
        category = this.category ?: "",
        dayMonth = this.dayMonth ?: "Event",
        year = this.year ?: "",
        fullDate = this.fullDate ?: "",
        time = this.time ?: "",
        location = this.location ?: "",
        currentParticipants = this.currentParticipants ?: 0,
        maxParticipants = this.maxParticipants ?: 0,
        description = this.description ?: "",
        categoryColorHex = this.categoryColorHex ?: "#C583FF"
    )
}

fun Event.toEntity(id: String, isDraft: Boolean = false): EventEntity {
    val colorInt = try {
        val hexCode = categoryColorHex.ifEmpty { "#808080" }
        android.graphics.Color.parseColor(hexCode)
    } catch (e: Exception) {
        android.graphics.Color.GRAY
    }

    return EventEntity(
        id = id.ifEmpty { UUID.randomUUID().toString() },
        title = title,
        category = category,
        date = "$fullDate|$time",
        location = location,
        description = description,
        currentParticipants = currentParticipants,
        maxParticipants = maxParticipants,
        categoryColor = colorInt,
        isDraft = isDraft
    )
}

fun EventEntity.toDomain(): Event {
    val colorHex =try {
        String.format("#%06X", 0xFFFFFF and categoryColor)
    } catch (e: Exception) {
        "#C583FF"
    }

    val dateParts = date.split("|")
    val rawDate = dateParts.getOrNull(0) ?: date
    val rawTime = dateParts.getOrNull(1) ?: "10:00 WITA"

    val rawDateParts = rawDate.split("/")
    val parsedMonth = if (rawDateParts.isNotEmpty()) {
        val monthInt = rawDateParts[0].toIntOrNull()
        when (monthInt) {
            1 -> "Jan"; 2 -> "Feb"; 3 -> "Mar"; 4 -> "Apr"; 5 -> "May"; 6 -> "Jun"
            7 -> "Jul"; 8 -> "Aug"; 9 -> "Sep"; 10 -> "Oct"; 11 -> "Nov"; 12 -> "Dec"
            else -> "Event"
        }
    } else "Event"

    val parsedDay = rawDateParts.getOrNull(1)?.ifEmpty { "1" } ?: "1"
    val parsedYear = rawDateParts.getOrNull(2)?.ifEmpty { "2024" } ?: "2024"

    return Event(
        id = id,
        title = title.ifEmpty { "No Title" },
        category = category.ifEmpty { "General" },
        dayMonth = "$parsedDay $parsedMonth",
        year = parsedYear,
        fullDate = rawDate,
        time = rawTime,
        location = location,
        currentParticipants = currentParticipants,
        maxParticipants = maxParticipants,
        description = description,
        categoryColorHex = colorHex
    )
}