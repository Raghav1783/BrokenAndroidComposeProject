package com.greedygame.brokenandroidcomposeproject.domain

import com.greedygame.brokenandroidcomposeproject.Result
import com.greedygame.brokenandroidcomposeproject.data.Article
import com.greedygame.brokenandroidcomposeproject.data.ArticleDto
import kotlinx.coroutines.flow.Flow

interface FixedRepository {
    fun getArticles(): Flow<Result<List<Article>>>
    suspend fun refreshFromRemote()
    suspend fun updateArticle(article: Article)
    fun getArticleById(id: Int):  Flow<Result<Article?>>

}