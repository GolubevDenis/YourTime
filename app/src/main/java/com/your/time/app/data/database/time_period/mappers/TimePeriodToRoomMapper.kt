package com.your.time.app.data.database.time_period.mappers

import com.your.time.app.data.database.time_period.TimePeriodRoomModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.mappers.Mapper

class TimePeriodToRoomMapper : Mapper<TimePeriodModel, TimePeriodRoomModel> {

    override fun map(from: TimePeriodModel): TimePeriodRoomModel {
        return TimePeriodRoomModel(
                id = null,
                actionId = from.action.id!!,
                startTimeMinutes = from.startTimeMinutes,
                endTimeMinutes = from.endTimeMinutes,
                comment = from.comment
        )
    }
}