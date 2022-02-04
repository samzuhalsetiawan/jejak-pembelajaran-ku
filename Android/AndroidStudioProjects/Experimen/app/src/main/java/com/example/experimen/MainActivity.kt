package com.example.experimen

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val console = object {
        fun log(message: Any?) {
            Log.e("DEBUG", message.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutinflater2 = LayoutInflater.from(this)
        val layoutinflater3 = LayoutInflater.from(this)

        console.log(layoutinflater2 == layoutinflater3)
        console.log(layoutinflater2 == layoutInflater)
        console.log("===========================================")

        val notificationManager1 = NotificationManagerCompat.from(this)
        val notificationManager2 = NotificationManagerCompat.from(this)
        val notificationManager3 = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        console.log(notificationManager1 == notificationManager2)

    }
}