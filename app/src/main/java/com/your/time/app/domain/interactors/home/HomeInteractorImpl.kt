package com.your.time.app.domain.interactors.home

import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.interactors.time.TimeInteractor
import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Single

class HomeInteractorImpl(
        private val timePeriodsDaoInteractor: DaoTimePeriodInteractor,
        private val timeInteractor: TimeInteractor
) : HomeInteractor {

    override fun getTimePeriods(): Single<List<TimePeriodModel>> {
        return timeInteractor.mergeAdjacentPeriods(
                timePeriodsDaoInteractor.getTimePeriodsByTodayWithUnmarkedPeriods()
        )
    }

    override fun getTimePeriodsPM(): Single<List<TimePeriodModel>> {
        return timeInteractor.mergeAdjacentPeriods(
                timePeriodsDaoInteractor.getTimePeriodsByTodayPMWithUnmarkedPeriods()
        )
    }

    override fun getTimePeriodsAM(): Single<List<TimePeriodModel>> {
        return timeInteractor.mergeAdjacentPeriods(
                timePeriodsDaoInteractor.getTimePeriodsByTodayAMWithUnmarkedPeriods()
        )
    }
}