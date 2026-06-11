package com.putrinadya.miti.data.remote.dto

data class NewsResponse(
    val articles: List<ArticleDto>
)

data class ArticleDto(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val source: SourceDto?,
    val publishedAt: String?
)

data class SourceDto(
    val name: String
)