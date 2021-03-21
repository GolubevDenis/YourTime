package com.your.time.app.presentation.stats.stats_activity.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.your.time.app.databinding.StatsTimePeriodListItemBinding
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.home.adapters.time_periods.DiffTimePeriodCallbackFactory
import com.your.time.app.presentation.stats.stats_usefulness.adapters.StatsUsefulnessDayHolder
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

class StatsActivityTimePeriodsAdapter(
        private val inflater: LayoutInflater,
        private val itemsList: ListForAdapter<TimePeriodModel, StatsActivityTimePeriodHolder>,
        diffTimePeriodCallbackFactory: DiffTimePeriodCallbackFactory,
        private val timeUtil: TimeUtil
) : RecyclerView.Adapter<StatsActivityTimePeriodHolder>() {

    init {
        itemsList.setupDiffCallbackFactory(diffTimePeriodCallbackFactory)
        itemsList.setupAdapter(this)
    }

    override fun getItemCount() = itemsList.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsActivityTimePeriodHolder {
        val binder = StatsTimePeriodListItemBinding.inflate(inflater)
        return StatsActivityTimePeriodHolder(binder, timeUtil)
    }

    override fun onBindViewHolder(holder: StatsActivityTimePeriodHolder, position: Int) {
        val item = itemsList.getItems()[position]
        val isLast = position == itemCount - 1
        holder.bind(item, isLast, position)
    }
}