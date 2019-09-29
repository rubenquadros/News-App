package com.rubenquadros.epicnews.di

import com.rubenquadros.epicnews.base.BaseTest
import com.rubenquadros.epicnews.di.module.ActivityModule
import com.rubenquadros.epicnews.di.module.RepositoryModule
import com.rubenquadros.epicnews.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityModule::class, ApiModuleTest::class,
            DbModuleTest::class, RepositoryModule::class,
            RxJavaModuleTest::class, ViewModelModule::class])
interface AppComponentTest {
    fun inject(baseTest: BaseTest)
}