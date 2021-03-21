package com.your.time.app.di.presentation.tasks

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.data.task.DaoTaskInteractor
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.tasks.mvp.TasksPresenter
import com.your.time.app.presentation.tasks.mvp.TasksPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class TasksModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            taskInteractor: DaoTaskInteractor,
            schedulersService: SchedulersService,
            timeUtil: TimeUtil
    ): TasksPresenter
            = TasksPresenterImpl(taskInteractor, schedulersService, timeUtil)
}