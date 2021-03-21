package com.your.time.app.di.presentation.home

import android.content.Context
import com.your.time.app.di.presentation.ActivityContext
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractor
import com.your.time.app.domain.interactors.data.task.DaoTaskInteractor
import com.your.time.app.domain.interactors.data.usefulness.UsefulnessInteractor
import com.your.time.app.domain.interactors.home.HomeInteractor
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.home.mvp.HomePresenter
import com.your.time.app.presentation.home.mvp.HomePresenterImpl
import com.your.time.app.presentation.home.adapters.actions.ActionsHomeRecyclerViewAdapter
import com.your.time.app.presentation.mark.dialog.adapters.list.ListForActionsAdapter
import com.your.time.app.presentation.mark.dialog.adapters.list.ListForActionsAdapterImpl
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMover
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMoverImpl
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            homeInteractor: HomeInteractor,
            markInteractor: MarkInteractor,
            timeUtil: TimeUtil,
            habitsInteractor: DaoHabitsInteractor,
            tasksInteractor: DaoTaskInteractor,
            daoActionsInteractor: DaoActionsInteractor,
            schedulersService: SchedulersService,
            usefulnessInteractor: UsefulnessInteractor
    ): HomePresenter = HomePresenterImpl(
            homeInteractor,
            markInteractor,
            timeUtil,
            habitsInteractor,
            tasksInteractor,
            daoActionsInteractor,
            schedulersService,
            usefulnessInteractor
    )

    @PresentationScope
    @Provides
    fun provideActionsAdapter(
            @ActivityContext context: Context,
            list: ListForActionsAdapter<ActionHomeHolder>): ActionsHomeRecyclerViewAdapter {
        return ActionsHomeRecyclerViewAdapter(context, list)
    }

    @PresentationScope
    @Provides
    fun provideListForActionsAdapter(): ListForActionsAdapter<ActionHomeHolder> {
        return ListForActionsAdapterImpl()
    }
}