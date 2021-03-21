package com.your.time.app.services.upload_first_actions

import android.content.Context
import com.your.time.app.R
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.services.GetFirstActionsService
import com.your.time.app.presentation.explansions.color
import io.reactivex.Single

class GetFirstActionsFromDeviceService(
        private val factory: ActionFactory,
        private val context: Context
) : GetFirstActionsService {

    override fun getFirstActionsList(): Single<List<ActionModel>> {
        return Single.just(getList())
    }

    private fun getList(): List<ActionModel>{
        return listOf(
                factory.clearAction(),

                factory.createActionModel(context.getString(R.string.work), usefulness = ActionModel.Usefulness.USEFULLY, iconName = "ic_portfolio", color = context.color(R.color.action_color_4)),
                factory.createActionModel(context.getString(R.string.study), usefulness = ActionModel.Usefulness.VERY_USEFULLY, iconName = "ic_notebook", color = context.color(R.color.action_color_5)),
                factory.createActionModel(context.getString(R.string.sleep), usefulness = ActionModel.Usefulness.USEFULLY, iconName = "ic_sleep", color = context.color(R.color.action_color_16)),
                factory.createActionModel(context.getString(R.string.food), usefulness = ActionModel.Usefulness.NEUTRALLY, iconName = "ic_food", color = context.color(R.color.action_color_2)),
                factory.createActionModel(context.getString(R.string.sport), usefulness = ActionModel.Usefulness.VERY_USEFULLY, iconName = "ic_athletics", color = context.color(R.color.action_color_11)),
                factory.createActionModel(context.getString(R.string.entertaintment), usefulness = ActionModel.Usefulness.HARMFULLY, iconName = "ic_theater", color = context.color(R.color.action_color_22)),
                factory.createActionModel(context.getString(R.string.communication), usefulness = ActionModel.Usefulness.NEUTRALLY, iconName = "ic_friends", color = context.color(R.color.action_color_17)),
                factory.createActionModel(context.getString(R.string.shopping), usefulness = ActionModel.Usefulness.HARMFULLY, iconName = "ic_full_basket", color = context.color(R.color.action_color_32)),
                factory.createActionModel(context.getString(R.string.reading), usefulness = ActionModel.Usefulness.VERY_USEFULLY, iconName = "ic_books", color = context.color(R.color.action_color_33)),
                factory.createActionModel(context.getString(R.string.creativity), usefulness = ActionModel.Usefulness.VERY_USEFULLY, iconName = "ic_innovation", color = context.color(R.color.action_color_19)),
                factory.createActionModel(context.getString(R.string.work_at_home), usefulness = ActionModel.Usefulness.NEUTRALLY, iconName = "ic_cleaning", color = context.color(R.color.action_color_37)),
                factory.createActionModel(context.getString(R.string.movies), usefulness = ActionModel.Usefulness.NEUTRALLY, iconName = "ic_film_tape", color = context.color(R.color.action_color_29)),
                factory.createActionModel(context.getString(R.string.social_network), usefulness = ActionModel.Usefulness.VERY_HARMFULLY, iconName = "ic_communicate", color = context.color(R.color.action_color_8)),
                factory.createActionModel(context.getString(R.string.transport), usefulness = ActionModel.Usefulness.NEUTRALLY, iconName = "ic_car", color = context.color(R.color.action_color_1)),
                factory.createActionModel(context.getString(R.string.walking), usefulness = ActionModel.Usefulness.NEUTRALLY, iconName = "ic_park", color = context.color(R.color.action_color_10)),
                factory.createActionModel(context.getString(R.string.internet), usefulness = ActionModel.Usefulness.HARMFULLY, iconName = "ic_internet", color = context.color(R.color.action_color_34)),
                factory.createActionModel(context.getString(R.string.games), usefulness = ActionModel.Usefulness.HARMFULLY, iconName = "ic_controller", color = context.color(R.color.action_color_31)),
                factory.createActionModel(context.getString(R.string.pets), usefulness = ActionModel.Usefulness.NEUTRALLY, iconName = "ic_fish", color = context.color(R.color.action_color_37)),
                factory.createActionModel(context.getString(R.string.facebook), usefulness = ActionModel.Usefulness.VERY_HARMFULLY, iconName = "ic_facebook", color = context.color(R.color.action_color_35)),
                factory.createActionModel(context.getString(R.string.instagram), usefulness = ActionModel.Usefulness.VERY_HARMFULLY, iconName = "ic_instagram", color = context.color(R.color.action_color_36)),
                factory.createActionModel(context.getString(R.string.childred), usefulness = ActionModel.Usefulness.USEFULLY, iconName = "ic_kid", color = context.color(R.color.action_color_19)),
                factory.createActionModel(context.getString(R.string.report), usefulness = ActionModel.Usefulness.USEFULLY, iconName = "ic_report", color = context.color(R.color.action_color_31)),
                factory.createActionModel(context.getString(R.string.hygiene), usefulness = ActionModel.Usefulness.USEFULLY, iconName = "ic_hygiene", color = context.color(R.color.action_color_37)),
                factory.createActionModel(context.getString(R.string.self_care), usefulness = ActionModel.Usefulness.USEFULLY, iconName = "ic_comb", color = context.color(R.color.action_color_4)),
                factory.createActionModel(context.getString(R.string.nothing), usefulness = ActionModel.Usefulness.VERY_HARMFULLY, color = context.color(R.color.action_color_38))
        )
    }
}