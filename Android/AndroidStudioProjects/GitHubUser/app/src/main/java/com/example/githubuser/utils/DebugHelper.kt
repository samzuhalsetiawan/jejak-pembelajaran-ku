package com.example.githubuser.utils

import android.util.Log

object DebugHelper {
    private const val TAG = "MY_DEBUG"
    fun loggingError(unit: String, message: String? = "", throwable: Throwable? = null) {
        Log.e(TAG, "[$unit] $message", throwable)
    }
}