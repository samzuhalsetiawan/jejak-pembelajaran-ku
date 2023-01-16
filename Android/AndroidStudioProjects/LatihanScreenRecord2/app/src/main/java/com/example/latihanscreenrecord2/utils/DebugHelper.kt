package com.example.latihanscreenrecord2.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

class DebugHelper {
    enum class Level {
        DEBUG, ERROR, WARNING, INFO
    }

    companion object {

        private fun debug(context: Context, message: String, level: Level, tag: String = "DEBUG_DEFAULT", data: Any?) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            when (level) {
                Level.DEBUG -> Log.d(tag, message)
                Level.ERROR -> Log.e(tag, message)
                Level.WARNING -> Log.w(tag, message)
                Level.INFO -> Log.i(tag, message)
            }
            data?.let {
                println(it)
            }
        }
        fun e(context: Context, message: String, tag: String = "DEBUG_DEFAULT", data: Any? = null) {
            debug(context, message, Level.ERROR, tag, data)
        }
        fun d(context: Context, message: String, tag: String = "DEBUG_DEFAULT", data: Any? = null) {
            debug(context, message, Level.DEBUG, tag, data)
        }
        fun w(context: Context, message: String, tag: String = "DEBUG_DEFAULT", data: Any? = null) {
            debug(context, message, Level.WARNING, tag, data)
        }
        fun i(context: Context, message: String, tag: String = "DEBUG_DEFAULT", data: Any? = null) {
            debug(context, message, Level.INFO, tag, data)
        }
    }
}