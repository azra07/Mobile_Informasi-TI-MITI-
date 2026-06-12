package com.putrinadya.miti.domain.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val nim: String = "",
    val nip: String = "",         // TAMBAHKAN INI
    val title: String = "",       // TAMBAHKAN INI
    val department: String = ""   // TAMBAHKAN INI
)