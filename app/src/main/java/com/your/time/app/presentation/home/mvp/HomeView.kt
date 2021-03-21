package com.your.time.app.presentation.home.mvp

import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.model.TimePeriodModel
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.your.time.app.domain.model.actions.ActionModel

interface HomeView : MvpView {

    fun showTimePeriods(periods: List<TimePeriodModel>)
    fun showError(message: String)
    fun showTasks(tasks: List<TaskModel>)
    fun showUsefulness(usefulness: Float)
    fun showUnmarkedTime(unmarkedTime: String)
    fun hideMarkingPanel()
    fun showActionsForFastMarking(list: List<ActionModel>)
}