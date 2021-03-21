package com.your.time.app.di.presentation.home

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.ActivityContextModule
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.home.mvp.HomePresenter
import com.your.time.app.presentation.home.adapters.actions.ActionsHomeRecyclerViewAdapter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [
            ActivityContextModule::class,
            HomeModule::class
        ]
)
interface HomeComponent {

    fun getPresenter(): HomePresenter
    fun getTimeUtil(): TimeUtil

    fun getFastMarkingActionsAdapter(): ActionsHomeRecyclerViewAdapter
}