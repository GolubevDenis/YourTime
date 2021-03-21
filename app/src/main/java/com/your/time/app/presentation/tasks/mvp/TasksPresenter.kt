package com.your.time.app.presentation.tasks.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import java.util.*

abstract class TasksPresenter : MvpBasePresenter<TasksView>() {

    abstract fun onSelectedDateRange(startDate: Calendar?, endDate: Calendar?)
    abstract fun onSelectedDate(date: Calendar?)
    abstract fun onClickShowAllTasks()
}