package com.example.tesscreenrecord

import android.os.Looper

class RecordingThread : Thread() {
    lateinit var looper: Looper

    override fun run() {
        Looper.prepare()
        looper = Looper.myLooper()!!

        Looper.loop()
    }
}