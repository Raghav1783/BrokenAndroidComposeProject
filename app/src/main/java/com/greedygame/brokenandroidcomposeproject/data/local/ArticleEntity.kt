package com.greedygame.brokenandroidcomposeproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.greedygame.brokenandroidcomposeproject.data.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val author: String?,
    val content: String?,
    val imageUrl: String?,
    val isSynced: Boolean = true
)


fun ArticleEntity.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        author = author,
        content = content,
        imageUrl = imageUrl,
        isSynced = isSynced
    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        author = author,
        content = content,
        imageUrl = imageUrl,
        isSynced = isSynced
    )
}




