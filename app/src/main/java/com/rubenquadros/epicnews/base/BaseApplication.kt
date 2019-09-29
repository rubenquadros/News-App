package com.rubenquadros.epicnews.base

import android.app.Application
import com.rubenquadros.epicnews.di.component.AppComponent
import com.rubenquadros.epicnews.di.component.DaggerAppComponent
import com.rubenquadros.epicnews.di.module.ApiModule
import com.rubenquadros.epicnews.di.module.DbModule
import com.rubenquadros.epicnews.di.module.RepositoryModule
import com.rubenquadros.epicnews.di.module.RxJavaModule
import com.rubenquadros.epicnews.utils.ApplicationConstants

open class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        this.appComponent = initDagger()
    }

    protected open fun initDagger():AppComponent =
        DaggerAppComponent.builder()
            .apiModule(ApiModule(ApplicationConstants.BASE_URL, this))
            .dbModule(DbModule(this))
            .repositoryModule(RepositoryModule())
            .rxJavaModule(RxJavaModule())
            .build()
}