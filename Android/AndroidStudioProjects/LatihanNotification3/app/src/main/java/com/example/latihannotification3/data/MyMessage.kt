package com.example.latihannotification3.data

import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import com.example.latihannotification3.App
import com.example.latihannotification3.MainActivity

object MyMessage {
    private val persons = listOf<Person?>(
        Person.Builder().setName("Agus").build(),
        null,
        Person.Builder().setName("Budi").build()
    )

    val pesan = mutableListOf(
        NotificationCompat.MessagingStyle.Message("Bro", System.currentTimeMillis(), persons[0]),
        NotificationCompat.MessagingStyle.Message("Kenapa?", System.currentTimeMillis(), persons[1]),
        NotificationCompat.MessagingStyle.Message("Eh Nongol si tuyul", System.currentTimeMillis(), persons[2])
    )
}