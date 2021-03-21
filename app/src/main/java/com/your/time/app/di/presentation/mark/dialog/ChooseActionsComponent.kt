package com.your.time.app.di.presentation.mark.dialog

import com.your.time.app.di.global.ApplicationComponent
import com.your.time.app.di.presentation.ActivityContextModule
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.presentation.mark.dialog.adapters.actions.ActionsRecyclerViewAdapter
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsPresenter
import com.your.time.app.services.ActionImagesServiceImpl
import com.your.time.app.presentation.mark.dialog.adapters.images.ImagesRecyclerAdapter
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [
            ActivityContextModule::class,
            ChooseActionsModule::class
        ]
)
interface ChooseActionsComponent {

    fun getPresenter(): ChooseActionsPresenter
    fun getActionsAdapter(): ActionsRecyclerViewAdapter
    fun getImagesAdapter(): ImagesRecyclerAdapter
    fun getImagesForChoosing(): ActionImagesServiceImpl
    fun getActionFactory(): ActionFactory
}