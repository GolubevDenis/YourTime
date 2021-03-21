package com.your.time.app.di.presentation.stats.activity

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.stats.AcitivityInteractor
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.presentation.stats.stats_activity.mvp.StatsActivityPresenter
import com.your.time.app.presentation.stats.stats_activity.mvp.StatsActivityPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class StatsActivityModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            acitivityInteractor: AcitivityInteractor,
            schedulersService: SchedulersService
    ): StatsActivityPresenter
            = StatsActivityPresenterImpl(acitivityInteractor, schedulersService)
}