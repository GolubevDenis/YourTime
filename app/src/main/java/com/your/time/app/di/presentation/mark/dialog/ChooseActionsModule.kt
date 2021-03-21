package com.your.time.app.di.presentation.mark.dialog

import android.content.Context
import com.your.time.app.di.presentation.ActivityContext
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.createAction.CreateActionInteractor
import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.model.ImageModel
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.mark.dialog.adapters.actions.ActionHolder
import com.your.time.app.presentation.mark.dialog.adapters.actions.ActionsRecyclerViewAdapter
import com.your.time.app.presentation.mark.dialog.adapters.images.ImageHolder
import com.your.time.app.presentation.mark.dialog.adapters.images.ImagesRecyclerAdapter
import com.your.time.app.presentation.mark.dialog.adapters.images.ImagesRecyclerAdapterImpl
import com.your.time.app.presentation.mark.dialog.adapters.list.ListForActionsAdapter
import com.your.time.app.presentation.mark.dialog.adapters.list.ListForActionsAdapterImpl
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMover
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMoverImpl
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsPresenter
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsPresenterImpl
import com.your.time.app.presentation.view.adapters.list.ListForAdapter
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.your.time.app.services.ActionImagesServiceImpl
import dagger.Module
import dagger.Provides

@Module
class ChooseActionsModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            interactor: DaoActionsInteractor,
            createActionInteractor: CreateActionInteractor,
            schedulersService: SchedulersService,
            markInteractor: MarkInteractor,
            timeUtil: TimeUtil
    ): ChooseActionsPresenter
            = ChooseActionsPresenterImpl(
            interactor,
            createActionInteractor,
            schedulersService,
            markInteractor,
            timeUtil
    )

    @PresentationScope
    @Provides
    fun provideActionsAdapter(
            @ActivityContext context: Context,
            list:ListForActionsAdapter<ActionHolder>,
            mover: ChangeActiveStateActionsMover): ActionsRecyclerViewAdapter {
        return ActionsRecyclerViewAdapter(context, list, mover)
    }

    @PresentationScope
    @Provides
    fun provideImagesAdapter(
            @ActivityContext context: Context,
            list: ListForAdapter<ImageModel, ImageHolder>): ImagesRecyclerAdapter {
        return ImagesRecyclerAdapterImpl(context, list)
    }

    @PresentationScope
    @Provides
    fun provideListForIntAdapter(): ListForAdapter<ImageModel, ImageHolder> {
        return ListForAdapterBase<ImageModel, ImageHolder>()
    }

    @PresentationScope
    @Provides
    fun provideListForActionsAdapter(): ListForActionsAdapter<ActionHolder> {
        return ListForActionsAdapterImpl()
    }

    @PresentationScope
    @Provides
    fun provideImagesForChoosing(): ActionImagesServiceImpl {
        return ActionImagesServiceImpl()
    }

    @PresentationScope
    @Provides
    fun provideChangeActiveStateActionsMover(
            list: ListForActionsAdapter<ActionHolder>): ChangeActiveStateActionsMover {
        return ChangeActiveStateActionsMoverImpl(list)
    }
}