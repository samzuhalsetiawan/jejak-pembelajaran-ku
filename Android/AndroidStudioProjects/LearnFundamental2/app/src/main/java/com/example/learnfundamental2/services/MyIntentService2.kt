package com.example.learnfundamental2.services

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService2: IntentService("MyIntentService2") {

    companion object {
        var isRunning = false
    }

    override fun onHandleIntent(intent: Intent?) {
        println("=== Service start ======== isRunning: $isRunning")
        try {
            while (isRunning) {
                Log.d("MyIntentService2", "My Service is Running...")
                Thread.sleep(1000)
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}