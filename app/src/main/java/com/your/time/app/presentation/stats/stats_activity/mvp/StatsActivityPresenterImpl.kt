package com.your.time.app.presentation.stats.stats_activity.mvp

import com.your.time.app.domain.interactors.stats.AcitivityInteractor
import com.your.time.app.domain.services.SchedulersService

class StatsActivityPresenterImpl(
        private val acitivityInteractor: AcitivityInteractor,
        private val schedulersService: SchedulersService
) : StatsActivityPresenter() {

    override fun onClickYesterday() {
        acitivityInteractor.getYesterdayStats()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ periods ->
                    ifViewAttached { it.showPeriods(periods) }
                }, {
                    it.printStackTrace()
                    ifViewAttached { it.showLoadingDataError() }
                })
    }

    override fun onClickToday() {
        acitivityInteractor.getTodayStats()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ periods ->
                    ifViewAttached { it.showPeriods(periods) }
                }, {
                    it.printStackTrace()
                    ifViewAttached { it.showLoadingDataError() }
                })
    }

    override fun onClickLast7Days() {
        acitivityInteractor.getLast7DaysStats()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ periods ->
                    ifViewAttached { it.showPeriods(periods) }
                }, {
                    it.printStackTrace()
                    ifViewAttached { it.showLoadingDataError() }
                })
    }

    override fun onClickLast30Days() {
        acitivityInteractor.getLast30DaysStats()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ periods ->
                    ifViewAttached { it.showPeriods(periods) }
                }, {
                    it.printStackTrace()
                    ifViewAttached { it.showLoadingDataError() }
                })
    }
}