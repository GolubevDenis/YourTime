package com.your.time.app.domain.data.repository

import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface TimePeriodsRepository {

    fun getLastTimePeriod(): Maybe<TimePeriodModel>
    fun addTimePeriods(periods: List<TimePeriodModel>): Completable
    fun getAllTimePeriods(): Single<List<TimePeriodModel>>
    fun getTimePeriodsByStartTime(time: Long): Single<List<TimePeriodModel>>
    fun deleteTimePeriod(period: TimePeriodModel): Completable
    fun updateTimePeriod(period: TimePeriodModel): Completable
    fun getTimePeriodsInInterval(startTime: Int, endTime: Int): Single<List<TimePeriodModel>>
    fun getTimePeriodsByEndTime(time: Long): Single<List<TimePeriodModel>>
    fun getLastPeriods(count: Int): Single<List<TimePeriodModel>>
}