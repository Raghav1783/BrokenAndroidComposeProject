package com.greedygame.brokenandroidcomposeproject.data

import android.util.Log
import com.greedygame.brokenandroidcomposeproject.Result
import com.greedygame.brokenandroidcomposeproject.data.local.ArticleDao
import com.greedygame.brokenandroidcomposeproject.data.local.toDomain
import com.greedygame.brokenandroidcomposeproject.data.local.toEntity
import com.greedygame.brokenandroidcomposeproject.domain.FixedRepository
import com.greedygame.brokenandroidcomposeproject.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FixedRepositoryImpl@Inject constructor(
    private val apiService: ApiService,
    private val articleDao: ArticleDao
) : FixedRepository{
    override fun getArticles(): Flow<Result<List<Article>>> = flow {
        emit(Result.Loading)
        articleDao.getArticles().collect { localList ->
            Log.d("getArticles", "getArticles Repo: $localList")
            emit(Result.Success(localList.map { it.toDomain() }))
        }
    }.onStart {
        refreshFromRemote()
    }.flowOn(Dispatchers.IO)

    override suspend fun refreshFromRemote() = withContext(Dispatchers.IO){
        try {
            Log.d("refreshFromRemote", "refreshFromRemote Repo: $apiService")
            val response = apiService.getArticles()
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                articleDao.deleteAllArticles()
                articleDao.insertArticles(articles.map { it.toEntity() })
            }
        } catch (e: Exception) {

        }
    }



    override suspend fun updateArticle(article: Article) = withContext(Dispatchers.IO) {
        Log.d("updateArticle", "updateArticle Repo: $article")
        articleDao.updateArticle(article.toEntity())
    }

    override fun getArticleById(id: Int): Flow<Result<Article?>> =
        flow {
            emit(Result.Loading)
            articleDao.getArticleById(id).collect { entity ->
                emit(Result.Success(entity?.toDomain()))
            }
        }.catch { e ->
            emit(Result.Error(e.message ?: "Something went wrong"))
        }.flowOn(Dispatchers.IO)

}