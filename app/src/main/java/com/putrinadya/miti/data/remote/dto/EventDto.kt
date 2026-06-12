package com.putrinadya.miti.data.remote.dto

data class EventDto(
    val id: String = "",
    val title: String? = null,
    val category: String? = null,
    val dayMonth: String? = null,
    val year: String? = null,
    val fullDate: String? = null,
    val time: String? = null,
    val location: String? = null,
    val currentParticipants: Int? = null,
    val maxParticipants: Int? = null,
    val description: String? = null,
    val categoryColorHex: String? = null
)