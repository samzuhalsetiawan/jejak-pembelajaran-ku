package com.example.latihanbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return
        val isActive = intent.getBooleanExtra("state", false)
        when {
            isActive -> {
                Toast.makeText(context, "Air Plane Active", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(context, "Air Plane Disable", Toast.LENGTH_LONG).show()
            }
        }
    }
}