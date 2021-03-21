package com.your.time.app.domain.interactors.home

import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Single

interface HomeInteractor {

    fun getTimePeriods(): Single<List<TimePeriodModel>>
    fun getTimePeriodsPM(): Single<List<TimePeriodModel>>
    fun getTimePeriodsAM(): Single<List<TimePeriodModel>>
}