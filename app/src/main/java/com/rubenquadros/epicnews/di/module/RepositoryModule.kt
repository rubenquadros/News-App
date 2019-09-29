package com.rubenquadros.epicnews.di.module

import com.rubenquadros.epicnews.data.local.dao.NewsDAO
import com.rubenquadros.epicnews.data.remote.api.NewsApiService
import com.rubenquadros.epicnews.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(newsApiService: NewsApiService, newsDAO: NewsDAO): NewsRepository {
        return NewsRepository(newsDAO, newsApiService)
    }
}