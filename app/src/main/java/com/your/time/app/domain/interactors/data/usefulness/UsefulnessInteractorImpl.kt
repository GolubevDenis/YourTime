package com.your.time.app.domain.interactors.data.usefulness

import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.interactors.time.TimeInteractor
import com.your.time.app.domain.interactors.time.TimeInteractorImpl
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.UsefulnessOfDay
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.Single

class UsefulnessInteractorImpl(
        private val daoTimePeriodInteractor: DaoTimePeriodInteractor,
        private val timeInteractor: TimeInteractor
) : UsefulnessInteractor {

    override fun getUsefulnessOfCurrentDay(): Single<Float> {
        return daoTimePeriodInteractor.getTimePeriodsForToday()
                .map { getUsefulnessOfPeriods(it) }
    }

    private fun getUsefulnessOfPeriods(periods: List<TimePeriodModel>): Float {

        val startTime = periods.minBy { it.startTimeMinutes }?.startTimeMinutes
        val endTime = periods.maxBy { it.endTimeMinutes }?.endTimeMinutes

        if(startTime == null || endTime == null){
            return 0f
        }

        val maxUsefulness = (endTime - startTime) * 4

        var usefulness = 0
        periods.forEach {
            usefulness += getFactorOfUtility(it)
        }
        return usefulness.toFloat() / maxUsefulness * 100
    }

    override fun getUsefulnessOf7Days(): Single<List<UsefulnessOfDay>> {
        return getUsefulnessOfDaysForPeriods(daoTimePeriodInteractor.getTimePeriodsForLast7Days())
    }

    override fun getUsefulnessOf30Days(): Single<List<UsefulnessOfDay>> {
        return getUsefulnessOfDaysForPeriods(daoTimePeriodInteractor.getTimePeriodsForLast30Days())
    }

    private fun getUsefulnessOfDaysForPeriods(single: Single<List<TimePeriodModel>>): Single<List<UsefulnessOfDay>> {
        return timeInteractor.separatePeriodsToDays(single)
                .map { map ->
                    val list = arrayListOf<UsefulnessOfDay>()
                    map.entries.forEach { entry ->
                        val usefulness = getUsefulnessOfPeriods(entry.value)
                        val usefulnessOfDay = UsefulnessOfDay(entry.key, usefulness)
                        list.add(usefulnessOfDay)
                    }
                    return@map list
                }
    }

    private fun getFactorOfUtility(period: TimePeriodModel): Int {

        val coef = when(period.action.usefulness){
            ActionModel.Usefulness.VERY_USEFULLY -> 4
            ActionModel.Usefulness.USEFULLY -> 3
            ActionModel.Usefulness.NEUTRALLY -> 2
            ActionModel.Usefulness.HARMFULLY -> 1
            ActionModel.Usefulness.VERY_HARMFULLY -> 0
            else -> 2
        }

        return period.getDurationInMinutes() * coef
    }
}