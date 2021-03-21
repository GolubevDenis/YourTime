package com.your.time.app.presentation.home.adapters.time_periods

import android.support.v7.widget.RecyclerView
import android.view.View
import com.your.time.app.R
import com.your.time.app.databinding.TimePeriodListItemBinding
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.explansions.color
import com.your.time.app.presentation.explansions.invisible
import com.your.time.app.presentation.explansions.visible

class HomeTimePeriodHolder(
        private val binder: TimePeriodListItemBinding,
        private val timeUtil: TimeUtil
) : RecyclerView.ViewHolder(binder.root) {

    private val context = binder.root.context
    private val colorForEven = context.color(android.R.color.white)
    private val colorForOdd = context.color(R.color.light_gray)

    fun bind(item: TimePeriodModel, isLast: Boolean, position: Int) {

        if(item.action.getIconId(context) != null){
            binder.icon.setImageResource(item.action.getIconId(context)!!)
            binder.icon.visible()
        }
        else binder.icon.invisible()

        binder.title.text = item.action.action
        binder.duration.text = timeUtil.getTextTimeDuration(item)
        binder.sinceUntil.text = timeUtil.getTextTimeInterval(item)

        val usefulnessIconId =  when(item.action.usefulness){

            ActionModel.Usefulness.VERY_USEFULLY -> R.drawable.action_very_usefully_active
            ActionModel.Usefulness.USEFULLY -> R.drawable.action_usefully_active
            ActionModel.Usefulness.HARMFULLY -> R.drawable.action_harmfully_active
            ActionModel.Usefulness.VERY_HARMFULLY -> R.drawable.action_very_harmfully_active
            else -> R.drawable.action_neutrally_active
        }
        binder.usefulness.setImageResource(usefulnessIconId)

        if(isLast)
            binder.divider1.visibility = View.INVISIBLE

        if(position % 2 == 0) binder.root.setBackgroundColor(colorForEven)
        else binder.root.setBackgroundColor(colorForOdd)
    }
}