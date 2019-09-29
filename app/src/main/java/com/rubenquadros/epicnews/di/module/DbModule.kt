package com.rubenquadros.epicnews.di.module

import android.app.Application
import androidx.room.Room
import com.rubenquadros.epicnews.data.local.dao.NewsDAO
import com.rubenquadros.epicnews.data.local.database.NewsDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule(private val application: Application) {

    @Provides
    fun provideDatabase(): NewsDatabase {
        return Room.databaseBuilder(application, NewsDatabase::class.java, "News.db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNewsDAO(newsDatabase: NewsDatabase): NewsDAO {
        return newsDatabase.newsDAO()
    }
}