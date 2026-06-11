package com.putrinadya.miti.domain.model

data class News(
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val source: String,
    val publishedAt: String
)