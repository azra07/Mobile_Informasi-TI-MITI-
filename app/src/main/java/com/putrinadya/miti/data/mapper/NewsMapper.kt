package com.putrinadya.miti.data.mapper

import com.putrinadya.miti.data.local.entity.NewsEntity
import com.putrinadya.miti.domain.model.News

fun NewsEntity.toDomain() = News(
    title,
    description,
    url,
    imageUrl,
    source,
    publishedAt
)

fun News.toEntity() = NewsEntity(
    url,
    title,
    description,
    imageUrl,
    source,
    publishedAt
)