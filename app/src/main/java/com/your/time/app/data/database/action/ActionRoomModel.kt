package com.your.time.app.data.database.action

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.your.time.app.data.database.action.ActionRoomModel.Companion.TABLE_NAME
import com.your.time.app.domain.model.actions.ActionModel

@Entity(tableName = TABLE_NAME)
data class ActionRoomModel constructor(
        @PrimaryKey(autoGenerate = true) val id: Long? = null,
        @ColumnInfo(name = ACTION) val action: String,
        @ColumnInfo(name = ICON_NAME) val iconName: String? = null,
        @ColumnInfo(name = USEFULNESS) val usefulness: String? = ActionModel.Usefulness.NEUTRALLY.toString(),
        @ColumnInfo(name = COLOR) val color: Int,
        @ColumnInfo(name = IS_ACTIVE) var isActive: Int = 1
) {

    companion object {
        const val TABLE_NAME = "actions"

        const val ID = "id"
        const val ACTION = "action"
        const val ICON_NAME = "iconName"
        const val USEFULNESS = "usefulness"
        const val COLOR = "color"
        const val IS_ACTIVE = "is_active"
    }
}