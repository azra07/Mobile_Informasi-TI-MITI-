package com.putrinadya.miti.domain.repository

import com.putrinadya.miti.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTechNews(): Flow<Result<List<News>>>
}