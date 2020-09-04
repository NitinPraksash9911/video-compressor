package com.example.trellassignment.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trellassignment.App
import com.example.trellassignment.R
import com.example.trellassignment.databinding.ActivityCompressVideoBinding
import com.example.trellassignment.utils.*
import com.example.trellassignment.viewmodel.VideoViewModel
import com.example.trellassignment.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class CompressVideoActivity : AppCompatActivity() {

    private var inputPath: String? = null

    lateinit var binding: ActivityCompressVideoBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var videoViewModel: VideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compress_video)

        App.getVideoDaggerComponent().inject(this)

        inputPath = intent.getStringExtra(FILE_PATH_KEY)



        initialize()

    }

    private fun initialize() {

        binding.videoPath = inputPath

        videoViewModel = ViewModelProvider(this, providerFactory).get(VideoViewModel::class.java)

        binding.startCompressBtn.setOnClickListener {

            val bitrate = binding.bitrateET.text.toString()

            if (bitrate.isEmpty()) {

                binding.bitrateTextInputLayout.error = "Invalid Bitrate"

            } else {

                binding.mainLayout.hideKeyboard(this)

                binding.bitrateTextInputLayout.error = ""

                inputPath?.let { it1 -> initCompression(it1, bitrate) }
            }

        }
    }

    private fun initCompression(path: String, bitrate: String) {

        videoViewModel.doCompressVideo(path, bitrate).observe(this, Observer {

            when (it.status) {

                Result.Status.LOADING -> {

                    this.showLoader()
                }
                Result.Status.SUCCESS -> {

                    dismissLoader()

                    val intent =
                        Intent(this@CompressVideoActivity, ShowResultActivity::class.java)

                    intent.putExtra(FILE_PATH_KEY, it.data)

                    startActivity(intent)
                }
                Result.Status.ERROR -> {

                    dismissLoader()

                    it.message?.snackMessage(Color.RED, binding.mainLayout)

                }

            }

        })

    }


}