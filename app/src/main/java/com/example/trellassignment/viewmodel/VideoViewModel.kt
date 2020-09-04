package com.example.trellassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.trellassignment.repository.VideoCompressRepo
import com.example.trellassignment.utils.Result
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val videoCompressRepo: VideoCompressRepo) :
    ViewModel() {


    fun doCompressVideo(path: String, bitrate: String): LiveData<Result<String>> {

        return videoCompressRepo.startCompression(path, bitrate)

    }

}


