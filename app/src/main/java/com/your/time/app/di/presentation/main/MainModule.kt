package com.your.time.app.di.presentation.main

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.services.NotificatorService
import com.your.time.app.domain.services.PropertiesService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.main.mvp.MainPresenter
import com.your.time.app.presentation.main.mvp.MainPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            timeUtil: TimeUtil,
            propertiesService: PropertiesService,
            notificatorService: NotificatorService
    ): MainPresenter
            = MainPresenterImpl(timeUtil, propertiesService, notificatorService)
}