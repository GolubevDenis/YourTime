package com.your.time.app.di.presentation.stats.usefulness

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.stats.stats_usefulness.mvp.StatsUsefulnessPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [StatsUsefulnessModule::class]
)
interface StatsUsefulnessComponent {

    fun getPresenter(): StatsUsefulnessPresenter
    fun getTimeUtil(): TimeUtil
}