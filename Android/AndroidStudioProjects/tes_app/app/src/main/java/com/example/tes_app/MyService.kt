package com.example.tes_app

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class MyService: Service() {

    val TAG = "MY_SERVICE"

    private lateinit var media: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        media = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        media.isLooping = true
        media.start()
        Log.d(TAG, "SERVICE IS RUNNING")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SERVICE IS STOP")
        media.stop()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}