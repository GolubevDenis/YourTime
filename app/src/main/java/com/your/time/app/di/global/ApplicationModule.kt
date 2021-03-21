package com.your.time.app.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(
        context: Context
) {
    private val appContext: Context = context.applicationContext

    @ApplicationContext
    @Singleton
    @Provides
    fun provideApplicationContext() = appContext

}