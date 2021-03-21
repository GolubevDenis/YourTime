package com.your.time.app.presentation.stats.stats_usefulness.adapters

import android.support.v7.util.DiffUtil
import com.your.time.app.domain.model.UsefulnessOfDay

class DiffUsefulnessOfDayCallback(
        private val oldList: List<UsefulnessOfDay>,
        private val newList: List<UsefulnessOfDay>
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
        return old.fullDate == new.fullDate
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old == new
    }
}