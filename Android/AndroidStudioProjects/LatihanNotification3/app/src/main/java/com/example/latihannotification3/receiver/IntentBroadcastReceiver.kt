package com.example.latihannotification3.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.latihannotification3.App

class IntentBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(App.EXTRA_PENDING_INTENT_MESSAGE) ?: ""
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}