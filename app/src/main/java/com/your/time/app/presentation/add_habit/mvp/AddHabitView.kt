package com.your.time.app.presentation.add_habit.mvp

import com.your.time.app.domain.model.actions.ActionModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface AddHabitView : MvpView {

    fun showActions(actions: List<ActionModel>)
    fun showTimeConflict()
    fun showNoActionSelectedError()
    fun showErrorAddind()
    fun showErrorSearching()
    fun showSuccessAddind()
    fun showNoDaySelected()
}