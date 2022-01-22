package com.example.learnfundamental

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LatihanPermission4 : AppCompatActivity() {

    private lateinit var btnRequestPermission4: Button

    companion object {
        private const val FIRST_RC = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_permission4)

        btnRequestPermission4 = findViewById(R.id.btnRequestPermission4)
        btnRequestPermission4.setOnClickListener {
            checkForPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            FIRST_RC -> {
                for (i in grantResults.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "acc ${permissions[i]}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "den ${permissions[i]}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun checkForPermission() {
        val isLocationGranted =
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        val isCameraGranted =
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

        val permissionsToRequest = mutableListOf<String>()
        if (!isLocationGranted) permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (!isCameraGranted) permissionsToRequest.add(Manifest.permission.CAMERA)

        if (permissionsToRequest.isEmpty()) return
        when {
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showDialogForExplainingWhyINeedYourPermission(permissionsToRequest.toTypedArray())
            }
            else -> requestPermissions(permissionsToRequest.toTypedArray(), FIRST_RC)
        }
    }

    private fun showDialogForExplainingWhyINeedYourPermission(permissions: Array<String>) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Izin Diperlukan")
            setMessage("Mohon diizinkan yah bang, ini demi kesatuan bangsa dan negara, kami ga nyolong data anda kok, aman, sans aja")
            setPositiveButton("Okelah") { _, _ ->
                ActivityCompat.requestPermissions(this@LatihanPermission4, permissions, FIRST_RC)
            }
        }
        builder.create().also { it.show() }
    }

}