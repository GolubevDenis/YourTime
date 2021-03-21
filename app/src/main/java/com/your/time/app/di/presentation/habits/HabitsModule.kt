package com.your.time.app.di.presentation.habits

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractor
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.presentation.habits.mvp.HabitsPresenter
import com.your.time.app.presentation.habits.mvp.HabitsPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class HabitsModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            daoHabitsInteractor: DaoHabitsInteractor,
            schedulersService: SchedulersService
    ): HabitsPresenter
            = HabitsPresenterImpl(daoHabitsInteractor, schedulersService)
}