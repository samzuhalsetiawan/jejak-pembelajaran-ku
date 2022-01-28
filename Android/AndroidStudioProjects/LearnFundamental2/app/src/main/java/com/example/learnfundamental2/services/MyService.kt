package com.example.learnfundamental2.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    companion object {
        private const val TAG = "MyService"
    }

    override fun onBind(intent: Intent?): IBinder? = null

    init {
        Log.d(TAG, "Service is Running...")
        Log.d(TAG, Thread.currentThread().name)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread({
            val dataString = intent?.getStringExtra("EXTRA_DATA")

            dataString?.let {
                Log.d(TAG, dataString)
                Log.d(TAG, Thread.currentThread().name)
            }
        }, "MyThread").start()

        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "Service is Stoped...")
        Log.d(TAG, Thread.currentThread().name)
        super.onDestroy()
    }

}