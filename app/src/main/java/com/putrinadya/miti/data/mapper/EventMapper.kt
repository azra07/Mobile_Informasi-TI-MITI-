package com.putrinadya.miti.data.mapper

import com.putrinadya.miti.data.remote.dto.EventDto
import com.putrinadya.miti.domain.model.Event
import java.text.SimpleDateFormat
import java.util.*

fun EventDto.toDomain(): Event {
    val dateObj = Date(this.timestamp ?: 0L)

    // Logic merapikan tanggal ada di sini (Tugas Engine)
    val sdfDayMonth = SimpleDateFormat("dd MMM", Locale.getDefault())
    val sdfYear = SimpleDateFormat("yyyy", Locale.getDefault())
    val sdfFullDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val sdfTime = SimpleDateFormat("hh:mm a", Locale.getDefault())

    return Event(
        title = this.title ?: "Tanpa Judul",
        category = this.category ?: "General",
        dayMonth = sdfDayMonth.format(dateObj),
        year = sdfYear.format(dateObj),
        fullDate = sdfFullDate.format(dateObj),
        time = sdfTime.format(dateObj),
        location = this.location ?: "Lokasi Belum Ditentukan",
        currentParticipants = this.currentParticipants ?: 0,
        maxParticipants = this.maxParticipants ?: 0,
        description = this.description ?: ""
    )
}