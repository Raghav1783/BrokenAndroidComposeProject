package com.greedygame.brokenandroidcomposeproject.data

import com.google.gson.Gson
import com.greedygame.brokenandroidcomposeproject.data.local.ArticleEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Article(
    val id: Int,
    val title: String,
    val author: String?,
    val content: String?,
    val imageUrl: String?,
    var isSynced:Boolean = true
)

@Serializable
data class NewsResponse(
    @SerialName("status")
    val status: String,

    @SerialName("totalResults")
    val totalResults: Int,

    @SerialName("articles")
    val articles: List<ArticleDto>? = emptyList()
)


@Serializable
data class ArticleDto(
    @SerialName("author")
    val author: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("url")
    val url: String,

    @SerialName("urlToImage")
    val urlToImage: String? = null,

    @SerialName("publishedAt")
    val publishedAt: String? = null,

    @SerialName("content")
    val content: String? = null
)

fun ArticleDto.toEntity(): ArticleEntity {

    val generatedId = url.hashCode()

    return ArticleEntity(
        id = generatedId,
        title = title.orEmpty(),
        author = author,
        content = content,
        imageUrl = urlToImage
    )
}

object BrokenRepository {
    fun fetchArticlesBlocking(): List<Article> {
        val fakeJson = "[{\"identifier\":1,\"heading\":\"Hello\",\"writer\":\"Alice\"}]"
        val gson = Gson()
        val articles: Array<Article> = try {
            gson.fromJson(fakeJson, Array<Article>::class.java)
        } catch (e: Exception) {
            emptyArray()
        }
        return articles.toList()
    }

    fun updateArticle(article: Article) {
    }
}