package com.your.time.app.di.presentation.habits

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.habits.mvp.HabitsPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [HabitsModule::class]
)
interface HabitsComponent {

    fun getPresenter(): HabitsPresenter
    fun getTimeUtil(): TimeUtil
}