package com.example.trellassignment.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.trellassignment.R
import com.example.trellassignment.databinding.ActivityShowResultBinding
import com.example.trellassignment.utils.snackMessage

class ShowResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_result)


        if (intent.hasExtra(FILE_PATH_KEY)) {

            binding.videoPath = intent.getStringExtra(FILE_PATH_KEY)
        } else {
            getString(R.string.invalid_path).snackMessage(Color.RED, binding.mainLayout)
        }

    }
}