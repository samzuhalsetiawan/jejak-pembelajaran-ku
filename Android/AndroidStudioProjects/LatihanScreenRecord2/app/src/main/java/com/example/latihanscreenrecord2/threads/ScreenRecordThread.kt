package com.example.latihanscreenrecord2.threads

import android.os.Handler
import android.os.Looper

class ScreenRecordThread : Thread() {
    lateinit var looper: Looper
    lateinit var handler: Handler

    override fun run() {
        Looper.prepare()
        looper = Looper.myLooper()!!
        handler = Handler(looper)
        Looper.loop()
    }
}