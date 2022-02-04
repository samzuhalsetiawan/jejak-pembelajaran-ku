package com.example.latihannotification3

import android.app.Notification
import android.app.PendingIntent
import android.app.RemoteInput
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.session.MediaSession
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import com.example.latihannotification3.data.MyMessage
import com.example.latihannotification3.databinding.ActivityMainBinding
import com.example.latihannotification3.receiver.IntentBroadcastReceiver
import com.example.latihannotification3.receiver.MessageNotificationBroadcastReceiver

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val notificationManager by lazy { NotificationManagerCompat.from(this) }
    private lateinit var mediaSession: MediaSessionCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mediaSession = MediaSessionCompat(this, "MediaSession")
        binding.etNotifTitle.setText("AHH")
        binding.etNotifContentText.setText("ICHIKA CHAN")

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

        binding.btnBigTextStyle.setOnClickListener {
            val title = binding.etNotifTitle.text.toString()
            val message = binding.etNotifContentText.text.toString()

            val activityIntent = Intent(this, MainActivity::class.java)
            val activityPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            val broadcasterIntent = Intent(this, IntentBroadcastReceiver::class.java)
            broadcasterIntent.putExtra(App.EXTRA_PENDING_INTENT_MESSAGE, "testing 2")
            val broadcastPendingIntent = PendingIntent.getBroadcast(this, 0, broadcasterIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            val ichikaBitmap = BitmapFactory.decodeResource(resources, R.drawable.ichika_hoshino_pp)

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(ichikaBitmap)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(resources.getString(R.string.big_dummy_text))
                    .setBigContentTitle("Big Content Title")
                    .setSummaryText("Ichika chan")
                )
                .setSmallIcon(R.drawable.ic_notif_android)
                .setContentIntent(activityPendingIntent)
                .addAction(R.mipmap.ic_launcher, "toast", broadcastPendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build()

            notificationManager.notify(4, notification)

        }

        binding.btnInboxStyle.setOnClickListener {
            val title = binding.etNotifTitle.text.toString()
            val message = binding.etNotifContentText.text.toString()
            val ichikaBitmap = BitmapFactory.decodeResource(resources, R.drawable.ichika_hoshino_pp)

            val activityIntent = Intent(this, MainActivity::class.java)
            val taskStackBuilder = TaskStackBuilder.create(this)
//            val activityPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            val pendingIntent = taskStackBuilder
                .addNextIntentWithParentStack(activityIntent)
                .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(ichikaBitmap)
                .setStyle(NotificationCompat.InboxStyle()
                    .addLine("Ichika chan")
                    .addLine("Wangy")
                    .addLine("Wangy")
                    .addLine("Wangy")
                    .addLine("Wangy")
                    .addLine("Wangy")
                    .addLine("Wangy")
                    .setBigContentTitle("Ichika Chaaannnnn")
                    .setSummaryText("Wangyyy")
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(5, notification)
        }

        binding.btnBigPictureStyle.setOnClickListener {
            val title = binding.etNotifTitle.text.toString()
            val message = binding.etNotifContentText.text.toString()

            val picture = BitmapFactory.decodeResource(resources, R.drawable.ichika_hoshino_pp)

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(picture)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(picture)
                    .bigLargeIcon(picture)
                    .setBigContentTitle("ICHIKA CHAN").also {
                        when {
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                                it.showBigPictureWhenCollapsed(true)
                            }
                        }
                    }
                    .setSummaryText("wangy bet")
                )
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(Color.BLUE)
                .setSubText("WANGYYY")
                .build()

            notificationManager.notify(6, notification)
        }

        binding.btnMediaStyle.setOnClickListener {
            val title = binding.etNotifTitle.text
            val message = binding.etNotifContentText.text

            val artwork = BitmapFactory.decodeResource(resources, R.drawable.ichika_hoshino_pp)

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(artwork)
                .addAction(R.drawable.ic_dislike, "Dislike", null)
                .addAction(R.drawable.ic_previous, "Previous", null)
                .addAction(R.drawable.ic_play, "Play", null)
                .addAction(R.drawable.ic_next, "Next", null)
                .addAction(R.drawable.ic_like, "Like", null)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1,2,3)
                    .setMediaSession(mediaSession.sessionToken)
                )
                .setSubText("Sub Text")
                .build()

            notificationManager.notify(7, notification)
        }

        val artwork = BitmapFactory.decodeResource(resources, R.drawable.ichika_hoshino_pp)

        binding.btnMessagingStyle.setOnClickListener {

            val messagingStyle = NotificationCompat.MessagingStyle(Person.Builder().setName("Sam").build())
                .setConversationTitle("Koversation Title")

            for (message in MyMessage.pesan) {
                messagingStyle.addMessage(message)
            }

            val broadcastIntent = Intent(this, MessageNotificationBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_IMMUTABLE or 0)
            val remoteInput = androidx.core.app.RemoteInput.Builder(App.EXTRA_REPLY_MESSAGE)
                .setLabel("Masukan Balasan ...")
                .build()

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setLargeIcon(artwork)
                .setStyle(messagingStyle)
                .setSubText("Sub Teks")
                .addAction(NotificationCompat.Action.Builder(R.drawable.ic_send, "Balas", pendingIntent)
                    .addRemoteInput(remoteInput)
                    .build()
                )
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(Color.GREEN)
                .build()

            notificationManager.notify(8, notification)
        }

        binding.btnProgress.setOnClickListener {
            val progressMax = 100
            val notificationBuilder = NotificationCompat.Builder(this, App.CHANNEL_ID_IMPORTANT)
                .setSmallIcon(R.drawable.ic_notif_android)
                .setContentTitle("Download")
                .setContentText("Downloading in Progress")
                .setColor(Color.GREEN)
                .setSubText("download anime")
                .setProgress(progressMax, 0, false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(artwork)
                .setOnlyAlertOnce(true)
                .setOngoing(true)

            notificationManager.notify(9, notificationBuilder.build())

            Thread {
                for (progress in 0..progressMax step 10) {
                    notificationBuilder.setProgress(progressMax, progress, false)
                    notificationManager.notify(9, notificationBuilder.build())
                    Thread.sleep(2000)
                }
                notificationBuilder.setProgress(0, 0, false)
                notificationBuilder.setContentText("Download Finished")
                notificationBuilder.setOngoing(false)
                notificationManager.notify(9, notificationBuilder.build())
            }.start()

        }

    }
}