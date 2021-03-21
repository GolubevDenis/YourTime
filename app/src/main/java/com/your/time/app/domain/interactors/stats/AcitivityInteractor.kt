package com.your.time.app.domain.interactors.stats

import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Single

interface AcitivityInteractor {

    fun getYesterdayStats(): Single<List<TimePeriodModel>>
    fun getLast7DaysStats(): Single<List<TimePeriodModel>>
    fun getLast30DaysStats(): Single<List<TimePeriodModel>>
    fun getTodayStats(): Single<List<TimePeriodModel>>
}