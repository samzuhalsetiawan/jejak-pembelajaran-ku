package com.example.latihannotification2

import android.app.Notification
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.latihannotification2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val notificationManager by lazy { NotificationManagerCompat.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSendNotification1.setOnClickListener {
            val title = binding.etTitleNotification.text.toString()
            val description = binding.etDescriptionNotification.text.toString()
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return@setOnClickListener
            val notification = Notification.Builder(this, App.CHANNEL_ID_1)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_one)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .build()
            notificationManager.notify(1, notification)
        }
        binding.btnSendNotification2.setOnClickListener {
            val title = binding.etTitleNotification.text.toString()
            val description = binding.etDescriptionNotification.text.toString()
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return@setOnClickListener
            val notification = Notification.Builder(this, App.CHANNEL_ID_2)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_two)
                .setCategory(Notification.CATEGORY_STATUS)
                .build()
            notificationManager.notify(2, notification)
        }
        binding.btnNotificationCompat.setOnClickListener {
            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("title")
                .setContentText("Descripsi")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()
            notificationManager.notify(3, notification)
        }

    }
}