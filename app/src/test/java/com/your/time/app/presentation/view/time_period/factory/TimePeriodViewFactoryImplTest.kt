package com.your.time.app.presentation.view.time_period.factory

import android.graphics.Color
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.util.time.TimeUtilImpl
import com.your.time.app.presentation.view.time_period.TimePeriodView
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class TimePeriodViewFactoryImplTest : Assert(){

    @Test
    fun testBuildWithOutTimePeriodModel() {
        val resourcesService = Mockito.mock(ResourcesService::class.java)
        val timeUtil = TimeUtilImpl(resourcesService)
        val factory = TimePeriodViewFactoryImpl(RuntimeEnvironment.application, timeUtil)
        val view = factory.build()
        assertEquals(TimePeriodView.DEFAULT_TIME_PERIOD, view.timePeriod)
    }

    @Test
    fun testBuildWithTimePeriodModel() {
        val resourcesService = Mockito.mock(ResourcesService::class.java)
        val timeUtil = TimeUtilImpl(resourcesService)
        val factory = TimePeriodViewFactoryImpl(RuntimeEnvironment.application, timeUtil)

        val action = ActionModel("any action", color = Color.BLUE)
        val period = TimePeriodModel(action, 1, 10)

        val view1 = factory
                .setupTimePeriod(period)
                .build()

        assertEquals(period, view1.timePeriod)

        val view2 = factory.build()
        assertEquals(TimePeriodView.DEFAULT_TIME_PERIOD, view2.timePeriod)
    }

}