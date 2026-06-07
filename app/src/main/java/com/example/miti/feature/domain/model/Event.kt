package com.example.miti.feature.domain.model

import androidx.compose.ui.graphics.Color

data class Event(
    val title: String,
    val category: String,
    val dateMonth: String,
    val dateDay: String,
    val time: String,
    val location: String,
    val currentParticipants: Int,
    val maxParticipants: Int,
    val description: String,
    val categoryColor: Color
)