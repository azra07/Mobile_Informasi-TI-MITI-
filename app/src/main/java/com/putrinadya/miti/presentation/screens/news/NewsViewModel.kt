package com.putrinadya.miti.presentation.screens.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrinadya.miti.domain.usecase.news.GetTechNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTechNewsUseCase: GetTechNewsUseCase
) : ViewModel() {
    var uiState by mutableStateOf(NewsUiState())
        private set

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            getTechNewsUseCase().collect { result ->
                uiState = if (result.isSuccess) {
                    uiState.copy(
                        newsList = result.getOrDefault(emptyList()),
                        isLoading = false
                    )
                } else {
                    uiState.copy(
                        error = result.exceptionOrNull()?.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}