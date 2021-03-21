package com.your.time.app.presentation.mark.adapter

import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.presentation.view.time_period.TimePeriodView

class MarkTimePeriodHolder(
        val view: TimePeriodView
) : RecyclerView.ViewHolder(view){

    fun bind(model: TimePeriodModel) {
        view.timePeriod = model
    }
}