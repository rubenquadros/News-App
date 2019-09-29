package com.rubenquadros.epicnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rubenquadros.epicnews.base.BaseViewModel
import com.rubenquadros.epicnews.data.local.entity.NewsEntity
import com.rubenquadros.epicnews.data.remote.model.Article
import com.rubenquadros.epicnews.data.repository.NewsRepository
import com.rubenquadros.epicnews.utils.ApplicationConstants
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class AllNewsViewModel @Inject
constructor(private val newsRepository: NewsRepository,
            @param:Named(ApplicationConstants.SUBSCRIBER_ON) private val subscriberOn: Scheduler,
            @param:Named(ApplicationConstants.OBSERVER_ON) private val observerOn: Scheduler) : BaseViewModel() {

    val isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val response: MutableLiveData<List<Article>> = MutableLiveData()
    val error: MutableLiveData<String?> = MutableLiveData()
    private var dbResponse: MutableLiveData<List<NewsEntity>> = MutableLiveData()
    private var allNews = ArrayList<NewsEntity>()

    fun getNews() {
        this.disposable.addAll(this.newsRepository.getNews()
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe{isLoading.value = true}
            .doOnComplete{isLoading.value = false}
            .doOnError{isLoading.value = false}
            .subscribe({ resource -> getNewsResponse().postValue(resource.articles) }, {resource -> getErrorResponse().postValue(resource.message)}))
    }

    fun getNewsResponse() = response

    fun getErrorResponse() = error

    fun deleteNews() {
        this.newsRepository.deleteNews()
    }

    fun saveNews(news: List<Article>) {
        for(i in news.indices) {
            allNews.add(NewsEntity(i, news[i].title, news[i].content, news[i].urlToImage, news[i].publishedAt, news[i].author, news[i].source?.name))
        }
        this.newsRepository.saveNews(allNews)
    }

    fun initLocalData() {
        dbResponse = this.newsRepository.getNewsFromDB()
    }

    fun getNewsResponseFromDB(): LiveData<List<NewsEntity>> {
        return dbResponse
    }

}