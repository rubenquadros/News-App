package com.rubenquadros.epicnews.di

import android.app.Application
import androidx.room.Room
import com.rubenquadros.epicnews.data.local.dao.NewsDAO
import com.rubenquadros.epicnews.data.local.database.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModuleTest(private val application: Application) {

    @Provides
    @Singleton
    fun provideDatabase(): NewsDatabase {
        return Room.inMemoryDatabaseBuilder(application, NewsDatabase::class.java)
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNewsDAO(newsDatabase: NewsDatabase): NewsDAO {
        return newsDatabase.newsDAO()
    }
}