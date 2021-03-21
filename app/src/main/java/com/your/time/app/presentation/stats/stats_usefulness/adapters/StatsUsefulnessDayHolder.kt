package com.your.time.app.presentation.stats.stats_usefulness.adapters

import android.support.v7.widget.RecyclerView
import com.your.time.app.databinding.DayUsefulnessItemBinding
import com.your.time.app.domain.model.UsefulnessOfDay

class StatsUsefulnessDayHolder(
        private val binder: DayUsefulnessItemBinding
) : RecyclerView.ViewHolder(binder.root) {

    fun bind(usefulnessOfDay: UsefulnessOfDay) {

        binder.dateOfDay.text = usefulnessOfDay.fullDate
        binder.usefulnessProgressBar.setUsefulness(usefulnessOfDay.usefulness)
    }
}