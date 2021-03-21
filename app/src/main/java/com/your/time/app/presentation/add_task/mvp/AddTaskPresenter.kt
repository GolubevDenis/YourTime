package com.your.time.app.presentation.add_task.mvp

import com.your.time.app.domain.model.actions.ActionModel
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

abstract class AddTaskPresenter : MvpBasePresenter<AddTaskView>() {

    abstract fun onClickDatePicker()
    abstract fun onDateSelected(year: Int, month: Int, dayOfMonth: Int)
    abstract fun onClickTimePicker()
    abstract fun onTimeSelected(hours: Int, minutes: Int)
    abstract fun onClickSearch(searchQueryText: String)
    abstract fun onClickCreate(action: ActionModel?, description: String?, needRemind: Boolean)
}