package com.putrinadya.miti.data.repository

import com.putrinadya.miti.data.local.dao.MitiDao
import com.putrinadya.miti.data.mapper.toDomain
import com.putrinadya.miti.data.mapper.toEntity
import com.putrinadya.miti.data.remote.NewsApiService
import com.putrinadya.miti.domain.model.News
import com.putrinadya.miti.domain.repository.NewsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val dao: MitiDao
) : NewsRepository {
    override fun getTechNews() = flow{
        val localNews = dao.getAllNews().firstOrNull()?.map { it.toDomain() }
        if (!localNews.isNullOrEmpty()) {
            emit(Result.success(localNews))
        }

        try {
            val response = apiService.getTechNews()
            val newsList = response.articles.map { dto ->
                News(
                    title = dto.title ?: "No Title",
                    description = dto.description ?: "No Description",
                    url = dto.url ?: "",
                    imageUrl = dto.urlToImage ?: "",
                    source = dto.source?.name ?: "Unknown",
                    publishedAt = dto.publishedAt ?: ""
                )
            }
            dao.clearNews()
            dao.insertNews(newsList.map { it.toEntity() })
            emit(Result.success(newsList))
        } catch (e: Exception) {
            if (localNews.isNullOrEmpty()) {
                emit(Result.failure(e))
            }
        }
    }
}