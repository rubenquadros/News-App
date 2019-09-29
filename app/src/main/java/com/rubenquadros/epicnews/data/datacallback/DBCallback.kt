package com.rubenquadros.epicnews.data.datacallback

import com.rubenquadros.epicnews.data.local.entity.NewsEntity

interface DBCallback {

    fun onQueryExecuted(newsData: List<NewsEntity>)
}