package com.your.time.app.domain.interactors.data.habit

import com.your.time.app.domain.model.HabitModel
import io.reactivex.Completable
import io.reactivex.Single

interface DaoHabitsInteractor {

    fun addHabit(habitModel: HabitModel): Completable
    fun getAllHabits(): Single<List<HabitModel>>
}