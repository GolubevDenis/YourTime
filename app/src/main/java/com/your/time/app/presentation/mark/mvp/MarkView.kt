package com.your.time.app.presentation.mark.mvp

import com.your.time.app.domain.model.TimePeriodModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface MarkView : MvpView {
    fun showMoreTimePeriods(periods: List<TimePeriodModel>)
    fun showErrorMessage(message: String)
    fun showUpdatingTimePeriod(position: Int)
    fun showAddingTimePeriod(period: TimePeriodModel)
    fun showRemovingTimePeriod(period: TimePeriodModel)
    fun showCommitSuccess()
    fun showTimePeriods(periods: List<TimePeriodModel>)
    fun showUnmarkedTime(textTime: String)
    fun updateVisibilityOfTimeClearingButtons(unmarkedTime: Int)
    fun hideRemainingUnmurkedTime()
    fun showRemaxiningUnmurkedTime(leftUnmarkedTime: String)
}