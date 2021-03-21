package com.your.time.app.di.presentation.add_task

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.data.task.DaoTaskInteractor
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.add_task.mvp.AddTaskPresenter
import com.your.time.app.presentation.add_task.mvp.AddTaskPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class AddTaskModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            taskDaoTaskInteractor: DaoTaskInteractor,
            daoActionsInteractor: DaoActionsInteractor,
            schedulersService: SchedulersService,
            timeUtil: TimeUtil
    ): AddTaskPresenter
            = AddTaskPresenterImpl(taskDaoTaskInteractor, daoActionsInteractor, schedulersService, timeUtil)
}