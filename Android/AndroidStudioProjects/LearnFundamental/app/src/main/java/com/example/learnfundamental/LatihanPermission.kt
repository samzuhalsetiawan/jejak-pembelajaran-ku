package com.example.learnfundamental

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat

class LatihanPermission : AppCompatActivity() {

    private lateinit var btnRequestPermission: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_permission)

        btnRequestPermission = findViewById(R.id.btnRequestPermission)
        btnRequestPermission.setOnClickListener {
            makeRequestPermission()
            val isInternetGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
            Log.d("LatihanPermission", "Internet Granted ? : $isInternetGranted")
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
                        Log.d("LatihanPermission", "${permissions[i]} diterima")
                    } else {
                        Log.d("LatihanPermission", "${permissions[i]} ditolak")
                    }
                }
            }
        }
    }

    private fun makeRequestPermission() {
        val isWriteStorageGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val isForegroundLocationGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val isBackgroundLocationGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED

        val permissionsToRequest = mutableListOf<String>()
        if (!isWriteStorageGranted) permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!isForegroundLocationGranted) permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (!isBackgroundLocationGranted) permissionsToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        Log.d("LatihanPermission", "${permissionsToRequest.size} Permission Requested")
    }

}