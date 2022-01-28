package com.example.learnfundamental2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.example.learnfundamental2.databinding.ActivityLatihanNotificationBinding

class LatihanNotificationActivity : AppCompatActivity() {

    companion object {
        private const val NOTIF_CHANNEL_ID = "12345"
        private const val NOTIF_CHANNEL_NAME = "channel name"
        private const val NOTIF_FIRST_ID = 123
    }

    private val binding by lazy { ActivityLatihanNotificationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        //        Open App When Notif Clicked
        val intent = Intent(this, LatihanNotificationActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
            .setContentTitle("My First Notification")
            .setContentText("Ini Adalah Notification Pertamaku")
            .setSmallIcon(R.drawable.ic_home)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) // implement pending intent
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        binding.btnShowNotification.setOnClickListener {
            notificationManager.notify(NOTIF_FIRST_ID, notification)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(NOTIF_CHANNEL_ID, NOTIF_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        channel.apply {
            lightColor = Color.GREEN
            enableLights(true)
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

}