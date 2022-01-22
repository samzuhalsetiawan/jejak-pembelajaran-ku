package com.example.learnfundamental

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LatihanPermission2 : AppCompatActivity() {

    private lateinit var btnRequestPermission2: Button

    companion object {
        const val TAG = "LatihanPermission2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_permission2)

        btnRequestPermission2 = findViewById(R.id.btnRequestPermission2)
        btnRequestPermission2.setOnClickListener {

//            START

            makeRequestPermission()

//            END

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                for (i in grantResults.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "${permissions[i]} Granted")
                    } else {
                        Log.d(TAG, "${permissions[i]} Denied")
                    }
                }
            }
        }
    }

    private fun makeRequestPermission() {
        val isCameraGranted =
            checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val isWriteStorageGranted =
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val isForegroundLocationGranted =
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val isBackgroundLocationGranted =
            checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED

        val permissionsToRequest = mutableListOf<String>()
        if (!isCameraGranted) permissionsToRequest.add(Manifest.permission.CAMERA)
        if (!isWriteStorageGranted) permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!isForegroundLocationGranted) permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (!isBackgroundLocationGranted) permissionsToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        requestPermissions(permissionsToRequest.toTypedArray(), 0)
    }

}