package com.example.githubuser.utils

import android.util.Log

object DebugHelper {
    private const val TAG = "MY_DEBUG"
    fun loggingError(unit: String, message: String? = "", throwable: Throwable? = null) {
        Log.e(TAG, "[$unit] $message", throwable)
    }

    fun loggingDebug(unit: String, message: String? = "") {
        Log.d(TAG, "[$unit] $message")
    }
}