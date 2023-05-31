package com.example.storyapp.presentation.camera_app

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View.OnClickListener
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityCameraBinding
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.dialogs.DialogFailedCapturePhoto
import com.example.storyapp.presentation.dialogs.DialogCameraPermissionNeeded
import java.util.Locale

class CameraActivity : AppCompatActivity(R.layout.activity_camera), DialogCameraPermissionNeeded.DialogCameraPermissionNeededListener {

    private val binding by viewBindings(ActivityCameraBinding::bind)
    private lateinit var cameraController: LifecycleCameraController
    private val dialogPermissionNeeded by lazy { DialogCameraPermissionNeeded().apply { dialogCameraPermissionNeededListener = this@CameraActivity } }
    private val dialogFailedCapturePhoto by lazy { DialogFailedCapturePhoto() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isRequiredPermissionsGranted = REQUIRED_PERMISSIONS.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
        if (!isRequiredPermissionsGranted) return Unit.also {
            dialogPermissionNeeded.show(supportFragmentManager, "dialog_permission_needed")
        }
        setupCameraController()
    }

    private fun setupCameraController() {
        cameraController = LifecycleCameraController(this)
        cameraController.bindToLifecycle(this)
        binding.pvCameraPreview.controller = cameraController
        binding.btnFlipCamera.setOnClickListener {
            cameraController.cameraSelector = when (cameraController.cameraSelector) {
                CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
                CameraSelector.DEFAULT_FRONT_CAMERA -> CameraSelector.DEFAULT_BACK_CAMERA
                else -> CameraSelector.DEFAULT_BACK_CAMERA
            }
        }
        binding.btnCapture.setOnClickListener(onButtonCaptureClicked)
    }

    private val onButtonCaptureClicked = OnClickListener {
        val sdf = SimpleDateFormat("yyyy.MM.dd'_'HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis())
        val name = "story_app_$sdf"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Story_App")
            }
        }
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            .build()
        cameraController.takePicture(outputOptions, ContextCompat.getMainExecutor(this), onImageSavedCallback)
    }

    private val onImageSavedCallback = object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            if (outputFileResults.savedUri == null) {
                dialogFailedCapturePhoto.show(supportFragmentManager, "dialog_failed_save_photo")
                return
            } else {
                val intent = Intent().apply { putExtra(EXTRA_PHOTO_URI, outputFileResults.savedUri.toString()) }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        override fun onError(exception: ImageCaptureException) {
            dialogFailedCapturePhoto.show(supportFragmentManager, "dialog_failed_save_photo")
        }
    }

    override fun onPositiveButtonClicked() { finish() }

    companion object {
        val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
        const val EXTRA_PHOTO_URI = "com.example.storyapp.EXTRA_PHOTO_URI"
    }
}