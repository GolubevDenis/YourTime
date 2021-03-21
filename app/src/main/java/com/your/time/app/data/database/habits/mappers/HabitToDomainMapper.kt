package com.your.time.app.data.database.habits.mappers

import com.your.time.app.data.database.habits.HabitRoomModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.HabitModel
import com.your.time.app.domain.model.Weekdays
import com.your.time.app.domain.model.mappers.MultiMapper

class HabitToDomainMapper : MultiMapper<HabitRoomModel, ActionModel, HabitModel> {

    override fun map(from1: HabitRoomModel, from2: ActionModel): HabitModel {

        val weekdays = Weekdays(from1.isMonday, from1.isTuesday, from1.isWednesday,
                from1.isThursday, from1.isFriday, from1.isSaturday, from1.isSunday)

        return HabitModel(
                action = from2,
                weekdays = weekdays,
                duration = from1.duration,
                isMoreThenDuration = from1.isMoreThenDuration,
                isNeedRemind = from1.isNeedRemind
        )
    }
}