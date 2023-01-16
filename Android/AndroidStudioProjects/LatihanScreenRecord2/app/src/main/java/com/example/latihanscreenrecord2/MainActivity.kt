package com.example.latihanscreenrecord2

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.*
import android.util.DisplayMetrics
import android.util.SparseIntArray
import android.view.Surface
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ConfigurationHelper
import com.example.latihanscreenrecord2.databinding.ActivityMainBinding
import com.example.latihanscreenrecord2.services.ScreenRecordService
import com.example.latihanscreenrecord2.threads.ScreenRecordThread
import com.example.latihanscreenrecord2.utils.DebugHelper
import java.io.File

class MainActivity : AppCompatActivity() {
    val TAG = "MAIN_ACTIVITY"

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var screenCaptureLauncher: ActivityResultLauncher<Intent>
    private lateinit var mediaProjectionManager: MediaProjectionManager
    private lateinit var mediaProjection: MediaProjection
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var virtualDisplay: VirtualDisplay
    private var DISPLAY_WIDTH = 720
    private var DISPLAY_HEIGHT = 1280
    private val ORIENTATIONS = SparseIntArray()
    private var isRecording = false
    private val screenDpi by lazy { ConfigurationHelper.getDensityDpi(resources) }
//    private val screenRecordThread = ScreenRecordThread()
    private val mediaProjectionCallback = object : MediaProjection.Callback() {
        override fun onStop() {
            stopScreenRecord()
            super.onStop()
        }
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRecord.isEnabled = false
        binding.btnStop.isEnabled = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            DISPLAY_WIDTH = metrics.bounds.width()
            DISPLAY_HEIGHT = metrics.bounds.height()
        } else {
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            DISPLAY_WIDTH = metrics.widthPixels
            DISPLAY_HEIGHT = metrics.heightPixels
        }


        mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        screenCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            result.data?.let { screenCaptureIntent ->
                startService(Intent(this, ScreenRecordService::class.java))
                val handler = Handler()
                handler.postDelayed({
                    mediaProjectionManager.getMediaProjection(result.resultCode, screenCaptureIntent)?.let { mediaProjection ->
                        this.mediaProjection = mediaProjection
                        mediaProjection.registerCallback(mediaProjectionCallback, null)
                        DebugHelper.i(this@MainActivity, "Handler Executed")
                        doScreenRecord()
                    }
                }, 1000)
            }
        }
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.values.all { it }) {
                prepareScreenRecordApp()
            }
        }

        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);

        if (checkForPermissions()) prepareScreenRecordApp()

    }

    private fun prepareScreenRecordApp() {
        binding.btnRecord.isEnabled = true
        val intent = mediaProjectionManager.createScreenCaptureIntent()
        binding.btnRecord.setOnClickListener {
            binding.btnRecord.isEnabled = false
            binding.btnStop.isEnabled = true
            screenCaptureLauncher.launch(intent)
        }
        binding.btnStop.setOnClickListener {
            binding.btnRecord.isEnabled = true
            binding.btnStop.isEnabled = false
            stopScreenRecord()
        }
    }

    private fun doScreenRecord() {
        DebugHelper.i(this@MainActivity, "doScreenRecord Called")
        if (isRecording) return
        val isMediaRecorderReady = prepareMediaRecorder()
        if (isMediaRecorderReady) {
            isRecording = true
            createVirtualDisplay()
            try {
                DebugHelper.i(this@MainActivity, "Screen Recording...")
                mediaRecorder.start()
            } catch (e: Exception) {
                DebugHelper.e(this, "MediaRecorder Failed to Start")
                e.printStackTrace()
            }
        }
    }

    private fun createVirtualDisplay(): VirtualDisplay? {
        return mediaProjection.createVirtualDisplay(
            "MainActivityVD",
            DISPLAY_WIDTH,
            DISPLAY_HEIGHT,
            screenDpi,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            mediaRecorder.surface,
            null,
            null
        )
    }

    private fun prepareMediaRecorder(): Boolean {
//        mediaRecorder = when {
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> MediaRecorder(this)
//            else -> MediaRecorder()
//        }
        mediaRecorder = MediaRecorder()
        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            val videoUri = "${externalCacheDir?.absolutePath}/tes${java.lang.System.currentTimeMillis()}.mp4"
            DebugHelper.i(this@MainActivity, "Video URI", data = videoUri)
            setOutputFile(videoUri)
//            val recordingFile = ("ScreenREC${System.currentTimeMillis()}.mp4")
//            val newPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//            val folder = File(newPath, "MyScreenREC/")
//            if (!folder.exists()) {
//                folder.mkdir()
//            }
//            val file1 = File(folder, recordingFile)
//            val videoUri = file1.absolutePath
//            DebugHelper.i(this@MainActivity, "Video URI", data = videoUri)
//            mediaRecorder.setOutputFile(videoUri)
            setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
            setVideoEncoder(MediaRecorder.VideoEncoder.H263)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setVideoEncodingBitRate(512 * 1000)
            setVideoFrameRate(30)
            val rotation = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> this@MainActivity.display?.rotation
                else -> windowManager.defaultDisplay.rotation
            }
            rotation?.let {
                val orientation = ORIENTATIONS.get(it + 90)
                setOrientationHint(orientation)
            }
            return try {
                prepare()
                true
            } catch (e: Exception) {
                DebugHelper.e(this@MainActivity, "Failed to prepare mediaRecorder", data = e.message)
                e.printStackTrace()
                false
            }
        }
    }

    private fun stopScreenRecord() {
        DebugHelper.i(this@MainActivity, "stopScreenRecord Called")
        if (!isRecording) return
        try {
            DebugHelper.i(this@MainActivity, "Stop Recording...")
            isRecording = false
            mediaRecorder.stop()
            mediaRecorder.reset()
            virtualDisplay.release()
            mediaProjection.unregisterCallback(mediaProjectionCallback)
            mediaProjection.stop()
        } catch (e: Exception) {
            DebugHelper.e(this@MainActivity, "Failed to stop", data = e.message)
            e.printStackTrace()
        }
    }

    private fun checkForPermissions() : Boolean {
        val isRecordAudioGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        val isWriteExternalGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val isCameraGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val isForegroundServiceGranted = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED
            else -> true
        }

        val permissionToRequests = arrayListOf<String>()
        if (!isCameraGranted) permissionToRequests.add(Manifest.permission.CAMERA)
        if (!isRecordAudioGranted) permissionToRequests.add(Manifest.permission.RECORD_AUDIO)
        if (!isWriteExternalGranted) permissionToRequests.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !isForegroundServiceGranted) permissionToRequests.add(Manifest.permission.FOREGROUND_SERVICE)

        return permissionToRequests.isEmpty().also { if (!it) requestPermissionLauncher.launch(permissionToRequests.toTypedArray()) }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopScreenRecord()
        stopService(Intent(this, ScreenRecordService::class.java))
    }
}