package com.your.time.app.presentation.habits.mvp

import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractor
import com.your.time.app.domain.services.SchedulersService

class HabitsPresenterImpl(
        private val daoHabitsInteractor: DaoHabitsInteractor,
        private val schedulersService: SchedulersService
) : HabitsPresenter() {

    override fun attachView(view: HabitsView) {
        super.attachView(view)

        daoHabitsInteractor.getAllHabits()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ habits ->
                    ifViewAttached { it.showHabits(habits) }
                }, {
                    ifViewAttached { it.showError() }
                })
    }
}