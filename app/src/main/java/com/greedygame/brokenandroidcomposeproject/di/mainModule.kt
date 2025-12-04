package com.example.pratilii.di

import android.content.Context
import androidx.room.Room
import com.greedygame.brokenandroidcomposeproject.data.FixedRepositoryImpl
import com.greedygame.brokenandroidcomposeproject.data.local.ArticleDao
import com.greedygame.brokenandroidcomposeproject.db.AppDatabase
import com.greedygame.brokenandroidcomposeproject.domain.FixedRepository
import com.greedygame.brokenandroidcomposeproject.network.ApiService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object mainModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "articles_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: AppDatabase): ArticleDao {
        return database.articleDao()
    }

    @Provides
    @Singleton
    fun provideFixedRepository(
        apiService: ApiService,
        articleDao: ArticleDao
    ): FixedRepository {
        return FixedRepositoryImpl(apiService, articleDao)
    }
}