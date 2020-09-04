package com.example.trellassignment.di

import android.app.Application
import com.example.trellassignment.utils.FFMpegVideoCompressor
import dagger.Module
import dagger.Provides

@Module
class FFMpegModule {

    @Provides
    fun provideFFMpeg(application: Application): FFMpegVideoCompressor {
        return FFMpegVideoCompressor(application)

    }
}