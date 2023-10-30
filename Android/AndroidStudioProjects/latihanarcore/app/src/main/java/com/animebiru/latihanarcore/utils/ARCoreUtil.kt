package com.animebiru.latihanarcore.utils

import android.content.Context
import com.google.ar.core.ArCoreApk

object ARCoreUtil {
    interface CheckForARCoreSupportCallback {
        fun onDeviceARCoreSupported()
        fun onDeviceARCoreUnsupported()
    }

    fun checkForARCoreSupport(applicationContext: Context, callback: CheckForARCoreSupportCallback) {
        ArCoreApk.getInstance().checkAvailabilityAsync(applicationContext) { availability ->
            if (availability.isSupported) {
                callback.onDeviceARCoreSupported()
            } else {
                callback.onDeviceARCoreUnsupported()
            }
        }
    }
}