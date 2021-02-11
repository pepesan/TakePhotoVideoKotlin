package com.example.takephotovideokotlin

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var mVideoView: VideoView? = null
    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mVideoView = findViewById(R.id.videoView) as VideoView
        imageView = findViewById(R.id.imageView) as ImageView
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_record_video) {
            dispatchTakeVideoIntent()
            return true
        }
        if (id == R.id.action_play_video) {
            playVideo()
            return true
        }
        if (id == R.id.action_take_photo) {
            takePhoto()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    val REQUEST_TAKE_PHOTO = 2
    private fun takePhoto() {
        val takeVideoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takeVideoIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_TAKE_PHOTO)
        }
    }

    val REQUEST_VIDEO_CAPTURE = 1

    private fun dispatchTakeVideoIntent() {
        val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (takeVideoIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri = intent.data
            mVideoView?.setVideoURI(videoUri)
        }
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val extras = intent.extras
            val imageBitmap = extras!!["data"] as Bitmap?
            imageView?.setImageBitmap(imageBitmap)
        }
    }

    fun playVideo() {
        mVideoView?.start()
    }
}