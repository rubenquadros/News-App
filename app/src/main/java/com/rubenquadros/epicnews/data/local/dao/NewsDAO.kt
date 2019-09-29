package com.rubenquadros.epicnews.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rubenquadros.epicnews.data.local.entity.NewsEntity

@Dao
interface NewsDAO {

    @Insert
    fun insertAll(data: ArrayList<NewsEntity>)

    @Query("DELETE FROM news_data")
    fun deleteAll()

    @Query("SELECT * FROM news_data")
    fun getAllNews(): List<NewsEntity>
}