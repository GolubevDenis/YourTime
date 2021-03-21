package com.your.time.app.di.presentation.add_task

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.presentation.add_task.mvp.AddTaskPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [AddTaskModule::class]
)
interface AddTaskComponent {

    fun getPresenter(): AddTaskPresenter
}