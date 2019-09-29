package com.rubenquadros.epicnews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rubenquadros.epicnews.data.local.dao.NewsDAO
import com.rubenquadros.epicnews.data.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDAO(): NewsDAO

}