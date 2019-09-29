package com.rubenquadros.epicnews.data.repository

import androidx.lifecycle.MutableLiveData
import com.rubenquadros.epicnews.data.datacallback.DBCallback
import com.rubenquadros.epicnews.data.local.dao.NewsDAO
import com.rubenquadros.epicnews.data.local.entity.NewsEntity
import com.rubenquadros.epicnews.data.remote.api.NewsApiService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NewsRepository(private val newsDAO: NewsDAO, private val newsApiService: NewsApiService) {

    private var newsData: List<NewsEntity> = ArrayList()
    private var news: MutableLiveData<List<NewsEntity>> = MutableLiveData()

    fun getNews() = this.newsApiService.getTopNews()

    fun deleteNews() {
        doAsync {
            newsDAO.deleteAll()
        }
    }

    fun saveNews(news: ArrayList<NewsEntity>) {
        doAsync {
            newsDAO.insertAll(news)
        }
    }

    fun getNewsFromDB(): MutableLiveData<List<NewsEntity>> {
        setNewsFromDB(object : DBCallback {
            override fun onQueryExecuted(newsData: List<NewsEntity>) {
                news.value = newsData
            }
        })
        return news
    }

    private fun setNewsFromDB(dbCallback: DBCallback){
        doAsync {
            newsData = newsDAO.getAllNews()
            uiThread { dbCallback.onQueryExecuted(newsData) }
        }
    }
}