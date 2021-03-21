package com.your.time.app.presentation.stats.stats_usefulness.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

abstract class StatsUsefulnessPresenter : MvpBasePresenter<StatsUsefulnessView>() {

    abstract fun onClickLast7Days()
    abstract fun onClickLast30Days()
}