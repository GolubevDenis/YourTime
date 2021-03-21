package com.your.time.app.presentation.home.adapters.time_periods

import android.support.v7.util.DiffUtil
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.presentation.view.adapters.diff.DiffCallbackFactory

class DiffTimePeriodCallbackFactory : DiffCallbackFactory<TimePeriodModel> {

    override fun build(oldData: List<TimePeriodModel>, newData: List<TimePeriodModel>): DiffUtil.Callback {
        return DiffTimePeriodCallback(oldData, newData)
    }
}