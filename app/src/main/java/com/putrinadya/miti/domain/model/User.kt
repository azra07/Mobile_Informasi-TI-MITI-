package com.putrinadya.miti.domain.model

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val role: String,      // "admin" atau "student"
    val nim: String = ""
)