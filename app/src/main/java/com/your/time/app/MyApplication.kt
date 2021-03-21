package com.your.time.app

import android.app.Application
import android.support.multidex.MultiDex
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.google.firebase.FirebaseApp
import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.global.DaggerApplicationComponent
import com.your.time.app.di.global.ApplicationModule
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric

class MyApplication : Application(){

    companion object {
        private lateinit var appComponent: ApplicationComponent

        fun getApplicationComponent() = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        Fabric.with(this, Crashlytics())

        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()

//        Crashlytics.log(Log.INFO, "some tag", "some message info")
//        Crashlytics.log(Log.DEBUG, "some tag", "some message debug")
//        Crashlytics.log(Log.ERROR, "some tag", "some message error")
//        Crashlytics.logException(Exception("my own exception"))
    }
}