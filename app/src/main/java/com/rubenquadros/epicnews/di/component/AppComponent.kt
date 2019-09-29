package com.rubenquadros.epicnews.di.component

import com.rubenquadros.epicnews.base.BaseActivity
import com.rubenquadros.epicnews.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityModule::class, ApiModule::class,
    DbModule::class, RepositoryModule::class,
    RxJavaModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
}