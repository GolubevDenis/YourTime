package com.your.time.app.domain.interactors.data.habit

import com.your.time.app.domain.data.repository.HabitsRepository
import com.your.time.app.domain.model.HabitModel
import io.reactivex.Completable
import io.reactivex.Single

class DaoHabitsInteractorImpl(
        private val habitsRepository: HabitsRepository
) : DaoHabitsInteractor {

    override fun addHabit(habitModel: HabitModel): Completable {
        return habitsRepository.addHabit(habitModel)
    }

    override fun getAllHabits(): Single<List<HabitModel>> {
        return habitsRepository.getAllHabits()
    }
}