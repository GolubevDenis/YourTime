package com.your.time.app.presentation.explansions

import android.app.Activity
import android.graphics.Point


fun Activity.getWidthDisplay(): Int{
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}

fun Activity.getHeightDisplay(): Int{
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.y
}

fun Activity.ui(run: () -> Unit){
    runOnUiThread {
        run.invoke()
    }
}
