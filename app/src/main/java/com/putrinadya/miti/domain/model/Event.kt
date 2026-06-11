package com.putrinadya.miti.domain.model

import androidx.compose.ui.graphics.Color

data class Event(
    val id: String = "",
    val title: String,
    val category: String,
    val dayMonth: String,
    val year: String,
    val fullDate: String,
    val time: String,
    val location: String,
    val currentParticipants: Int,
    val maxParticipants: Int,
    val description: String,
    val categoryColor: Color
)