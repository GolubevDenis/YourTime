package com.your.time.app.di.presentation.add_habit

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractor
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.add_habit.mvp.AddHabitPresenter
import com.your.time.app.presentation.add_habit.mvp.AddHabitPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class AddHabitModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            daoActionsInteractor: DaoActionsInteractor,
            daoHabitsInteractor: DaoHabitsInteractor,
            schedulersService: SchedulersService,
            timeUtil: TimeUtil
    ): AddHabitPresenter
            = AddHabitPresenterImpl(daoActionsInteractor, daoHabitsInteractor, schedulersService, timeUtil)
}