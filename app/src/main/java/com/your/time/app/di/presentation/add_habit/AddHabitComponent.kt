package com.your.time.app.di.presentation.add_habit

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.presentation.add_habit.mvp.AddHabitPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [AddHabitModule::class]
)
interface AddHabitComponent {

    fun getPresenter(): AddHabitPresenter
}