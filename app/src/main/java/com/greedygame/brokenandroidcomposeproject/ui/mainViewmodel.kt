package com.greedygame.brokenandroidcomposeproject.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greedygame.brokenandroidcomposeproject.Result
import com.greedygame.brokenandroidcomposeproject.data.Article

import com.greedygame.brokenandroidcomposeproject.domain.FixedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class mainViewmodel@Inject constructor(
    private val repository: FixedRepository): ViewModel() {

    private val _uiState = MutableStateFlow(ArticlesUiState())
    val uiState: StateFlow<ArticlesUiState> = _uiState

    private val _articleDetailUiState = MutableStateFlow(ArticleDetailUiState())
    val articleDetailUiState: StateFlow<ArticleDetailUiState> = _articleDetailUiState.asStateFlow()
    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            repository.getArticles().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            errorMessage = null
                        )
                    }

                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            articles = result.data,
                            errorMessage = null
                        )

                    }

                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )

                    }
                }
            }
        }
    }

    fun updateArticle(article: Article) {
        viewModelScope.launch {
            Log.d("mainViewmodel", "updateArticle: $article")
            repository.updateArticle(article)
        }
    }

    fun getArticleDetail(articleId: Int) {
        Log.d("mainViewmodel", "getArticleDetail: $articleId")
        viewModelScope.launch {
            repository.getArticleById(articleId).collect { result ->
                _articleDetailUiState.value = when (result) {
                    is Result.Loading -> ArticleDetailUiState(isLoading = true)
                    is Result.Success -> ArticleDetailUiState(isLoading = false,article = result.data)
                    is Result.Error -> ArticleDetailUiState(isLoading = false,errorMessage = result.message)
                }
            }
        }
    }




}
data class ArticlesUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val errorMessage: String? = null
)

data class ArticleDetailUiState(
    val isLoading: Boolean = false,
    val article: Article? = null,
    val errorMessage: String? = null
)