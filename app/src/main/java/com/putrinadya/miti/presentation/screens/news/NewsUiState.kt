package com.putrinadya.miti.presentation.screens.news

import com.putrinadya.miti.domain.model.News

data class NewsUiState(
    val isLoading: Boolean = false,
    val newsList: List<News> = emptyList(),
    val error: String? = null
)