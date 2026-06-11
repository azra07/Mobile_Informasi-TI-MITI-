package com.putrinadya.miti.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.putrinadya.miti.data.local.entity.EventEntity
import com.putrinadya.miti.data.remote.dto.EventDto
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

fun EventDto.toDomain(id: String): Event {
    val dateObj = Date(this.timestamp ?: 0L)

    val sdfDayMonth = SimpleDateFormat("dd MMM", Locale.getDefault())
    val sdfYear = SimpleDateFormat("yyyy", Locale.getDefault())
    val sdfFullDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val sdfTime = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val color = when (this.category?.lowercase()) {
        "workshop" -> MitiWorkshop
        "hackathon" -> MitiHackathon
        "seminar" -> MitiSeminar
        "webinar" -> MitiWebinar
        "competition" -> MitiCompetition
        "fun match" -> MitiFunMatch
        else -> MitiGray
    }

    return Event(
        id = id,
        title = this.title ?: "",
        category = this.category ?: "",
        dayMonth = sdfDayMonth.format(dateObj),
        year = sdfYear.format(dateObj),
        fullDate = sdfFullDate.format(dateObj),
        time = sdfTime.format(dateObj),
        location = this.location ?: "",
        currentParticipants = this.currentParticipants ?: 0,
        maxParticipants = this.maxParticipants ?: 0,
        description = this.description ?: "",
        categoryColor = color
    )
}

fun Event.toEntity(id: String, isDraft: Boolean = false): EventEntity {
    return EventEntity(
        id = id,
        title = title,
        category = category,
        date = fullDate,
        location = location,
        description = description,
        currentParticipants = currentParticipants,
        maxParticipants = maxParticipants,
        categoryColor = categoryColor.toArgb(),
        isDraft = isDraft
    )
}

fun EventEntity.toDomain(): Event {
    return Event(
        title = title,
        category = category,
        dayMonth = date.take(6),
        year = "2026",
        fullDate = date,
        time = "10:00 AM",
        location = location,
        currentParticipants = currentParticipants,
        maxParticipants = maxParticipants,
        description = description,
        categoryColor = Color(categoryColor)
    )
}