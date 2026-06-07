package com.putrinadya.miti.domain.model

data class Event(
    val title: String,
    val category: String,
    val dayMonth: String,      // Misal: "15 Apr"
    val year: String,          // Misal: "2026"
    val fullDate: String,      // Misal: "15 April 2026"
    val time: String,          // Misal: "10:00 AM"
    val location: String,
    val currentParticipants: Int,
    val maxParticipants: Int,
    val description: String
)