package com.your.time.app.presentation.habits.adapter

import android.support.v7.widget.RecyclerView
import com.your.time.app.databinding.HabitListItemBinding
import com.your.time.app.domain.model.HabitModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.explansions.invisible
import com.your.time.app.presentation.explansions.visible

class HabitHolder(
        private val binder: HabitListItemBinding,
        private val timeUtil: TimeUtil
) : RecyclerView.ViewHolder(binder.root) {

    fun bind(habit: HabitModel) {
        binder.action.action = habit.action

        binder.weekdays.weekdays = habit.weekdays

        val time = timeUtil.getTextTimeDuration(habit.duration)
        binder.time.text = time

        if(habit.isNeedRemind) binder.isNeedNotify.visible()
        else binder.isNeedNotify.invisible()
    }
}