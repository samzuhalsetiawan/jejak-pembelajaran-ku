package com.example.learnfundamental

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LatihanPermission3 : AppCompatActivity() {

    private lateinit var btnRequestPermissionLocation: Button
    private lateinit var btnRequestPermissionCamera: Button

    companion object {
        private const val LOCATION_RC = 1
        private const val CAMERA_RC = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_permission3)

        btnRequestPermissionLocation = findViewById(R.id.btnRequestPermissionLocation)
        btnRequestPermissionLocation.setOnClickListener {
            checkForPermission(Manifest.permission.ACCESS_COARSE_LOCATION, "location", LOCATION_RC)
        }
        btnRequestPermissionCamera = findViewById(R.id.btnRequestPermissionCamera)
        btnRequestPermissionCamera.setOnClickListener {
            checkForPermission(Manifest.permission.CAMERA, "kamera", CAMERA_RC)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "$name granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "$name Rejected", Toast.LENGTH_LONG).show()
                }
            }
        }
        when (requestCode) {
            LOCATION_RC -> innerCheck("location")
            CAMERA_RC -> innerCheck("kamera")
        }
    }

    private fun checkForPermission(permission: String, name: String, requestCode: Int) {
        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "$name permission is granted", Toast.LENGTH_LONG).show()
            }
            shouldShowRequestPermissionRationale(permission) -> showRasionale(permission, name, requestCode)
            else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    private fun showRasionale(permission: String, permissionName: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Izin Dibutuhkan")
            setMessage("Aplikasi ini membutuhkan izin $permissionName untuk dapat berjalan dengan baik")
            setPositiveButton("OK") { dialog, wich ->
                ActivityCompat.requestPermissions(this@LatihanPermission3, arrayOf(permission), requestCode)
            }
        }
        builder.create().also { it.show() }
    }

}