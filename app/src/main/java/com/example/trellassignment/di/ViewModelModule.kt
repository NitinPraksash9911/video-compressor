package com.example.trellassignment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trellassignment.viewmodel.VideoViewModel
import com.example.trellassignment.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    abstract fun bindWebViewModel(myViewModel: VideoViewModel): ViewModel
}