package com.example.trellassignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trellassignment.utils.FFMpegVideoCompressor
import com.example.trellassignment.utils.Result
import javax.inject.Inject

class VideoCompressRepo @Inject constructor(private var mVideoCompressor: FFMpegVideoCompressor) {


    var liveData = MutableLiveData<Result<String>>()


    fun startCompression(path: String, bitrate: String): LiveData<Result<String>> {

        mVideoCompressor.startCompressing(
            path, bitrate,
            object : FFMpegVideoCompressor.CompressionListener {

                override fun onStart() {
                    liveData.value = Result.loading()
                }

                override fun compressionFinished(
                    status: Int,
                    isVideo: Boolean,
                    fileOutputPath: String?
                ) {
                    liveData.value = fileOutputPath?.let { Result.success(it) }
                }

                override fun onFailure(message: String?) {
                    liveData.value = message?.let { Result.error(it) }

                }

                override fun onProgress(progress: String) {
                }


            })

        return liveData
    }


}