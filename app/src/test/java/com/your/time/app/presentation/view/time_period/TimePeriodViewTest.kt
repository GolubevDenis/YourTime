package com.your.time.app.presentation.view.time_period

import android.graphics.Color
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.your.time.app.R
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.domain.util.time.TimeUtilImpl
import com.your.time.app.presentation.view.action.ActionView
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class TimePeriodViewTest : Assert(){

    private val action = ActionModel("any action", color = Color.BLUE)
    private val timePeriod = TimePeriodModel(action, 1, 60)


    @Test
    fun testCreating(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())
        assertNotNull(view)
        assertEquals(TimePeriodView.DEFAULT_TIME_PERIOD, view.timePeriod)
    }

    @Test
    fun testCloseClicks_Click(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())
        val closeView = view.findViewById<ImageView>(R.id.close)

        val listener = Mockito.mock(TimePeriodView.OnCloseClickListener::class.java)
        view.onCloseClickListener = listener

        closeView.performClick()
        Mockito.verify(listener).onClickClose(view.timePeriod)
    }

    @Test
    fun testActionView_isStateActive(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val actionView = view.findViewById<ActionView>(R.id.action)
        assertTrue(actionView.isActiveState)
    }

    @Test
    fun testSetTimePeriodModel_ChangeTimePeriodText(){
        val timeUtil = getTimeUtilReal()
        val view = TimePeriodView(RuntimeEnvironment.application, timeUtil)

        val timePeriodText = view.findViewById<TextView>(R.id.timePeriodText)
        view.timePeriod = timePeriod
        assertEquals(timeUtil.getTextTimeInterval(timePeriod), timePeriodText.text)
    }

    @Test
    fun testSubscribeWhenSetTimePeriod(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 1, 60)

        assertFalse(timePeriod.getChangingListeners().contains(view))
        view.timePeriod = timePeriod
        assertTrue(timePeriod.getChangingListeners().contains(view))
    }

    @Test
    fun testUnsubscribeWhenSetNewTimePeriod(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod1 = TimePeriodModel(action, 1, 60)
        val timePeriod2 = TimePeriodModel(action, 1, 60)

        view.timePeriod = timePeriod1
        view.timePeriod = timePeriod2
        assertFalse(timePeriod1.getChangingListeners().contains(view))
    }

    @Test
    fun testClickMinus5_MinusDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 1, 900)
        val oldDuration = timePeriod.getDurationInMinutes()
        view.timePeriod = timePeriod

        val minus5M = view.findViewById<Button>(R.id.minus5M)
        minus5M.performClick()

        assertEquals(oldDuration - 5, timePeriod.getDurationInMinutes())
    }

    @Test
    fun testClickMinus20_MinusDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 0, 900)
        val oldDuration = timePeriod.getDurationInMinutes()
        view.timePeriod = timePeriod

        val minus20M = view.findViewById<Button>(R.id.minus20M)
        minus20M.performClick()

        assertEquals(oldDuration - 20, timePeriod.getDurationInMinutes())
    }

    @Test
    fun testClickMinus1H_MinusDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 0, 900)
        val oldDuration = timePeriod.getDurationInMinutes()
        view.timePeriod = timePeriod

        val minus1H = view.findViewById<Button>(R.id.minus1H)
        minus1H.performClick()

        assertEquals(oldDuration - 60, timePeriod.getDurationInMinutes())
    }

    @Test
    fun testClickMinus4H_MinusDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 0, 900)
        val oldDuration = timePeriod.getDurationInMinutes()
        view.timePeriod = timePeriod

        val minus4H = view.findViewById<Button>(R.id.minus4H)
        minus4H.performClick()

        assertEquals(oldDuration - (60 * 4), timePeriod.getDurationInMinutes())
    }



    @Test
    fun testClickPlus5_PlusDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 0, 900)
        val oldDuration = timePeriod.getDurationInMinutes()
        view.timePeriod = timePeriod

        val plus5M = view.findViewById<Button>(R.id.plus5M)
        plus5M.performClick()

        assertEquals(oldDuration + 5, timePeriod.getDurationInMinutes())
    }

    @Test
    fun testClickPlus5_ChangeTextDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilReal())

        val timePeriod = TimePeriodModel(action, 0, 20)
        view.timePeriod = timePeriod

        val textDuration = view.findViewById<TextView>(R.id.duration)
        val plus5M = view.findViewById<Button>(R.id.plus5M)

        val oldTextDuration = textDuration.text.toString()
        plus5M.performClick()
        val newTextDuration = textDuration.text.toString()

        assertNotEquals(oldTextDuration, newTextDuration)
    }

    @Test
    fun testClickPlus20_PlusDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 0, 900)
        val oldDuration = timePeriod.getDurationInMinutes()
        view.timePeriod = timePeriod

        val plus20M = view.findViewById<Button>(R.id.plus20M)
        plus20M.performClick()

        assertEquals(oldDuration + 20, timePeriod.getDurationInMinutes())
    }

    @Test
    fun testClickPlus1H_PlusDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 0, 900)
        val oldDuration = timePeriod.getDurationInMinutes()
        view.timePeriod = timePeriod

        val plus1H = view.findViewById<Button>(R.id.plus1H)
        plus1H.performClick()

        assertEquals(oldDuration + 60, timePeriod.getDurationInMinutes())
    }

    @Test
    fun testClickPlus4H_PlusDuration(){
        val view = TimePeriodView(RuntimeEnvironment.application, getTimeUtilMock())

        val timePeriod = TimePeriodModel(action, 0, 900)
        val oldDuration = timePeriod.getDurationInMinutes()
        view.timePeriod = timePeriod

        val plus4H = view.findViewById<Button>(R.id.plus4H)
        plus4H.performClick()

        assertEquals(oldDuration + (60 * 4), timePeriod.getDurationInMinutes())
    }

    private fun getTimeUtilMock() = Mockito.mock(TimeUtil::class.java)
    private fun getTimeUtilReal() = TimeUtilImpl(Mockito.mock(ResourcesService::class.java))
}