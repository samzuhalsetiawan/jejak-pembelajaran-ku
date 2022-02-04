package com.example.latihannotification3

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.graphics.drawable.IconCompat

class App : Application() {

    companion object {
        const val CHANNEL_ID_IMPORTANT = "importantNotificationChannel"
        const val CHANNEL_NAME_IMPORTANT = "Notifikasi Penting"
        const val CHANNEL_ID_LOW = "lowNotificationChannel"
        const val CHANNEL_NAME_LOW = "Notifikasi Kecil"
        const val EXTRA_PENDING_INTENT_MESSAGE = "EXTRA_PENDING_INTENT_MESSAGE"
        const val EXTRA_REPLY_MESSAGE = "EXTRA_REPLY_MESSAGE"
        var myIcon : IconCompat? = null
    }

    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onCreate() {
        super.onCreate()
        myIcon = IconCompat.createWithBitmap(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ichika))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(CHANNEL_ID_IMPORTANT, CHANNEL_NAME_IMPORTANT, NotificationManager.IMPORTANCE_HIGH)
            createNotificationChannel(CHANNEL_ID_LOW, CHANNEL_NAME_LOW, NotificationManager.IMPORTANCE_LOW)
        }
    }

    private fun createNotificationChannel(channelId: String, channelName: String, important: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, important).apply {
                description = "Menampilkan notifikasi notifikasi penting"
                vibrationPattern = longArrayOf(1000L,1000L,1000L,1000L,1000L)
                enableVibration(true)
                lightColor = Color.RED
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

}