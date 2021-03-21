package com.your.time.app.presentation.stats.stats_usefulness.mvp

import com.your.time.app.domain.interactors.data.usefulness.UsefulnessInteractor
import com.your.time.app.domain.services.SchedulersService

class StatsUsefulnessPresenterImpl(
        private val usefulnessInteractor: UsefulnessInteractor,
        private val schedulersService: SchedulersService
) : StatsUsefulnessPresenter() {

    override fun onClickLast7Days() {
        usefulnessInteractor.getUsefulnessOf7Days()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ usefulness ->
                    ifViewAttached { it.showUsefulnessOfDays(usefulness) }
                }, {
                    it.printStackTrace()
                    ifViewAttached { it.showLoadingDataError() }
                })
    }

    override fun onClickLast30Days() {
        usefulnessInteractor.getUsefulnessOf30Days()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ usefulness ->
                    ifViewAttached { it.showUsefulnessOfDays(usefulness) }
                }, {
                    it.printStackTrace()
                    ifViewAttached { it.showLoadingDataError() }
                })
    }
}