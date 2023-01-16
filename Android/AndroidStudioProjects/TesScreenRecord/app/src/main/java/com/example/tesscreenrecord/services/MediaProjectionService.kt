package com.example.tesscreenrecord.services

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import com.example.tesscreenrecord.DebugHelper
import com.example.tesscreenrecord.RecordingThread
import com.example.tesscreenrecord.callback.MediaProjectionCallback
import java.io.IOException

class MediaProjectionService : Service() {
    val TAG = "MP_SERVICE"
    private var mediaProjectionManager: MediaProjectionManager =
        getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    private val recordingThread = RecordingThread()
    private lateinit var recordingThreadHandler: Handler
    private val mediaProjectionCallback = MediaProjectionCallback()

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val mediaProjectionResultCode = it.getIntExtra("MP_RC", 0)
            val mediaProjection = mediaProjectionManager.getMediaProjection(mediaProjectionResultCode, it)
            recordingThread.start()
            recordingThreadHandler = Handler(recordingThread.looper)
            mediaProjection.registerCallback(mediaProjectionCallback, recordingThreadHandler)
            recordingThreadHandler.post { doScreenRecord() }
        }
        return START_NOT_STICKY
    }

    fun doScreenRecord() {
        val isMediaRecorderReady = prepareMediaRecorder()
        if (isMediaRecorderReady) {
            btnRecord.isEnabled = false
            btnStop.isEnabled = true
            try {
                mediaRecorder.start()
            } catch (e: Exception) {
                DebugHelper.e(this, "Failed to Start", TAG)
            }
        } else {
            DebugHelper.e(this, "mediaRecorder doesn't ready", TAG)
        }
    }

    private fun prepareMediaRecorder(): Boolean {
        mediaRecorder = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> MediaRecorder(this)
            else -> MediaRecorder()
        }
        try {
            mediaRecorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setVideoSource(MediaRecorder.VideoSource.SURFACE)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                val videoUri = getVideoUri()
                videoUri?.let {
//                    val file = it.toFile()
                    val tes = externalCacheDir?.absolutePath
                    setOutputFile("$tes/tes${System.currentTimeMillis()}.mp4")
                    DebugHelper.i(this@Nyoba2, "Uri Path", TAG, it.toString())
//                    val tesUri =
//                        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/tes.mp4")
//                    DebugHelper.i(this@Nyoba2, "TES URI", TAG, tesUri.absolutePath)
                } ?: run {
                    DebugHelper.e(this@Nyoba2, "Output Uri was null", TAG)
                    setOutputFile(
                        "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/MyRec${System.currentTimeMillis()}.mp4"
                    )
                }
                setVideoSize(720, 1280)
                setVideoEncoder(MediaRecorder.VideoEncoder.H264)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setVideoEncodingBitRate(512 * 1000)
                setVideoFrameRate(30)
                val rotation = when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> this@Nyoba2.display?.rotation
                    else -> windowManager.defaultDisplay.rotation
                }
                rotation?.let {
                    val orientation = ORIENTATIONS.get(it + 90)
                    setOrientationHint(orientation)
                } ?: DebugHelper.e(this@Nyoba2, "Failed to get Orientation", TAG)
            }
            mediaRecorder.prepare()
            return true
        } catch (e: IOException) {
            DebugHelper.e(this, "Failed to prepare mediaRecorder", TAG, data = e.message)
            e.printStackTrace()
            return false
        }
    }
    fun stopRecording() {
        btnRecord.isEnabled = true
        btnStop.isEnabled = false
        try {
            mediaRecorder.stop()
            mediaRecorder.reset()
        } catch (e: Exception) {
            DebugHelper.e(this, "Failed to Stop", TAG)
        }
    }
}