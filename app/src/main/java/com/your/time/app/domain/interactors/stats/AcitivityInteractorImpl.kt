package com.your.time.app.domain.interactors.stats

import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.interactors.time.TimeInteractor
import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Single

class AcitivityInteractorImpl(
        private val timePeriodInteractor: DaoTimePeriodInteractor,
        private val timeInteractor: TimeInteractor
) : AcitivityInteractor {

    override fun getYesterdayStats(): Single<List<TimePeriodModel>> {
        return timeInteractor.sortDescByDuration(
                timeInteractor.mergeAllPeriods(timePeriodInteractor.getTimePeriodsForYesterday())
        )
    }

    override fun getTodayStats(): Single<List<TimePeriodModel>> {
        return timeInteractor.sortDescByDuration(
                timeInteractor.mergeAllPeriods(timePeriodInteractor.getTimePeriodsForToday())
        )    }

    override fun getLast7DaysStats(): Single<List<TimePeriodModel>> {
        return timeInteractor.sortDescByDuration(
                timeInteractor.mergeAllPeriods(timePeriodInteractor.getTimePeriodsForLast7Days())
        )
    }

    override fun getLast30DaysStats(): Single<List<TimePeriodModel>> {
        return timeInteractor.sortDescByDuration(
                timeInteractor.mergeAllPeriods(timePeriodInteractor.getTimePeriodsForLast30Days())
        )
    }
}