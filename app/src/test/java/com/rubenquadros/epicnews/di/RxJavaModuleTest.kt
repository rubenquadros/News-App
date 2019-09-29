package com.rubenquadros.epicnews.di

import com.rubenquadros.epicnews.utils.ApplicationConstants
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class RxJavaModuleTest {

    @Provides
    @Named(ApplicationConstants.SUBSCRIBER_ON)
    @Singleton
    fun provideSubsriberOn(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(ApplicationConstants.OBSERVER_ON)
    @Singleton
    fun provideObserverOn(): Scheduler = AndroidSchedulers.mainThread()
}