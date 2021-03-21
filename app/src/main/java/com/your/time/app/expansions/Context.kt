package com.your.time.app.expansions

import android.app.ActivityManager
import android.content.Context

fun Context.isMyServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}

fun Context.getDrawableIdByName(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}