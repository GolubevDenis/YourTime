package com.your.time.app.presentation.add_habit.mvp

import com.your.time.app.domain.model.actions.ActionModel
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

abstract class AddHabitPresenter : MvpBasePresenter<AddHabitView>() {

    abstract fun onClickSearch(query: String)
    abstract fun onClickCreate(action: ActionModel?, hours: Int, minutes: Int, monday: Boolean, tuesday: Boolean, wednesday: Boolean, thursday: Boolean, friday: Boolean, saturday: Boolean, sunday: Boolean, isMoreThenDuration: Boolean, isNeedToRemind: Boolean)
}