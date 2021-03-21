package com.your.time.app.presentation.explansions

import android.os.Build
import android.support.annotation.StyleRes
import android.widget.TextView

@Suppress("DEPRECATION")
fun TextView.setTextStyle(@StyleRes styleId: Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) setTextAppearance(styleId)
    else setTextAppearance(context, styleId)
}