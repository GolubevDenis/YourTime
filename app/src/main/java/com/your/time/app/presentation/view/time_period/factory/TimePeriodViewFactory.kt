package com.your.time.app.presentation.view.time_period.factory

import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.presentation.view.time_period.TimePeriodView

interface TimePeriodViewFactory {

    fun setupTimePeriod(period: TimePeriodModel): TimePeriodViewFactory
    fun build(): TimePeriodView
}