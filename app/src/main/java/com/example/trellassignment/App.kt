package com.example.trellassignment

import android.app.Application
import android.content.Context
import com.example.trellassignment.di.DaggerVideoComponent
import com.example.trellassignment.di.VideoComponent
import com.github.hiteshsondhi88.libffmpeg.FFmpeg

class App : Application() {


    init {
        instance = this

        videoComponent = DaggerVideoComponent.builder().application(this).build()
    }


    override fun onCreate() {
        super.onCreate()
        getFFmpeg()
    }

    companion object {
        private var fFmpeg: FFmpeg? = null

        private var instance: App? = null
        private lateinit var videoComponent: VideoComponent


        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getVideoDaggerComponent(): VideoComponent {
            return videoComponent
        }

        fun getFFmpeg() = if (fFmpeg == null) FFmpeg.getInstance(applicationContext()) else fFmpeg
    }


}


