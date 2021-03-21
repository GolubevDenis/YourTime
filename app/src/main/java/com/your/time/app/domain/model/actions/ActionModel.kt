package com.your.time.app.domain.model.actions

import android.content.Context
import com.your.time.app.expansions.getDrawableIdByName

data class ActionModel constructor(
        val action: String,
        val iconName: String? = null,
        val usefulness: Usefulness? = Usefulness.NEUTRALLY,
        val color: Int,
        var id: Long? = null
) {
    enum class Usefulness {
        VERY_USEFULLY, USEFULLY, NEUTRALLY, HARMFULLY, VERY_HARMFULLY
    }

    private var iconId: Int? = null
    fun getIconId(context: Context): Int? {
        if (iconId != null) return iconId
        if (iconName == null) return null

        iconId = context.getDrawableIdByName(iconName)
        return iconId
    }

    override fun equals(other: Any?): Boolean {
        if (other is ActionModel)
            return other.action == this.action
        return false
    }

    override fun hashCode(): Int {
        return action.hashCode()
    }
}
