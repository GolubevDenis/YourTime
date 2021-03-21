package com.your.time.app.presentation.tasks.mvp

import com.your.time.app.domain.interactors.data.task.DaoTaskInteractor
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import java.util.*

class TasksPresenterImpl(
        private val tasksInteractor: DaoTaskInteractor,
        private val schedulersService: SchedulersService,
        private val timeUtil: TimeUtil
) : TasksPresenter() {

    override fun attachView(view: TasksView) {
        super.attachView(view)
        showAllTasks()
    }

    private fun showAllTasks(){
        val currentTime = timeUtil.getCurrentTimeInMinutes()
        tasksInteractor.getAllFutureTasks(currentTime)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ list ->
                    ifViewAttached { it.showTasks(list) }
                }, {
                    ifViewAttached { it.showError() }
                })
    }

    override fun onClickShowAllTasks() {
        showAllTasks()
    }

    override fun onSelectedDateRange(startDate: Calendar?, endDate: Calendar?) {
        if(startDate == null || endDate == null) return

        val startTimeInMinutes = timeUtil.millisecondsToMinutes(startDate.timeInMillis)
        val endTimeInMinutes = timeUtil.millisecondsToMinutes(endDate.timeInMillis)

        tasksInteractor.getTasksInInterval(startTimeInMinutes, endTimeInMinutes)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ list ->
                    ifViewAttached { it.showTasks(list) }
                }, {
                    ifViewAttached { it.showError() }
                })
    }

    override fun onSelectedDate(date: Calendar?) {
        if(date == null) return

        val startTimeInMinutes = timeUtil.millisecondsToMinutes(date.timeInMillis)
        val endTimeInMilliseconds= timeUtil.endOfDay(date.timeInMillis)
        val endTimeInMinutes = timeUtil.millisecondsToMinutes(endTimeInMilliseconds)

        tasksInteractor.getTasksInInterval(startTimeInMinutes, endTimeInMinutes)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ list ->
                    ifViewAttached { it.showTasks(list) }
                }, {
                    ifViewAttached { it.showError() }
                })
    }
}