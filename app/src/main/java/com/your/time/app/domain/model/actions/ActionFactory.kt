package com.your.time.app.domain.model.actions

abstract class ActionFactory {

    fun createActionModel(
            action: String,
            iconName: String? = null,
            usefulness: ActionModel.Usefulness? = ActionModel.Usefulness.NEUTRALLY,
            color: Int = getRandomActionColor()
    ): ActionModel {
        return ActionModel(action, iconName, usefulness, color)
    }

    protected abstract fun getAllActionColors(): IntArray
    protected abstract fun getRandomActionColor(): Int
    abstract fun createUnmarkedAction(): ActionModel
    abstract fun clearAction(): ActionModel
    abstract fun isServiceAction(action: ActionModel): Boolean
}