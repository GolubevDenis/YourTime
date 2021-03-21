package com.your.time.app.di.presentation.uploading_actions

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.presentation.uploading_actions.mvp.UploadingActionsPresenter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [
            UploadingActionsModule::class
        ]
)
interface UploadingActionsComponent {

    fun getPresenter(): UploadingActionsPresenter
}