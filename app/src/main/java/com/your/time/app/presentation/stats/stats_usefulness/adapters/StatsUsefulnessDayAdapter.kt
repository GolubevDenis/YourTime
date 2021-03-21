package com.your.time.app.presentation.stats.stats_usefulness.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.your.time.app.databinding.DayUsefulnessItemBinding
import com.your.time.app.databinding.StatsTimePeriodListItemBinding
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.UsefulnessOfDay
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.home.adapters.time_periods.DiffTimePeriodCallbackFactory
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

class StatsUsefulnessDayAdapter(
        private val inflater: LayoutInflater,
        private val itemsList: ListForAdapter<UsefulnessOfDay, StatsUsefulnessDayHolder>,
        diffUsefulnessOfDayCallbackFactory: DiffUsefulnessOfDayCallbackFactory
) : RecyclerView.Adapter<StatsUsefulnessDayHolder>() {

    init {
        itemsList.setupDiffCallbackFactory(diffUsefulnessOfDayCallbackFactory)
        itemsList.setupAdapter(this)
    }

    override fun getItemCount() = itemsList.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsUsefulnessDayHolder {
        val binder = DayUsefulnessItemBinding.inflate(inflater, parent, false)
        return StatsUsefulnessDayHolder(binder)
    }

    override fun onBindViewHolder(holder: StatsUsefulnessDayHolder, position: Int) {
        val item = itemsList.getItems()[position]
        holder.bind(item)
    }
}