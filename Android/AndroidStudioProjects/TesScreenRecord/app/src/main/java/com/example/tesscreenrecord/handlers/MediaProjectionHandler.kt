package com.example.tesscreenrecord.handlers

import android.os.Handler
import android.os.Message
import com.example.tesscreenrecord.RecordingThread

class MediaProjectionHandler(private val recordingThread: RecordingThread) : Handler(recordingThread.looper) {
    override fun handleMessage(msg: Message) {
        msg.callback.run()
    }
}