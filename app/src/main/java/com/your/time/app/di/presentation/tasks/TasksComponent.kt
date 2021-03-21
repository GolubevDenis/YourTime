package com.your.time.app.di.presentation.tasks

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.tasks.mvp.TasksPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [TasksModule::class]
)
interface TasksComponent {

    fun getPresenter(): TasksPresenter
    fun getTimeUtil(): TimeUtil
}