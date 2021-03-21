package com.your.time.app.presentation.habits.mvp

import com.your.time.app.domain.model.HabitModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface HabitsView : MvpView {
    fun showHabits(habits: List<HabitModel>)
    fun showError()
}