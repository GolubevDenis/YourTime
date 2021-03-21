package com.your.time.app.domain.interactors.data.time_period

import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface DaoTimePeriodInteractor {

    fun getTimePeriodByEndTime(time: Long): Single<List<TimePeriodModel>>
    fun getTimePeriodByStartTime(time: Long): Single<List<TimePeriodModel>>
    fun deleteTimePeriod(period: TimePeriodModel): Completable
    fun updateTimePeriod(period: TimePeriodModel): Completable
    fun getAllTimePeriods(): Single<List<TimePeriodModel>>
    fun addTimePeriods( periods: List<TimePeriodModel>): Completable
    fun getLastestTimePeriod(): Maybe<TimePeriodModel>
    fun getLastTimePeriods(count: Int): Single<List<TimePeriodModel>>

    fun getTimePeriodsByTodayWithUnmarkedPeriods(): Single<List<TimePeriodModel>>
    fun getTimePeriodsByTodayPMWithUnmarkedPeriods(): Single<List<TimePeriodModel>>
    fun getTimePeriodsByTodayAMWithUnmarkedPeriods(): Single<List<TimePeriodModel>>
    fun getTimePeriodsForYesterday(): Single<List<TimePeriodModel>>
    fun getTimePeriodsForLast7Days(): Single<List<TimePeriodModel>>
    fun getTimePeriodsForLast30Days(): Single<List<TimePeriodModel>>

    /**
     * Returns list of time periods in the time interval.
     * */
    fun getTimePeriodsForInterval(startTimeInMinutes: Int, endTimeInMinutes: Int): Single<List<TimePeriodModel>>

    fun getTimePeriodsForToday(): Single<List<TimePeriodModel>>
}