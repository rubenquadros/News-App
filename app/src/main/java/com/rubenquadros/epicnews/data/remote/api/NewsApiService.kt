package com.rubenquadros.epicnews.data.remote.api

import com.rubenquadros.epicnews.data.remote.model.TopNewsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsApiService {

    @GET("top-headlines?country=in&apiKey=api_key")
    fun getTopNews(): Observable<TopNewsResponse>
}
