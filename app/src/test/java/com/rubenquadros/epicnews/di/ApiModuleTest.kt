package com.rubenquadros.epicnews.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rubenquadros.epicnews.data.remote.api.NewsApiService
import com.rubenquadros.epicnews.utils.ApplicationConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModuleTest(private val baseUrl:String) {

    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(ApplicationConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        httpClient.readTimeout(ApplicationConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        httpClient.writeTimeout(ApplicationConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}