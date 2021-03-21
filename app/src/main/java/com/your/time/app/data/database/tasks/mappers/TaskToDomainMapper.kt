package com.your.time.app.data.database.tasks.mappers

import com.your.time.app.data.database.tasks.TaskRoomModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.model.mappers.MultiMapper

class TaskToDomainMapper : MultiMapper<TaskRoomModel, ActionModel, TaskModel> {

    override fun map(from1: TaskRoomModel, from2: ActionModel): TaskModel {
        return TaskModel(
                action = from2,
                description = from1.description,
                isJustInTime = from1.isJustInTime,
                isManuallyCompleted = from1.isManuallyCompleted,
                isNeedRemind = from1.isNeedRemind,
                timeInMinutes = from1.timeInMinutes

        )
    }
}