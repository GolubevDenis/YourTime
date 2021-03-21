package com.your.time.app.domain.data.repository

import com.your.time.app.domain.model.HabitModel
import io.reactivex.Completable
import io.reactivex.Single

interface HabitsRepository {
    fun getAllHabits(): Single<List<HabitModel>>
    fun addHabit(habit: HabitModel): Completable
    fun deleteHabit(habit: HabitModel): Completable
}