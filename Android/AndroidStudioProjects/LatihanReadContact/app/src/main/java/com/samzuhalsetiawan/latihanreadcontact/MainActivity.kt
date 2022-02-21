package com.samzuhalsetiawan.latihanreadcontact

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.samzuhalsetiawan.latihanreadcontact.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted ->
        when {
            isGranted -> {
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestForPermission(Manifest.permission.READ_CONTACTS)

    }

    private fun requestForPermission(readContacts: String) {
        when {
            applicationContext.checkSelfPermission(readContacts) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show()
            }
            shouldShowRequestPermissionRationale(readContacts) -> {
                Toast.makeText(this, "Show Rationale", Toast.LENGTH_SHORT).show()
            }
            else -> {
                launcher.launch(readContacts)
            }
        }
    }
}