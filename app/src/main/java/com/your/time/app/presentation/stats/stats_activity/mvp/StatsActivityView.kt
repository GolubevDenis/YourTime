package com.your.time.app.presentation.stats.stats_activity.mvp

import com.your.time.app.domain.model.TimePeriodModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface StatsActivityView : MvpView {

    fun showPeriods(periods: List<TimePeriodModel>)
    fun showLoadingDataError()
}