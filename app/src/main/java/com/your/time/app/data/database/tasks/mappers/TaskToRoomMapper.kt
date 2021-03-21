package com.your.time.app.data.database.tasks.mappers

import com.your.time.app.data.database.tasks.TaskRoomModel
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.model.mappers.Mapper

class TaskToRoomMapper : Mapper<TaskModel, TaskRoomModel> {

    override fun map(from: TaskModel): TaskRoomModel {
        return TaskRoomModel(
                actionId = from.action.id!!,
                description = from.description,
                isJustInTime = from.isJustInTime,
                isManuallyCompleted = from.isManuallyCompleted,
                isNeedRemind = from.isNeedRemind,
                timeInMinutes = from.timeInMinutes
        )
    }
}