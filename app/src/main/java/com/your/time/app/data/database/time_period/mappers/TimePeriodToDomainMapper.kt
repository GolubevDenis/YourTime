package com.your.time.app.data.database.time_period.mappers

import com.your.time.app.data.database.time_period.TimePeriodRoomModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.mappers.MultiMapper

class TimePeriodToDomainMapper : MultiMapper<TimePeriodRoomModel, ActionModel, TimePeriodModel> {

    override fun map(from1: TimePeriodRoomModel, from2: ActionModel): TimePeriodModel {
        return TimePeriodModel(
                action = from2,
                startTimeMinutes = from1.startTimeMinutes,
                endTimeMinutes = from1.endTimeMinutes,
                comment = from1.comment
        )
    }
}