package com.your.time.app.domain.model.time

import com.your.time.app.domain.model.TimePeriodModel

interface TimeModel {

    fun putPeriod(timePeriod: TimePeriodModel)
    fun getTimePeriod(i: Int): TimePeriodModel
    fun putPeriods(timePeriods: List<TimePeriodModel>)
    fun deletePeriod(timePeriod: TimePeriodModel)
    fun clear()
    fun vacuum()
    fun getTimePeriods(): List<TimePeriodModel>
    fun getDuration(): Int
    fun getMinStartTime(): Int
    fun getMaxEndTime(): Int
    fun setStartTimeBound(startTimeBound: Int)
    fun getStartTimeBound(): Int?
    fun setEndTimeBound(endTimeBound: Int)
    fun getEndTimeBound(): Int?
    fun addOnTimePeriodsChangedListener(listener: TimeModelImpl.OnTimePeriodsChangedListener)
    fun removeOnTimePeriodsChangedListener(listener: TimeModelImpl.OnTimePeriodsChangedListener)
    fun getOnTimePeriodsChangedListeners(): ArrayList<TimeModelImpl.OnTimePeriodsChangedListener>
}