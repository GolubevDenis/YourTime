package com.your.time.app.domain.interactors.time

import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Single
import java.util.LinkedHashMap

interface TimeInteractor {

    fun mergeAdjacentPeriods(single: Single<List<TimePeriodModel>>): Single<List<TimePeriodModel>>
    fun mergeAllPeriods(single: Single<List<TimePeriodModel>>): Single<List<TimePeriodModel>>

    fun sortByDuration(single: Single<List<TimePeriodModel>>): Single<List<TimePeriodModel>>
    fun sortDescByDuration(single: Single<List<TimePeriodModel>>): Single<List<TimePeriodModel>>
    fun separatePeriodsToDays(single: Single<List<TimePeriodModel>>): Single<LinkedHashMap<String, List<TimePeriodModel>>>
}