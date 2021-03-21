package com.your.time.app.presentation.habits.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.your.time.app.databinding.HabitListItemBinding
import com.your.time.app.domain.model.HabitModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase

class HabitsAdapter(
        private val listItems: ListForAdapterBase<HabitModel, HabitHolder>,
        private val layoutInflater: LayoutInflater,
        private val timeUtil: TimeUtil
) : RecyclerView.Adapter<HabitHolder>() {

    init {
        listItems.setupAdapter(this)
    }

    override fun getItemCount() = listItems.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val binder = HabitListItemBinding.inflate(layoutInflater)
        return HabitHolder(binder, timeUtil)
    }

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        val habit = listItems.getItems()[position]
        holder.bind(habit)
    }
}