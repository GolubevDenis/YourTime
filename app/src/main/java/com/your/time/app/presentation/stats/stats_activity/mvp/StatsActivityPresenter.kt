package com.your.time.app.presentation.stats.stats_activity.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

abstract class StatsActivityPresenter : MvpBasePresenter<StatsActivityView>() {

    abstract fun onClickYesterday()
    abstract fun onClickLast7Days()
    abstract fun onClickLast30Days()
    abstract fun onClickToday()
}