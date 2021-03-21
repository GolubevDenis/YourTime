package com.your.time.app.presentation.add_task.mvp

import com.your.time.app.domain.model.actions.ActionModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface AddTaskView : MvpView {

    fun showError()
    fun showActions(actions: List<ActionModel>)
    fun showDatePicker(minDate: Long, selectedTime: Long, maxDate: Long)
    fun showDate(dateText: String)
    fun showTimePicker()
    fun showTime(time: String)
    fun showErrorSelectAction()
    fun taskIsAdded()
}