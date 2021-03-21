package com.your.time.app.di.presentation

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ActivityContextModule(
        private val context: Context
) {

    @ActivityContext
    @PresentationScope
    @Provides
    fun provideContext() = context

}