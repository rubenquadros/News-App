package com.rubenquadros.epicnews.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rubenquadros.epicnews.factory.ViewModelFactory
import com.rubenquadros.epicnews.viewmodel.AllNewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AllNewsViewModel::class)
    internal abstract fun allNewsViewModel(viewModel: AllNewsViewModel): ViewModel
}