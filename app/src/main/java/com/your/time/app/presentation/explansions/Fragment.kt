package com.your.time.app.presentation.explansions

import android.support.v4.app.Fragment


fun Fragment.ui(run: () -> Unit) {
    activity?.runOnUiThread {
        run.invoke()
    }
}