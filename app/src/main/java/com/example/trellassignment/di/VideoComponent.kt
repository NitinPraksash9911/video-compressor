package com.example.trellassignment.di

import android.app.Application
import com.example.trellassignment.ui.CompressVideoActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ViewModelModule::class, FFMpegModule::class])
interface VideoComponent {

    fun inject(compressVideoActivity: CompressVideoActivity)

    @Component.Builder
    interface Builder {

        fun build(): VideoComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}