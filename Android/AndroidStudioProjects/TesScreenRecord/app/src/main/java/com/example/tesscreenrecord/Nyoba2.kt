package com.example.tesscreenrecord

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.example.tesscreenrecord.callback.MediaProjectionCallback
import java.io.File
import java.io.IOException
import java.net.URI

class Nyoba2 : AppCompatActivity() {

    val TAG = "NYOBA_2"

//    private val mediaProjectionManager by lazy {
//        getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
//    }
    private lateinit var mediaProjectionManager: MediaProjectionManager
    private var mediaProjection: MediaProjection? = null
    private val metrics by lazy { resources.displayMetrics }
    private val density by lazy { metrics.densityDpi }
    private lateinit var screenCaptureResultLauncher: ActivityResultLauncher<Intent>
    private val screenRecordRequestPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val isAllPermissionGranted = it.all { permission -> permission.value }
            if (isAllPermissionGranted) prepareForRecordingApp()
        }
    }
    private val btnRecord by lazy { findViewById<Button>(R.id.btnRecord) }
    private val btnStop by lazy { findViewById<Button>(R.id.btnStop) }
    private lateinit var mediaRecorder: MediaRecorder
    private val ORIENTATIONS = SparseIntArray()
    private val mediaProjectionCallback = MediaProjectionCallback { stopRecording() }
    private val recordingThread = RecordingThread()
    private lateinit var recordingThreadHandler: Handler

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nyoba2)

        mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        screenCaptureResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
//            if (it.resultCode == Activity.RESULT_OK) {
//                doScreenRecord(it.data)
//            } else {
//                DebugHelper.w(this, "Permission Denied", TAG)
//            }
            activityResult.data?.let { intent ->
                intent.putExtra("MP_RC", activityResult.resultCode)
                mediaProjection = mediaProjectionManager.getMediaProjection(activityResult.resultCode, intent)
                mediaProjection?.let {
                    recordingThread.start()
                    recordingThread.looper?.let { looper ->
                        recordingThreadHandler = Handler(looper)
                        it.registerCallback(mediaProjectionCallback, recordingThreadHandler)
                        recordingThreadHandler.post { doScreenRecord() }
                    } ?: DebugHelper.w(this, "Looper Null", TAG)
                } ?: DebugHelper.w(this, "MediaProjection Null", TAG)
            } ?: run {
                if (activityResult.resultCode != Activity.RESULT_OK) DebugHelper.w(this, "Permission Denied", TAG)
                DebugHelper.w(this, "MediaProjection Intent Null", TAG)
            }
        }

        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);

        val isAllPermissionGranted = checkAndRequestForPermission()
        if (isAllPermissionGranted) {
            prepareForRecordingApp()
        }
    }

    private fun prepareForRecordingApp() {
        btnRecord.isEnabled = true
        btnRecord.setOnClickListener {
            screenCaptureResultLauncher.launch(mediaProjectionManager.createScreenCaptureIntent())
        }
        btnStop.setOnClickListener {
            stopRecording()
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

    private fun checkAndRequestForPermission(): Boolean {
        val isWriteExternalStorageGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val isRecordAudioGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

        val permissionToRequest = mutableListOf<String>()

        if (!isWriteExternalStorageGranted) permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!isRecordAudioGranted) permissionToRequest.add(Manifest.permission.RECORD_AUDIO)

        return permissionToRequest.isEmpty().also { if (!it) screenRecordRequestPermissionLauncher.launch(permissionToRequest.toTypedArray()) }

    }

    private fun getVideoUri(): Uri? {
        val uri = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            else -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }
        val videoName = "MyRec${System.currentTimeMillis()}.mp4"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, videoName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "DCIM/MyScreenRec/")
        return contentResolver.insert(uri, contentValues)
    }

    override fun onDestroy() {
        mediaProjection?.unregisterCallback(mediaProjectionCallback)
        mediaProjection = null
        super.onDestroy()
    }
}