package com.example.learnfundamental2.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplanemodeBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isActive = intent?.getBooleanExtra("state", false) ?: return
        context?.run {
            when {
                isActive -> Toast.makeText(this, "Mode Pesawat Aktif", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Mode Pesawat Mati", Toast.LENGTH_SHORT).show()
            }
        }
    }
}