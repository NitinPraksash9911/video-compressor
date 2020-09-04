package com.example.trellassignment.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.trellassignment.App
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import java.io.File


const val VIDEO_DIR = "CompressedVideos"
const val EXTN_MP4 = ".mp4"
const val FILE_NAME = "trell"
const val TAG = "FFMpegVideoCompressor"

class FFMpegVideoCompressor(private val context: Context) {

    private var status = NONE
    private var errorMessage: String? = "Compression Failed!"

    fun startCompressing(inputPath: String?, bitrate: String, listener: CompressionListener?) {
        if (inputPath == null || inputPath.isEmpty()) {
            status = NONE
            listener?.compressionFinished(NONE, false, null)
            return
        }
        val outputPath = appDir
        val commandParams = arrayOfNulls<String>(26)
        commandParams[0] = "-y"
        commandParams[1] = "-i"
        commandParams[2] = inputPath
        commandParams[3] = "-s"
        commandParams[4] = "320x320"
        commandParams[5] = "-r"
        commandParams[6] = "25"
        commandParams[7] = "-c:v"
        commandParams[8] = "libx264"
        commandParams[9] = "-preset"
        commandParams[10] = "ultrafast"
        commandParams[11] = "-c:a"
        commandParams[12] = "copy"
        commandParams[13] = "-me_method"
        commandParams[14] = "zero"
        commandParams[15] = "-tune"
        commandParams[16] = "fastdecode"
        commandParams[17] = "-tune"
        commandParams[18] = "zerolatency"
        commandParams[19] = "-strict"
        commandParams[20] = "-2"
        commandParams[21] = "-b:v"  //bitrate key for video
        commandParams[22] = "${bitrate}k" // bitrate in kb
        commandParams[23] = "-pix_fmt"
        commandParams[24] = "yuv420p"
        commandParams[25] = outputPath


        compressVideo(commandParams, outputPath, listener)
    }

    private val appDir: String
        get() {
            val mydir: File = context.getDir(
                VIDEO_DIR,
                AppCompatActivity.MODE_PRIVATE
            )

            if (!mydir.exists()) {
                mydir.mkdirs() //Creating an internal dir
            }
            val file = File(mydir, "$FILE_NAME$EXTN_MP4")
            return file.path
        }

    private fun compressVideo(
        command: Array<String?>,
        outputFilePath: String,
        listener: CompressionListener?
    ) {
        try {

            val ffmpegObject = App.getFFmpeg()

            ffmpegObject!!.loadBinary(object : FFmpegLoadBinaryResponseHandler {
                override fun onStart() {

                }

                override fun onFinish() {
                }

                override fun onFailure() {
                }

                override fun onSuccess() {
                }

            })

            ffmpegObject.execute(command, object : FFmpegExecuteResponseHandler {
                override fun onSuccess(message: String) {
                    status = SUCCESS
                }

                override fun onProgress(message: String) {
                    status = RUNNING
                    Log.e(TAG, message)
                    listener!!.onProgress(message)

                }

                override fun onFailure(message: String) {
                    status = FAILED
                    Log.e(TAG, message)
                    listener?.onFailure("Error : $message")
                }

                override fun onStart() {
                    status = START
                    listener?.onStart()

                }

                override fun onFinish() {
                    Log.e(TAG, "finish")
                    listener?.compressionFinished(status, true, outputFilePath)
                }
            })


        } catch (e: FFmpegCommandAlreadyRunningException) {
            status = FAILED
            errorMessage = e.message
            listener?.onFailure("Error : " + e.message)
        }
    }

    interface CompressionListener {
        fun compressionFinished(status: Int, isVideo: Boolean, fileOutputPath: String?)
        fun onFailure(message: String?)
        fun onProgress(progress: String)
        fun onStart()

    }


    companion object {
        const val SUCCESS = 1
        const val FAILED = 2
        const val NONE = 3
        const val RUNNING = 4
        const val START = 4

    }


}