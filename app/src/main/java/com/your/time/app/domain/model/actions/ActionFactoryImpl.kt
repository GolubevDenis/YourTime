package com.your.time.app.domain.model.actions

import android.content.Context
import com.your.time.app.R
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.presentation.explansions.color
import java.util.*

class ActionFactoryImpl(
        context: Context,
        private val resourcesService: ResourcesService
) : ActionFactory() {

    private val colors: IntArray =
            intArrayOf(
                    context.color(R.color.action_color_1),
                    context.color(R.color.action_color_2),
                    context.color(R.color.action_color_3),
                    context.color(R.color.action_color_4),
                    context.color(R.color.action_color_5),
                    context.color(R.color.action_color_6),
                    context.color(R.color.action_color_7),
                    context.color(R.color.action_color_8),
                    context.color(R.color.action_color_9),
                    context.color(R.color.action_color_10),
                    context.color(R.color.action_color_11),
                    context.color(R.color.action_color_12),
                    context.color(R.color.action_color_13),
                    context.color(R.color.action_color_14),
                    context.color(R.color.action_color_15),
                    context.color(R.color.action_color_16),
                    context.color(R.color.action_color_17),
                    context.color(R.color.action_color_18),
                    context.color(R.color.action_color_19),
                    context.color(R.color.action_color_20),
                    context.color(R.color.action_color_21),
                    context.color(R.color.action_color_22),
                    context.color(R.color.action_color_23),
                    context.color(R.color.action_color_24),
                    context.color(R.color.action_color_25),
                    context.color(R.color.action_color_26),
                    context.color(R.color.action_color_27),
                    context.color(R.color.action_color_28),
                    context.color(R.color.action_color_29),
                    context.color(R.color.action_color_30),
                    context.color(R.color.action_color_31),
                    context.color(R.color.action_color_32),
                    context.color(R.color.action_color_33),
                    context.color(R.color.action_color_34)
            )

    private val random = Random(System.currentTimeMillis())

    override fun getAllActionColors(): IntArray {
        return colors
    }

    override fun getRandomActionColor(): Int {
        return colors[random.nextInt(colors.size)]
    }

    override fun createUnmarkedAction(): ActionModel {
        return ActionModel(resourcesService.getTitleUnmarkedAction(), color = resourcesService.getColorUnmarkedAction(), usefulness = null)
    }

    companion object {
        const val CLEAR_ACTION_ID = 0L
    }

    private val serviceActionIds = listOf(
            CLEAR_ACTION_ID
    )

    override fun clearAction(): ActionModel {
        return ActionModel(resourcesService.getTitleClearAction(), color = resourcesService.getColorClearAction())
                .also { it.id = CLEAR_ACTION_ID }
    }

    override fun isServiceAction(action: ActionModel): Boolean {
        return serviceActionIds.contains(action.id)
    }
}