package com.your.time.app.presentation.view.time_period.factory

import android.content.Context
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.view.time_period.TimePeriodView

class TimePeriodViewFactoryImpl(
        private val context: Context,
        private val timeUtil: TimeUtil
) : TimePeriodViewFactory {

    private var period: TimePeriodModel? = null

    override fun setupTimePeriod(period: TimePeriodModel): TimePeriodViewFactory {
        this.period = period
        return this
    }

    override fun build(): TimePeriodView {
        val view = TimePeriodView(context, timeUtil)
        this.period?.let { view.timePeriod = it }
        this.period = null
        return view
    }
}