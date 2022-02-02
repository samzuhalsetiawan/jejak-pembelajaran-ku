package com.example.latihannotification3

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.latihannotification3.databinding.ActivityMainBinding
import com.example.latihannotification3.receiver.IntentBroadcastReceiver

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val notificationManager by lazy { NotificationManagerCompat.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSendAsNotifImportant.setOnClickListener {
            val title = binding.etNotifTitle.text.toString()
            val message = binding.etNotifContentText.text.toString()

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

            notificationManager.notify(1,notification)
        }

        binding.btnSendAsNotifQuite.setOnClickListener {
            val title = binding.etNotifTitle.text.toString()
            val message = binding.etNotifContentText.text.toString()

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_LOW)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

            notificationManager.notify(2,notification)
        }


        binding.btnNotifWithAction.setOnClickListener {
            val title = binding.etNotifTitle.text.toString()
            val message = binding.etNotifContentText.text.toString()

            val activityIntent = Intent(this, MainActivity::class.java)
            val pendingActivityIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            val broadcastIntent = Intent(this, IntentBroadcastReceiver::class.java)
            broadcastIntent.putExtra(App.EXTRA_PENDING_INTENT_MESSAGE, message)
            val pendingBroadcastReceiver = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(Color.GREEN)
                .setContentIntent(pendingActivityIntent)
                .addAction(R.mipmap.ic_launcher, "Toast", pendingBroadcastReceiver)
                .build()

            notificationManager.notify(3, notification)
        }

        binding.btnNotificationInboxStyle.setOnClickListener {
            val title = binding.etNotifTitle.text.toString()
            val message = binding.etNotifContentText.text.toString()

            val activityIntent = Intent(this, MainActivity::class.java)
            val activityPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            val broadcasterIntent = Intent(this, IntentBroadcastReceiver::class.java)
            broadcasterIntent.putExtra(App.EXTRA_PENDING_INTENT_MESSAGE, "testing 2")
            val broadcastPendingIntent = PendingIntent.getBroadcast(this, 0, broadcasterIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setContentIntent(activityPendingIntent)
                .addAction(R.mipmap.ic_launcher, "toast", broadcastPendingIntent)
                .setAutoCancel(true)
                TODO("Lanjutkan")

        }

    }
}