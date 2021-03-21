package com.your.time.app.domain.model

import com.your.time.app.domain.model.actions.ActionModel

data class TaskModel(
        var action: ActionModel,
        var description: String? = null,
        var timeInMinutes: Int,
        var isJustInTime: Boolean = false,
        var isManuallyCompleted: Boolean = false,
        var isNeedRemind: Boolean = false
)