package com.your.time.app.presentation.mark.mvp

import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

abstract class MarkPresenter : MvpBasePresenter<MarkView>() {
    abstract fun onActionsSelected(actions: List<ActionModel>)
    abstract fun onClickTimePeriodClose(timePeriod: TimePeriodModel)
    abstract fun onClickOk()
    abstract fun onStart()
    abstract fun onClickClearTime(minutesForClearing: Int)
}