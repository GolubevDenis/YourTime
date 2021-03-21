package com.your.time.app.presentation.mark.adapter

import android.app.Activity
import android.graphics.Color
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.view.time_period.TimePeriodView
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MarkTimePeriodHolderTest : Assert(){

    private lateinit var activity: Activity

    @Before
    fun init(){
        activity = Robolectric.setupActivity(Activity::class.java)
    }

    @Test
    fun testBind_SetTimePeriodIntoView(){
        val timeUtil = Mockito.mock(TimeUtil::class.java)
        val view = TimePeriodView(activity, timeUtil)
        val holder = MarkTimePeriodHolder(view)

        val action = ActionModel("any action", color = Color.BLUE)
        val timePeriod = TimePeriodModel(action, 0, 10)
        holder.bind(timePeriod)

        assertEquals(timePeriod, view.timePeriod)
    }
}