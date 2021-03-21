package com.your.time.app.domain.interactors.time

import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.Single
import java.util.LinkedHashMap

class TimeInteractorImpl(
        private val timeUtil: TimeUtil
) : TimeInteractor {

    override fun separatePeriodsToDays(single: Single<List<TimePeriodModel>>)
            : Single<LinkedHashMap<String, List<TimePeriodModel>>> {

        return single.map { list ->

            val map = linkedMapOf<String, ArrayList<TimePeriodModel>>()

            list
                    .forEach { period ->
                        val startTimeMillis = timeUtil.minutesToMilliseconds(period.startTimeMinutes)
                        val endTimeMillis = timeUtil.minutesToMilliseconds(period.endTimeMinutes)
                        val startTimeTextDate = timeUtil.getDateText(startTimeMillis)
                        val endTimeTextDate = timeUtil.getDateText(endTimeMillis)

                        val textDate =
                                if(startTimeTextDate == endTimeTextDate) startTimeTextDate
                                else endTimeTextDate

                        if (!map.containsKey(textDate)) {
                            map[textDate] = arrayListOf()
                        }
                        map[textDate]!!.add(period)
                    }

            map as LinkedHashMap<String, List<TimePeriodModel>>
        }
    }

    override fun mergeAdjacentPeriods(single: Single<List<TimePeriodModel>>): Single<List<TimePeriodModel>> {
        return single
                .map { periods ->

                    if (periods.isEmpty()) return@map periods

                    val resultList = arrayListOf<TimePeriodModel>()
                    resultList.add(periods[0])
                    var i = 0
                    for (period in periods) {
                        if (period.action == resultList[i].action) {
                            val periodInResultList = resultList[i]
                            periodInResultList.endTimeMinutes = period.endTimeMinutes
                        } else {
                            resultList.add(period)
                            i++
                        }
                    }
                    resultList
                }
    }

    override fun mergeAllPeriods(single: Single<List<TimePeriodModel>>): Single<List<TimePeriodModel>> {
        return single
                .map { periods ->
                    val timeForAction = hashMapOf<ActionModel, Int>()
                    periods.forEach { period ->
                        if (timeForAction.containsKey(period.action))
                            timeForAction[period.action] = timeForAction[period.action]!! + period.getDurationInMinutes()
                        else
                            timeForAction[period.action] = period.getDurationInMinutes()
                    }
                    val listOfPeriods = arrayListOf<TimePeriodModel>()
                    timeForAction.forEach { e ->
                        listOfPeriods.add(TimePeriodModel(e.key, 0, e.value))
                    }
                    listOfPeriods
                }
    }

    override fun sortByDuration(single: Single<List<TimePeriodModel>>): Single<List<TimePeriodModel>> {
        return single
                .map {
                    it.sortedBy { it.getDurationInMinutes() }
                }
    }

    override fun sortDescByDuration(single: Single<List<TimePeriodModel>>): Single<List<TimePeriodModel>> {
        return single
                .map {
                    it.sortedByDescending { it.getDurationInMinutes() }
                }
    }

}