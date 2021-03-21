package com.your.time.app.di.presentation.setting

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.ActivityContextModule
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.presentation.setting.mvp.SettingPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [
            ActivityContextModule::class,
            SettingModule::class
        ]
)
interface SettingComponent {

    fun getPresenter(): SettingPresenter
}