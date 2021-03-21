package com.your.time.app.data.database.tasks

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.your.time.app.data.database.tasks.TaskRoomModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME,
        primaryKeys = [
            (TaskRoomModel.ACTION_ID),
            (TaskRoomModel.TIME_IN_MINUTES),
            (TaskRoomModel.IS_JUST_IN_TIME)
        ]
)
data class TaskRoomModel(
        @ColumnInfo(name = ACTION_ID) var actionId: Long,
        @ColumnInfo(name = DESCRIPTION) var description: String? = null,
        @ColumnInfo(name = TIME_IN_MINUTES) var timeInMinutes: Int,
        @ColumnInfo(name = IS_JUST_IN_TIME) var isJustInTime: Boolean = false,
        @ColumnInfo(name = IS_MANUALLY_COMPLETED) var isManuallyCompleted: Boolean = false,
        @ColumnInfo(name = IS_NEED_REMIND) var isNeedRemind: Boolean = false
){

    companion object {
        const val TABLE_NAME = "tasks"

        const val ACTION_ID = "action_id"
        const val DESCRIPTION = "description"
        const val TIME_IN_MINUTES = "timeInMinutes"
        const val IS_JUST_IN_TIME = "isJustInTime"
        const val IS_MANUALLY_COMPLETED = "isManuallyCompleted"
        const val IS_NEED_REMIND = "isNeedRemind"
    }
}