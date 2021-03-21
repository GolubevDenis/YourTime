package com.your.time.app.presentation.explansions

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.NestedScrollView


fun NestedScrollView.attachFloatButton(fab: FloatingActionButton){
    setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        if (scrollY > oldScrollY) {
            fab.hide()
        } else {
            fab.show()
        }
    })
}