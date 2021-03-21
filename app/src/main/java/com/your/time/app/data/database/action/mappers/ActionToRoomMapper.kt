package com.your.time.app.data.database.action.mappers

import com.your.time.app.data.database.action.ActionRoomModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.mappers.Mapper

class ActionToRoomMapper : Mapper<ActionModel, ActionRoomModel> {

    override fun map(from: ActionModel): ActionRoomModel {
        return ActionRoomModel(
                action = from.action,
                iconName = from.iconName,
                usefulness = from.usefulness?.toString(),
                color = from.color,
                id = from.id
        )
    }
}