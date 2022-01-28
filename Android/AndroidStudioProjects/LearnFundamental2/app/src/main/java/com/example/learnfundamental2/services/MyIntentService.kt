package com.example.learnfundamental2.services

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService("MyIntentService") {

    init {
        instance = this
    }

    companion object {
        var isRunning = false

        private lateinit var instance: MyIntentService

        fun stopService() {
            isRunning = false
            instance.stopSelf()
        }

    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            isRunning = true
            while (isRunning) {
                Log.d("MyIntentService", "Service is Running...")
                Thread.sleep(1000)
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}