package com.putrinadya.miti.data.remote

import com.putrinadya.miti.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTechNews(
        @Query("category") category: String = "technology",
        @Query("language") language: String = "en",
        @Query("apiKey") apiKey: String = com.putrinadya.miti.BuildConfig.NEWS_API_KEY
    ): NewsResponse
}