package com.your.time.app.presentation.explansions

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import org.solovyev.android.views.llm.LinearLayoutManager

fun RecyclerView.attachFloatButton(button: FloatingActionButton){
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (dy > 0 || dy < 0 && button.isShown)
                button.hide()
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                button.show()
            }
            super.onScrollStateChanged(recyclerView, newState)
        }
    })
}

fun RecyclerView.setUnscrolableLinearLayoutManager(){
    layoutManager = object : LinearLayoutManager(context){
        override fun canScrollVertically(): Boolean {
            return false
        }
    }
}