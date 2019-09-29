package com.rubenquadros.epicnews.data.remote.api

import com.rubenquadros.epicnews.data.remote.model.TopNewsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsApiService {

    @GET("top-headlines?country=in&apiKey=5a80ff621106489ea959c0f8b3740404")
    fun getTopNews(): Observable<TopNewsResponse>
}