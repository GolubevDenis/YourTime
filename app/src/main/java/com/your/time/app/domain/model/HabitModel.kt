package com.your.time.app.domain.model

import com.your.time.app.domain.model.actions.ActionModel

data class HabitModel(
        var action: ActionModel,
        var weekdays: Weekdays,
        var duration: Int,
        var isMoreThenDuration: Boolean,
        var isNeedRemind: Boolean = false
)