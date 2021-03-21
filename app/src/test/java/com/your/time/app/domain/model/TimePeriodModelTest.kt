package com.your.time.app.domain.model

import android.graphics.Color
import com.your.time.app.assertException
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.exceptions.ConstructTimePeriodModelException
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class TimePeriodModelTest : Assert() {

    private val START_TIME_IN_MINUTES: Int = (System.currentTimeMillis() / 1000 / 60).toInt()
    private val END_TIME_IN_MINUTES: Int = START_TIME_IN_MINUTES + 5
    private val ACTION = ActionModel("any action", color = Color.BLUE)

    @Test
    fun testCreate(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)
        assertEquals("Added or received incorrect action during creation", ACTION, timePeriod.action)
        assertEquals("Added or received incorrect startTimeInSeconds during creation", START_TIME_IN_MINUTES, timePeriod.startTimeMinutes)
        assertEquals("Added or received incorrect endTimeInSeconds during creation", END_TIME_IN_MINUTES, timePeriod.endTimeMinutes)
    }

    @Test
    fun testBoundariesPositive(){
        assertException({TimePeriodModel(ACTION, -1, 1)}, ConstructTimePeriodModelException::class)
        assertException({TimePeriodModel(ACTION, 0, -1)}, ConstructTimePeriodModelException::class)
    }

    @Test
    fun testTopMoreThenBottomBound(){
        assertException({TimePeriodModel(ACTION, 2, 1)}, ConstructTimePeriodModelException::class)
    }

    @Test
    fun testGetCorrectTimeDuration(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)
        assertEquals(END_TIME_IN_MINUTES - START_TIME_IN_MINUTES, timePeriod.getDurationInMinutes())
    }

    @Test
    fun testSetDurationInMinutesDeposeEndTime(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)
        timePeriod.startTimeMinutes = 5
        timePeriod.endTimeMinutes = 10
        timePeriod.setDurationInMinutesDeposeEndTime(15)
        assertEquals(15, timePeriod.getDurationInMinutes())
        assertEquals(20, timePeriod.endTimeMinutes)

        timePeriod.setDurationInMinutesDeposeEndTime(1)
        assertEquals(1, timePeriod.getDurationInMinutes())
        assertEquals(6, timePeriod.endTimeMinutes)
    }

    @Test
    fun testSetDurationInMinutesDeposeStartTime(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)
        timePeriod.startTimeMinutes = 15
        timePeriod.endTimeMinutes = 20
        timePeriod.setDurationInMinutesDeposeStartTime(15)
        assertEquals(15, timePeriod.getDurationInMinutes())
        assertEquals(5, timePeriod.startTimeMinutes)

        timePeriod.setDurationInMinutesDeposeStartTime(1)
        assertEquals(1, timePeriod.getDurationInMinutes())
        assertEquals(19, timePeriod.startTimeMinutes)
    }

    @Test
    fun testAddChangingListeners_AddNewListeners(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)

        val listener1 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)
        val listener2 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)

        timePeriod.addChangeListener(listener1)
        assertEquals(1, timePeriod.getChangingListeners().size)

        timePeriod.addChangeListener(listener2)
        assertEquals(2, timePeriod.getChangingListeners().size)
    }

    @Test
    fun testAddChangingListeners_DoNotAddTheSaeListeners(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)

        val listener1 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)

        timePeriod.addChangeListener(listener1)
        assertEquals(1, timePeriod.getChangingListeners().size)

        timePeriod.addChangeListener(listener1)
        assertEquals(1, timePeriod.getChangingListeners().size)
    }

    @Test
    fun testNotifyDataChanged_CallsCallbackInListeners(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)

        val listener1 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)
        val listener2 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)
        timePeriod.addChangeListener(listener1)
        timePeriod.addChangeListener(listener2)

        timePeriod.notifyDataChanged()
        Mockito.verify(listener1).onTimePeriodChanged(timePeriod)
        Mockito.verify(listener2).onTimePeriodChanged(timePeriod)
    }

    @Test
    fun testNotifyDataChangedBy_CallsCallbackInTheRightListeners(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)

        val listener1 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)
        val listener2 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)
        val listener3 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)
        timePeriod.addChangeListener(listener1)
        timePeriod.addChangeListener(listener2)
        timePeriod.addChangeListener(listener3)

        timePeriod.notifyDataChangedBy(listener2)
        Mockito.verify(listener1).onTimePeriodChanged(timePeriod)
        Mockito.verifyZeroInteractions(listener2)
        Mockito.verify(listener3).onTimePeriodChanged(timePeriod)
    }

    @Test
    fun testRemoveChangeListener(){
        val timePeriod = TimePeriodModel(ACTION, START_TIME_IN_MINUTES, END_TIME_IN_MINUTES)

        val listener1 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)
        val listener2 = Mockito.mock(TimePeriodModel.TimePeriodChangingListener::class.java)
        timePeriod.addChangeListener(listener1)
        timePeriod.addChangeListener(listener2)

        timePeriod.removeChangeListener(listener1)
        assertEquals(1, timePeriod.getChangingListeners().size)

        timePeriod.removeChangeListener(listener2)
        assertEquals(0, timePeriod.getChangingListeners().size)
    }

    @Test
    fun testPlusDurationInMinutes(){
        val timePeriod = TimePeriodModel(ACTION, 1, 5)
        val oldDuration = timePeriod.getDurationInMinutes()

        timePeriod.plusDurationInMinutes(5)
        assertEquals(oldDuration + 5, timePeriod.getDurationInMinutes())

        timePeriod.plusDurationInMinutes(10)
        assertEquals(oldDuration + 5 + 10, timePeriod.getDurationInMinutes())
    }

    @Test
    fun testMinusDurationInMinutes(){
        val timePeriod = TimePeriodModel(ACTION, 1, 60)
        val oldDuration = timePeriod.getDurationInMinutes()

        timePeriod.minusDurationInMinutes(5)
        assertEquals(oldDuration - 5, timePeriod.getDurationInMinutes())

        timePeriod.minusDurationInMinutes(10)
        assertEquals(oldDuration - 5 - 10, timePeriod.getDurationInMinutes())
    }
}