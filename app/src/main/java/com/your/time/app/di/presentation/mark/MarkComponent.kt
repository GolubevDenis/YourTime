package com.your.time.app.di.presentation.mark

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.ActivityContextModule
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.presentation.mark.mvp.MarkPresenter
import com.your.time.app.presentation.mark.adapter.MarkTimePeriodsAdapter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [
            ActivityContextModule::class,
            MarkModule::class
        ]
)
interface MarkComponent {

    fun getPresenter(): MarkPresenter
    fun getAdapter(): MarkTimePeriodsAdapter
}