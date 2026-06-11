package com.putrinadya.miti.domain.usecase.news

import com.putrinadya.miti.domain.repository.NewsRepository
import javax.inject.Inject

class GetTechNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke() = repository.getTechNews()
}