package com.your.time.app.di.presentation.stats.activity

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.stats.stats_activity.mvp.StatsActivityPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [StatsActivityModule::class]
)
interface StatsActivityComponent {

    fun getPresenter(): StatsActivityPresenter
    fun getTimeUtil(): TimeUtil
}