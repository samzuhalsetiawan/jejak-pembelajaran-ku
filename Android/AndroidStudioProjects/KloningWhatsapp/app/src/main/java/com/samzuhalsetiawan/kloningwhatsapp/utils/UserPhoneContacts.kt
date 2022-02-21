package com.samzuhalsetiawan.kloningwhatsapp.utils

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.samzuhalsetiawan.kloningwhatsapp.model.UserObject

object UserPhoneContacts {

    private val contacts = mutableListOf<UserObject>()

    private fun updateContacts(activity: AppCompatActivity) {
        contacts.clear()
        activity.apply {
            contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ),
                null,
                null,
                null
            )?.apply {
                val columNameIndex =
                    getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)
                val columNumberIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                while (moveToNext()) {
                    val phoneName = getString(columNameIndex)
                    val phoneNumber = prunePhoneNumber(getString(columNumberIndex))
                    contacts.add(UserObject(phoneName, phoneNumber))
                }
                close()
            }
        }
    }

    fun prunePhoneNumber(phoneNumber: String): String {
        return phoneNumber
            .replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")
    }

    fun getReadAccessToUserContacts(
        activity: AppCompatActivity,
        callback: (isGranted: Boolean) -> Unit
    ) {
        val launcher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                when {
                    isGranted -> {
                        updateContacts(activity)
                        callback(true)
                    }
                    else -> {
                        Toast.makeText(activity, "PermissionDenied", Toast.LENGTH_SHORT).show()
                        callback(false)
                    }
                }
            }
        when {
            activity.applicationContext.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> {
                updateContacts(activity)
                callback(true)
            }
            activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                val alertDialog = AlertDialog.Builder(activity)
                    .setTitle("Izinkan akses membaca kontak")
                    .setMessage("Applikasi kami memerlukan izin akses ke kontak anda agar dapat menampilkan user lain yang terdapat di kontak anda")
                    .setPositiveButton("Izinkan") { _: DialogInterface, _: Int ->
                        launcher.launch(Manifest.permission.READ_CONTACTS)
                    }
                    .setNegativeButton("Tolak") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .create()
                alertDialog.show()
            }
            else -> launcher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    fun findContact(phoneNumber: String): UserObject? {
        return contacts.find {
            Log.e("TAG", "COMPARING (${it.phoneNumber}) WITH ($phoneNumber)")
            it.phoneNumber == phoneNumber
        }
    }

}