package com.your.time.app.presentation.mark.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.presentation.view.adapters.list.ListForAdapter
import com.your.time.app.presentation.view.time_period.TimePeriodView
import com.your.time.app.presentation.view.time_period.factory.TimePeriodViewFactory


class MarkTimePeriodsAdapter(
        val listItems: ListForAdapter<TimePeriodModel, MarkTimePeriodHolder>,
        private val viewFactory: TimePeriodViewFactory

) : RecyclerView.Adapter<MarkTimePeriodHolder>() {

    init {
        listItems.setupAdapter(this)
    }

    var onClickCloseListener: TimePeriodView.OnCloseClickListener? = null

    override fun getItemCount(): Int = listItems.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkTimePeriodHolder {
        return MarkTimePeriodHolder(viewFactory.build())
                .also { it.view.onCloseClickListener = onClickCloseListener }
    }

    override fun onBindViewHolder(holder: MarkTimePeriodHolder, position: Int) {
        val model = listItems.getItems()[position]
        holder.bind(model)
    }
}