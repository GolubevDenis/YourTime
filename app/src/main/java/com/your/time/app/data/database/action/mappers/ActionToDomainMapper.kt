package com.your.time.app.data.database.action.mappers

import com.your.time.app.data.database.action.ActionRoomModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.mappers.Mapper

class ActionToDomainMapper : Mapper<ActionRoomModel, ActionModel> {

    override fun map(from: ActionRoomModel): ActionModel {
        return ActionModel(
                action = from.action,
                iconName = from.iconName,
                usefulness = if (from.usefulness != null) ActionModel.Usefulness.valueOf(from.usefulness) else null,
                color = from.color,
                id = from.id
        )
    }
}