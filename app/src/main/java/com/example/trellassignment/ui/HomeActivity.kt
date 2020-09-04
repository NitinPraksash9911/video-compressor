package com.example.trellassignment.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.trellassignment.R
import com.example.trellassignment.databinding.ActivityHomeBinding


const val FILE_PATH_KEY = "trellFilePath"
const val FILE_REQUEST_CODE = 212

class HomeActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        activityMainBinding.selectVideoBtn.setOnClickListener {
            requestPermission()
        }
    }

    private fun selectVideo() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        )
        intent.type = "video/*"
        startActivityForResult(intent, FILE_REQUEST_CODE)
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 111
            )
        } else {
            selectVideo()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectVideo()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {

            if (requestCode == FILE_REQUEST_CODE) {
                val path = data?.data?.lastPathSegment

                val intent = Intent(this, CompressVideoActivity::class.java)

                intent.putExtra(FILE_PATH_KEY, path)

                startActivity(intent)

            }
        }
    }


}