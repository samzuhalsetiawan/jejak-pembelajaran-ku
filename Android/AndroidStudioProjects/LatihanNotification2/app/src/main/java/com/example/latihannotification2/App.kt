package com.example.latihannotification2

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class App : Application() {

    companion object {
        const val CHANNEL_ID_1 = "Channel1"
        const val CHANNEL_NAME_1 = "Channel 1"
        const val CHANNEL_ID_2 = "Channel2"
        const val CHANNEL_NAME_2 = "Channel 2"

    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel1 = NotificationChannel(CHANNEL_ID_1, CHANNEL_NAME_1, NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Ini Channel 1"
                enableVibration(true)
            }
            val channel2 = NotificationChannel(CHANNEL_ID_2, CHANNEL_NAME_2, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Ini Channel 2"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }

}