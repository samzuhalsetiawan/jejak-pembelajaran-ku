package com.example.latihannotification3.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.example.latihannotification3.App
import com.example.latihannotification3.R
import com.example.latihannotification3.data.MyMessage

class MessageNotificationBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        if (intent == null) return
        val bundle = RemoteInput.getResultsFromIntent(intent)
        val balasan = bundle.getCharSequence(App.EXTRA_REPLY_MESSAGE)

        val messagingStyle = NotificationCompat.MessagingStyle(Person.Builder().setName("Anda").build())
            .setConversationTitle("Knversation Title")

        val broadcastIntent = Intent(context, this::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val remoteInput = RemoteInput.Builder(App.EXTRA_REPLY_MESSAGE)
            .setLabel("Masukan Balasannya ... ")
            .build()

        balasan?.let {
            val person = Person.Builder().setName(null).setIcon(App.myIcon).build()
            val newMessage = NotificationCompat.MessagingStyle.Message(balasan, System.currentTimeMillis(), person)
            MyMessage.pesan.add(newMessage)
            for (message in MyMessage.pesan) {
                messagingStyle.addMessage(message)
            }
            val notification = NotificationCompat.Builder(context, App.CHANNEL_ID_IMPORTANT)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ichika_hoshino_pp))
                .setStyle(messagingStyle)
                .addAction(NotificationCompat.Action.Builder(R.drawable.ic_send, "Balas", pendingIntent)
                    .addRemoteInput(remoteInput)
                    .build()
                )
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(8, notification)
        }
    }
}