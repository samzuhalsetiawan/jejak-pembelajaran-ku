package com.animebiru.latihanarcore

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.animebiru.latihanarcore.utils.ARCoreUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException

class MainActivity : AppCompatActivity(), ARCoreUtil.CheckForARCoreSupportCallback {

    private lateinit var cameraPermissionRequestLauncher: ActivityResultLauncher<String>
    private var isUserRequestInstallARCore = true
    private var arCoreSession: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraPermissionRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(), ::onCameraPermissionRequestResult)

        ARCoreUtil.checkForARCoreSupport(applicationContext, this)

    }

    override fun onDeviceARCoreSupported() {

    }

    override fun onDeviceARCoreUnsupported() {
        Toast.makeText(this, "Device not support for AR", Toast.LENGTH_LONG).show()
    }

    private fun onCameraPermissionRequestResult(isGranted: Boolean) {
        when {
            isGranted -> {

            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Permission Needed")
                    .setMessage("We need camera access to use AR")
                    .setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
                        cameraPermissionRequestLauncher.launch(Manifest.permission.CAMERA)
                    }
            }
            else -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Permission Needed")
                    .setMessage("We need camera access to use AR")
                    .setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.data = Uri.fromParts("package", packageName, null)
                        startActivity(intent)
                    }
            }
        }
    }

    override fun onResume() {
        super.onResume()

//        Check For Camera Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            cameraPermissionRequestLauncher.launch(Manifest.permission.CAMERA)
            return
        }

        try {

            if (arCoreSession == null) {
                when (ArCoreApk.getInstance().requestInstall(this, isUserRequestInstallARCore)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        arCoreSession = Session(this)
                    }
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        isUserRequestInstallARCore = false
                        return
                    }
                }
            }

        } catch (err: UnavailableUserDeclinedInstallationException) {
            Toast.makeText(this, "ARCore needed in order to use this App", Toast.LENGTH_LONG).show()

        } catch (err: Throwable) {
            err.printStackTrace()
            Toast.makeText(this, "Error Occurred, See Log", Toast.LENGTH_LONG).show()
        }

    }
}