package com.your.time.app.di.presentation.uploading_actions

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.data.action.uploading.UploadingFirstActionsInteractor
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.presentation.uploading_actions.mvp.UploadingActionsPresenter
import com.your.time.app.presentation.uploading_actions.mvp.UploadingActionsPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class UploadingActionsModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            interactor: UploadingFirstActionsInteractor,
            resourcesService: ResourcesService,
            schedulersService: SchedulersService
    ): UploadingActionsPresenter =
            UploadingActionsPresenterImpl(interactor, resourcesService, schedulersService)
}