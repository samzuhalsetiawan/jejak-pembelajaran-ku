package com.example.screen_recorder

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.SparseIntArray
import android.view.Surface
import android.view.View
import android.widget.Toast
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1000
    private val REQUEST_PERMISSION = 1001
    private lateinit var  mediaProjectionManager: MediaProjectionManager
    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null
    private lateinit var mediaProjectionCallback: MediaProjectionCallback

    private var mScreenDensity: Int? = null
    private val DISPLAY_WIDTH = 720
    private val DISPLAY_HEIGHT = 1280

    private var mediaRecorder: MediaRecorder? = null
    private lateinit var toogleBtn: FloatingActionButton

    private var isChecked = false

    private lateinit var videoView: VideoView
    private var videoUri: String = ""
    private val ORIENTATIONS = SparseIntArray()

    init {
        ORIENTATIONS.append(Surface.ROTATION_0, 90)
        ORIENTATIONS.append(Surface.ROTATION_90, 0)
        ORIENTATIONS.append(Surface.ROTATION_180, 270)
        ORIENTATIONS.append(Surface.ROTATION_270, 180)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        mScreenDensity = metrics.densityDpi
        mediaRecorder = MediaRecorder()
        mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        videoView = findViewById(R.id.videoView)
        toogleBtn = findViewById(R.id.btnSwitchRecord)

        toogleBtn.setOnClickListener {
            if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
            ){
                isChecked = false
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                    ),
                    REQUEST_PERMISSION
                )
            } else {
                toggleScreenSharing(toogleBtn)
            }
        }

    }

    private fun toggleScreenSharing(toogleBtn: FloatingActionButton?) {
        if (!isChecked){
            initRecorder()
            recordScreen()
            isChecked = true
            toogleBtn?.setImageResource(R.drawable.ic_baseline_stop_24)
        } else {
            try {
                mediaRecorder!!.stop()
                mediaRecorder!!.reset()
                stopRecordingScreen()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            videoView.visibility = View.VISIBLE
            videoView.setVideoURI(Uri.parse(videoUri))
            videoView.start()
            isChecked = false
            toogleBtn?.setImageResource(R.drawable.ic_start_record)
        }
    }

    private fun stopRecordingScreen() {
        if (virtualDisplay == null) return
        virtualDisplay!!.release()
        destroyMediaProjection()
    }

    private fun destroyMediaProjection() {
        if (mediaProjection != null) {
            mediaProjection!!.unregisterCallback(mediaProjectionCallback)
            mediaProjection!!.stop()
            mediaProjection = null
        }
    }

    private fun recordScreen() {
        if (mediaProjection == null) {
            startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_CODE
            )
        }
        virtualDisplay = createVirtualDisplay()
        try {
            mediaRecorder!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createVirtualDisplay(): VirtualDisplay? {
        return mediaProjection?.createVirtualDisplay(
            "MainActivity",
            DISPLAY_WIDTH,
            DISPLAY_HEIGHT,
            mScreenDensity!!,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            mediaRecorder!!.surface,
            null,
            null
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != REQUEST_CODE) {
            Toast.makeText(this, "unk Error", Toast.LENGTH_LONG).show()
            return
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
            isChecked = false
            return
        }
        mediaProjectionCallback = MediaProjectionCallback(
            mediaRecorder!!, mediaProjection
        )
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data!!)
        mediaProjection!!.registerCallback(mediaProjectionCallback, null)
        virtualDisplay = createVirtualDisplay()
        try {
            mediaRecorder!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initRecorder() {
        try {
            var recordingFile = ("ScreenREC${System.currentTimeMillis()}.mp4")
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

            val newpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val folder = File(newpath, "MyScreenREC/")

            if (!folder.exists()) {
                folder.mkdir()
            }

            val file1 = File(folder, recordingFile)
            videoUri = file1.absolutePath

            mediaRecorder!!.setOutputFile(videoUri)
            mediaRecorder!!.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
            mediaRecorder!!.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder!!.setAudioEncodingBitRate(512*1000)
            mediaRecorder!!.setVideoFrameRate(30)

            var rotaion = windowManager.defaultDisplay.rotation
            var orientation = ORIENTATIONS.get(rotaion + 90)

            mediaRecorder!!.setOrientationHint(orientation)
            mediaRecorder!!.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class MediaProjectionCallback(
        var mediaRecord: MediaRecorder,
        var mediaProjection: MediaProjection?
    ): MediaProjection.Callback() {
        override fun onStop() {
            if (isChecked) {
                isChecked = false
                mediaRecord.stop()
                mediaRecord.reset()
            }
            mediaProjection = null
            stopRecordingScreen()
            super.onStop()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE -> {
                if (
                    grantResults.size > 0 &&
                    grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    toggleScreenSharing(toogleBtn)
                } else {
                    isChecked = false
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO,
                        ),
                        REQUEST_PERMISSION
                    )
                }
            }
        }
    }

}