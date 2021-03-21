package com.your.time.app.presentation.add_habit.mvp

import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.HabitModel
import com.your.time.app.domain.model.Weekdays
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil

class AddHabitPresenterImpl(
        private val daoActionsInteractor: DaoActionsInteractor,
        private val daoHabitsInteractor: DaoHabitsInteractor,
        private val schedulersService: SchedulersService,
        private val timeUtil: TimeUtil
) : AddHabitPresenter() {

    override fun attachView(view: AddHabitView) {
        super.attachView(view)

        daoActionsInteractor.getAllActions()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ actions ->
                    ifViewAttached {
                        it.showActions(actions)
                    }
                }, {
                   // TODO
                })
    }

    override fun onClickSearch(query: String) {
        daoActionsInteractor.queryActiveActionByTitle(query)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ actions ->
                    ifViewAttached {
                        it.showActions(actions)
                    }
                }, {
                    ifViewAttached { it.showErrorSearching() }
                })
    }

    override fun onClickCreate(action: ActionModel?, hours: Int, minutes: Int, monday: Boolean, tuesday: Boolean, wednesday: Boolean,
                               thursday: Boolean, friday: Boolean, saturday: Boolean, sunday: Boolean,
                               isMoreThenDuration: Boolean, isNeedToRemind: Boolean) {
        if(action == null){
            ifViewAttached { it.showNoActionSelectedError() }
            return
        }
        if(!monday && !tuesday && !wednesday && !thursday && !friday && !saturday && !sunday){
            ifViewAttached { it.showNoDaySelected() }
            return
        }

        val duration = timeUtil.getTime(hours, minutes)
        val durationMinutes = timeUtil.millisecondsToMinutes(duration)

        val weekdays = Weekdays(monday, tuesday, wednesday, thursday, friday, saturday, sunday)

        val habit = HabitModel(
            action, weekdays, durationMinutes, isMoreThenDuration, isNeedToRemind
        )

        daoHabitsInteractor.addHabit(habit)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({
                    ifViewAttached { it.showSuccessAddind() }
                }, {
                    ifViewAttached { it.showErrorAddind() }
                })
    }
}