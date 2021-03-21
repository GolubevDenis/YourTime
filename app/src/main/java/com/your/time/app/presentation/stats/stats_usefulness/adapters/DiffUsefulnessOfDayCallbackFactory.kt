package com.your.time.app.presentation.stats.stats_usefulness.adapters

import android.support.v7.util.DiffUtil
import com.your.time.app.domain.model.UsefulnessOfDay
import com.your.time.app.presentation.view.adapters.diff.DiffCallbackFactory

class DiffUsefulnessOfDayCallbackFactory : DiffCallbackFactory<UsefulnessOfDay> {

    override fun build(oldData: List<UsefulnessOfDay>, newData: List<UsefulnessOfDay>): DiffUtil.Callback {
        return DiffUsefulnessOfDayCallback(oldData, newData)
    }
}