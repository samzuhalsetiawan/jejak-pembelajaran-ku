package com.example.learnfundamental

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class LatihanPermission5 : AppCompatActivity() {

    companion object {
        private const val FIRST_RC = 1
        private const val TAG = "LatihanPermission5"
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            when {
                isGranted -> Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
                else -> Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_permission5)

        val btnRequestPermission5 = findViewById<Button>(R.id.btnRequestPermission5)
        btnRequestPermission5.setOnClickListener {
            checkForPermission(Manifest.permission.CAMERA)
        }

    }

    private fun checkForPermission(permission: String) {
        when {
            ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                Log.d(TAG, "Permission Already Granted")
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                Log.d(TAG, "should show request permission rationale")
                showReasonWhyINeedYourPermission(permission)
            }
            else -> {
                Log.d(TAG, "ELSE from check for permission")
                Log.d(TAG, "showRationale: ${shouldShowRequestPermissionRationale(permission)}")
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun showReasonWhyINeedYourPermission(permission: String) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Izin Dibutuhkan")
            setMessage("Tolonglah")
            setPositiveButton("Okelah") { _,_ ->
                requestPermissionLauncher.launch(permission)
            }
        }
        builder.create().also { it.show() }
    }

}