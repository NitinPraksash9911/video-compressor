package com.example.trellassignment.ui.adapter

import android.widget.VideoView
import androidx.databinding.BindingAdapter

/**
 * used to set video path
 * */
@BindingAdapter("setVideoPath")
fun setVideoPath(videoView: VideoView, path: String?) {
    videoView.setVideoPath(path)
    val mediaController = android.widget.MediaController(videoView.context)
    mediaController.setAnchorView(videoView)
    videoView.setMediaController(mediaController)
    videoView.start()
}