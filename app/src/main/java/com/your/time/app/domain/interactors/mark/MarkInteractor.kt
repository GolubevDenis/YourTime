package com.your.time.app.domain.interactors.mark

import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.time.TimeModelImpl
import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Completable
import io.reactivex.Single

interface MarkInteractor {

    fun newTimePeriodsByActions(actions: List<ActionModel>): Completable
    fun getActiveTimePeriods(): Single<List<TimePeriodModel>>
    fun subscribeOnTimePeriodsChanging(listener: TimeModelImpl.OnTimePeriodsChangedListener)
    fun addTimePeriod(timePeriod: TimePeriodModel)
    fun deleteTimePeriod(timePeriod: TimePeriodModel)
    fun commitTimePeriods()
    fun getUnmarkedTime(): Single<Int>
    fun clearTimePeriods()

    fun markAsCleared(): Completable
    fun markAsCleared(minutesForClearing: Int): Completable
    fun getDuration(): Int
}