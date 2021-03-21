package com.your.time.app.di.presentation.stats.usefulness

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.data.usefulness.UsefulnessInteractor
import com.your.time.app.domain.interactors.stats.AcitivityInteractor
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.presentation.stats.stats_usefulness.mvp.StatsUsefulnessPresenter
import com.your.time.app.presentation.stats.stats_usefulness.mvp.StatsUsefulnessPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class StatsUsefulnessModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            usefulnessInteractor: UsefulnessInteractor,
            schedulersService: SchedulersService
    ): StatsUsefulnessPresenter
            = StatsUsefulnessPresenterImpl(usefulnessInteractor, schedulersService)
}