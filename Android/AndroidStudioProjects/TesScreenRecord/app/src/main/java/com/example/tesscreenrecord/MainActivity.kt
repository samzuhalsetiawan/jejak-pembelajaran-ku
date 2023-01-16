package com.example.tesscreenrecord

import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var recorder: MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recorder = MediaRecorder().apply {
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            val fileName = "myrecord_${System.currentTimeMillis()}.mp4"
            val newPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val folder = File(newPath, "MyScreenRec/")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val file = File(folder, fileName)
            val videoUri = file.absolutePath
            Log.d("MainActivity", videoUri)
            setOutputFile(videoUri)
            setVideoSize(720,1280)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setVideoEncodingBitRate(512*1000)
            setVideoFrameRate(30)
        }

        try {
            recorder.prepare()
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "Failed To Prepare", Toast.LENGTH_LONG).show()
        } finally {
            Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_LONG).show()
        }

        val btnRecord = findViewById<FloatingActionButton>(R.id.btnRecord)
        val btnStop = findViewById<FloatingActionButton>(R.id.btnStop)

        btnRecord.setOnClickListener {
            recorder.start()
        }
        btnStop.setOnClickListener {
            recorder.stop()
        }

    }
}