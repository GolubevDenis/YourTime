package com.your.time.app.presentation.home.adapters.time_periods

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.your.time.app.databinding.TimePeriodListItemBinding
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

class HomeTimePeriodsAdapter(
        private val inflater: LayoutInflater,
        private val itemsList: ListForAdapter<TimePeriodModel, HomeTimePeriodHolder>,
        diffTimePeriodCallbackFactory: DiffTimePeriodCallbackFactory,
        private val timeUtil: TimeUtil
) : RecyclerView.Adapter<HomeTimePeriodHolder>() {

    init {
        itemsList.setupDiffCallbackFactory(diffTimePeriodCallbackFactory)
        itemsList.setupAdapter(this)
    }

    override fun getItemCount() = itemsList.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTimePeriodHolder {
        val binder = TimePeriodListItemBinding.inflate(inflater)
        return HomeTimePeriodHolder(binder, timeUtil)
    }

    override fun onBindViewHolder(holder: HomeTimePeriodHolder, position: Int) {
        val item = itemsList.getItems()[position]
        val isLast = position == itemCount - 1
        holder.bind(item, isLast, position)
    }
}