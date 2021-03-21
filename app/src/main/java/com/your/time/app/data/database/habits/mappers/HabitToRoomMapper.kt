package com.your.time.app.data.database.habits.mappers

import com.your.time.app.data.database.habits.HabitRoomModel
import com.your.time.app.domain.model.HabitModel
import com.your.time.app.domain.model.mappers.Mapper

class HabitToRoomMapper : Mapper<HabitModel, HabitRoomModel> {

    override fun map(from: HabitModel): HabitRoomModel {
        return HabitRoomModel(
                from.action.action,
                isMonday = from.weekdays.isMonday,
                isTuesday = from.weekdays.isTuesday,
                isWednesday = from.weekdays.isWednesday,
                isThursday = from.weekdays.isThursday,
                isFriday = from.weekdays.isFriday,
                isSaturday = from.weekdays.isSaturday,
                isSunday = from.weekdays.isSunday,
                duration = from.duration,
                isMoreThenDuration = from.isMoreThenDuration,
                isNeedRemind = from.isNeedRemind
        )
    }
}