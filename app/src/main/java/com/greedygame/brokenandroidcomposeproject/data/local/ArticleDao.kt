package com.greedygame.brokenandroidcomposeproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY id DESC")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles  WHERE issynced = 1 ")
    suspend fun deleteAllArticles()

    @Update
    suspend fun updateArticle(article: ArticleEntity)

    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    fun getArticleById(id: Int): Flow<ArticleEntity?>
}
