package com.your.time.app.data.database.habits

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, primaryKeys = [
    (HabitRoomModel.ACTION),
    (HabitRoomModel.IS_MONDAY),
    (HabitRoomModel.IS_TUESDAY),
    (HabitRoomModel.IS_WEDNESDAY),
    (HabitRoomModel.IS_THURSDAY),
    (HabitRoomModel.IS_FRIDAY),
    (HabitRoomModel.IS_SATURDAY),
    (HabitRoomModel.DURATION),
    (HabitRoomModel.IS_MORE_THEN_DURATION),
    (HabitRoomModel.IS_NEED_REMIND)
])
data class HabitRoomModel(
        @ColumnInfo(name = ACTION) var action: String,
        @ColumnInfo(name = IS_MONDAY) var isMonday: Boolean = false,
        @ColumnInfo(name = IS_TUESDAY) var isTuesday: Boolean = false,
        @ColumnInfo(name = IS_WEDNESDAY) var isWednesday: Boolean = false,
        @ColumnInfo(name = IS_THURSDAY) var isThursday: Boolean = false,
        @ColumnInfo(name = IS_FRIDAY) var isFriday: Boolean = false,
        @ColumnInfo(name = IS_SATURDAY) var isSaturday: Boolean = false,
        @ColumnInfo(name = IS_SUNDAY) var isSunday: Boolean = false,
        @ColumnInfo(name = DURATION) var duration: Int,
        @ColumnInfo(name = IS_MORE_THEN_DURATION) var isMoreThenDuration: Boolean,
        @ColumnInfo(name = IS_NEED_REMIND) var isNeedRemind: Boolean = false
){
    companion object {
        const val TABLE_NAME = "habits"

        const val ACTION = "action"
        const val IS_MONDAY = "isMonday"
        const val IS_TUESDAY = "isTuesday"
        const val IS_WEDNESDAY = "isWednesday"
        const val IS_THURSDAY = "isThursday"
        const val IS_FRIDAY = "isFriday"
        const val IS_SATURDAY = "isSaturday"
        const val IS_SUNDAY = "isSunday"
        const val DURATION = "startTime"
        const val IS_MORE_THEN_DURATION = "endTime"
        const val IS_NEED_REMIND = "isNeedRemind"
    }
}