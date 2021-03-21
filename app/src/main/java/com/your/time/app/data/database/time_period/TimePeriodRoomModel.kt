package com.your.time.app.data.database.time_period

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.your.time.app.data.database.time_period.TimePeriodRoomModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TimePeriodRoomModel(
        @PrimaryKey(autoGenerate = true) var id: Long? = null,
        @ColumnInfo(name = ACTION_ID) var actionId: Long,
        @ColumnInfo(name = START_TIME) var startTimeMinutes: Int,
        @ColumnInfo(name = END_TIME) var endTimeMinutes: Int,
        @ColumnInfo(name = COMMENT) var comment: String? = null
) {

    companion object {
        const val TABLE_NAME = "time_periods"
        const val ID = "id"
        const val ACTION_ID = "action_id"
        const val START_TIME = "startTimeMinutes"
        const val END_TIME = "endTimeMinutes"
        const val COMMENT = "comment"
    }
}