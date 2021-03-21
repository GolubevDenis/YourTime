package com.your.time.app.presentation.tasks.mvp

import com.your.time.app.domain.model.TaskModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface TasksView : MvpView {
    fun showTasks(tasks: List<TaskModel>)
    fun showError()
}