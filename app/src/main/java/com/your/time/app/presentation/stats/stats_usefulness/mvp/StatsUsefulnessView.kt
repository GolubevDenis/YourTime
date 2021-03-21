package com.your.time.app.presentation.stats.stats_usefulness.mvp

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.your.time.app.domain.model.UsefulnessOfDay

interface StatsUsefulnessView : MvpView {

    fun showUsefulnessOfDays(usefulness: List<UsefulnessOfDay>)
    fun showLoadingDataError()
}