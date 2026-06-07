package com.putrinadya.miti.data.remote.dto

data class EventDto(
    val title: String? = null,
    val category: String? = null,
    val timestamp: Long? = null,
    val location: String? = null,
    val currentParticipants: Int? = null,
    val maxParticipants: Int? = null,
    val description: String? = null
)