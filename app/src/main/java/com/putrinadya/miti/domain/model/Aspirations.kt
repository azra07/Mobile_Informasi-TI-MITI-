package com.putrinadya.miti.domain.model

class Aspirations(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val content: String = "",
    val category: String = "", // misal: Fasilitas, Akademik, Umum
    val timestamp: Long = System.currentTimeMillis(),
    val isAnonymous: Boolean = false
)