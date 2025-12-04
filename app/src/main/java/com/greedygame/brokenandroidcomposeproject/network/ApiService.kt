package com.greedygame.brokenandroidcomposeproject.network

import com.greedygame.brokenandroidcomposeproject.data.Article
import com.greedygame.brokenandroidcomposeproject.data.ArticleDto
import com.greedygame.brokenandroidcomposeproject.data.NewsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiService {
    @GET("/v2/everything?q=android&apiKey=43263b41c76b45c7a70ca9d16f2a89c4")
    suspend fun getArticles(): Response<NewsResponse>
}

//object ApiClient {
//    val api: ApiService = Retrofit.Builder()
//        .baseUrl("https://newsapi.org")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(ApiService::class.java)
//}