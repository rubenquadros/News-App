package com.rubenquadros.epicnews.di.module

import com.rubenquadros.epicnews.utils.ApplicationConstants
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class RxJavaModule {

    @Provides
    @Named(ApplicationConstants.SUBSCRIBER_ON)
    fun provideSubscriberOn(): Scheduler = Schedulers.io()

    @Provides
    @Named(ApplicationConstants.OBSERVER_ON)
    fun provideObserverOn(): Scheduler = Schedulers.io()
}