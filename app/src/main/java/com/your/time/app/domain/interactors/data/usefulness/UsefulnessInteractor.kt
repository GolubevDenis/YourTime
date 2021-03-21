package com.your.time.app.domain.interactors.data.usefulness

import com.your.time.app.domain.model.UsefulnessOfDay
import io.reactivex.Single

interface UsefulnessInteractor {

    fun getUsefulnessOfCurrentDay(): Single<Float>

    fun getUsefulnessOf7Days(): Single<List<UsefulnessOfDay>>
    fun getUsefulnessOf30Days(): Single<List<UsefulnessOfDay>>
}