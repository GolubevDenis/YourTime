package com.your.time.app.presentation.home.adapters.time_periods

import android.support.v7.util.DiffUtil
import com.your.time.app.domain.model.TimePeriodModel

class DiffTimePeriodCallback(
        private val oldList: List<TimePeriodModel>,
        private val newList: List<TimePeriodModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.startTimeMinutes == new.startTimeMinutes
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old == new
    }
}