package com.putrinadya.miti.domain.model

data class Event(
    val id: String = "",
    val title: String = "",
    val category: String = "",
    val dayMonth: String = "",
    val year: String = "",
    val fullDate: String = "",
    val time: String = "",
    val location: String = "",
    val currentParticipants: Int = 0,
    val maxParticipants: Int = 0,
    val description: String = "",
    val categoryColorHex: String = ""
)