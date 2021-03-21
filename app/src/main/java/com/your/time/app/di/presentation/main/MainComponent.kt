package com.your.time.app.di.presentation.main

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.presentation.main.mvp.MainPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [MainModule::class]
)
interface MainComponent {

    fun getPresenter(): MainPresenter
}