package com.rubenquadros.epicnews.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_data")
data class NewsEntity(

    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "content")
    var content: String?,

    @ColumnInfo(name = "image")
    var imageURL: String?,

    @ColumnInfo(name = "created_on")
    var createdOn: String?,

    @ColumnInfo(name = "author")
    var author: String?,

    @ColumnInfo(name = "source")
    var source: String?
)