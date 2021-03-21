package com.your.time.app.presentation.explansions

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import io.reactivex.Observable

fun BottomNavigationView.itemClicks(): Observable<MenuItem> {
    return Observable.create {
        val emitter = it
        this.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener{
            emitter.onNext(it)
                true
        })
    }
}