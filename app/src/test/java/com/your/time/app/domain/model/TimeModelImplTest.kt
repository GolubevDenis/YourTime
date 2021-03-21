package com.your.time.app.domain.model

import android.graphics.Color
import com.your.time.app.assertException
import com.your.time.app.assertNotException
import com.your.time.app.domain.exceptions.TimeBoundException
import com.your.time.app.domain.model.time.TimeModelImpl
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.exceptions.TimePeriodConflictsException
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class TimeModelImplTest : Assert(){

    @Test
    fun testGetTimePeriodModel(){
        val time = TimeModelImpl()
        val timePeriod = createTimePeriodModel(1,2)
        time.putPeriod(timePeriod)
        assertEquals("Put or got ${TimePeriodModel::class.java.simpleName} incorrectly", timePeriod, time.getTimePeriod(0))
    }

    @Test
    fun testPutAndGetTimePeriodModels(){
        val time = TimeModelImpl()
        time.putPeriods(
                listOf(
                        createTimePeriodModel(1,2),
                        createTimePeriodModel(3,4)
                )
        )
        assertEquals("Put or got ${TimePeriodModel::class.java.simpleName}'s incorrectly", 2, time.getTimePeriods().size)
    }

    @Test
    fun testPutTimePeriodModelsConflicts(){

        var time = TimeModelImpl()
        assertException({
            time.putPeriods(
                    listOf(
                            createTimePeriodModel(1,2),
                            createTimePeriodModel(2,5),
                            createTimePeriodModel(4,6)
                    )
            )
        }, TimePeriodConflictsException::class, "These periods must conflict")

        time = TimeModelImpl()
        assertNotException({
            time.putPeriods(
                    listOf(
                            createTimePeriodModel(1,3),
                            createTimePeriodModel(3,5),
                            createTimePeriodModel(5,7)
                    )
            )
        }, "These periods must not conflict")
    }

    @Test
    fun testGetDuration(){
        val time = TimeModelImpl()
        time.putPeriods(
                listOf(
                        createTimePeriodModel(2,3),
                        createTimePeriodModel(3,4),
                        createTimePeriodModel(4,5)
                )
        )
        assertEquals(5 - 2, time.getDuration())
    }

    @Test
    fun testGetMinStartTime(){
        val time = TimeModelImpl()
        assertEquals(0, time.getMinStartTime())
        time.putPeriods(
                listOf(
                        createTimePeriodModel(2,3),
                        createTimePeriodModel(4,5)
                )
        )
        assertEquals(2, time.getMinStartTime())
    }

    @Test
    fun testGetTMaxEndTime(){
        val time = TimeModelImpl()
        assertEquals(0, time.getMaxEndTime())
        time.putPeriods(
                listOf(
                        createTimePeriodModel(2,3),
                        createTimePeriodModel(4,5)
                )
        )
        assertEquals(5, time.getMaxEndTime())
    }

    @Test
    fun testClear(){
        val time = TimeModelImpl()
        time.putPeriods(
                listOf(
                        createTimePeriodModel(2,3),
                        createTimePeriodModel(4,5)
                )
        )
        time.clear()
        assertEquals(0, time.getTimePeriods().size)
    }

    @Test
    fun testDelete(){
        val time = TimeModelImpl()
        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(4,5)
        time.putPeriods(listOf(timePeriod1, timePeriod2))
        time.deletePeriod(timePeriod1)
        assertEquals(1, time.getTimePeriods().size)
        assertEquals(timePeriod2, time.getTimePeriods()[0])
    }

    @Test
    fun testVacuumWithOutStartTimeBound(){
        val time = TimeModelImpl()
        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)
        val timePeriod3 = createTimePeriodModel(4,5)
        val timePeriod4 = createTimePeriodModel(5,6)
        time.putPeriods(listOf(timePeriod1, timePeriod2, timePeriod3, timePeriod4))
        time.deletePeriod(timePeriod2)
        time.vacuum()
        assertEquals(3, timePeriod3.startTimeMinutes)
        assertEquals(4, timePeriod3.endTimeMinutes)

        assertEquals(4, timePeriod4.startTimeMinutes)
        assertEquals(5, timePeriod4.endTimeMinutes)
    }

    @Test
    fun testVacuumWithStartTimeBound(){
        val time = TimeModelImpl()
        time.setStartTimeBound(0)
        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)
        val timePeriod3 = createTimePeriodModel(4,5)
        val timePeriod4 = createTimePeriodModel(5,6)
        time.putPeriods(listOf(timePeriod1, timePeriod2, timePeriod3, timePeriod4))
        time.deletePeriod(timePeriod2)
        time.vacuum()
        assertEquals(0, timePeriod1.startTimeMinutes)
        assertEquals(1, timePeriod1.endTimeMinutes)

        assertEquals(1, timePeriod3.startTimeMinutes)
        assertEquals(2, timePeriod3.endTimeMinutes)

        assertEquals(2, timePeriod4.startTimeMinutes)
        assertEquals(3, timePeriod4.endTimeMinutes)
    }

    @Test
    fun testSetStartTimeBound_SuccessIfValueMoreOrEqualsMinStartTimeOfTimePeriod(){
        val time = TimeModelImpl()
        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)
        time.putPeriods(listOf(timePeriod1, timePeriod2))

        assertNotException({
            time.setStartTimeBound(1)
            assertEquals(1, time.getStartTimeBound())
        })
    }

    @Test
    fun testSetStartTimeBound_ErrorIfValueLessThenMinStartTimeOfTimePeriod(){
        val time = TimeModelImpl()
        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)
        time.putPeriods(listOf(timePeriod1, timePeriod2))

        assertException({
            time.setStartTimeBound(3)
        }, TimeBoundException::class)
    }

    @Test
    fun testAddTimePeriodsAfterSettingStartTimeBound_SuccessIfMinStartTimeOfPeriodMoreOrEqualsStartTimeBound(){
        val time = TimeModelImpl()
        time.setStartTimeBound(1)

        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)

        assertNotException({
            time.putPeriods(listOf(timePeriod1, timePeriod2))
        })
        assertEquals(2, time.getTimePeriods().size)
    }

    @Test
    fun testAddTimePeriodsAfterSettingStartTimeBound_ErrorIfMinStartTimeOfPeriodLessThenStartTimeBound(){
        val time = TimeModelImpl()
        time.setStartTimeBound(3)

        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)

        assertException({
            time.putPeriods(listOf(timePeriod1, timePeriod2))
        }, TimeBoundException::class)
    }

    @Test
    fun testSetEndTimeBound_SuccessIfValueLessOrEqualsMaxEndTimeOfTimePeriod(){
        val time = TimeModelImpl()
        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)
        time.putPeriods(listOf(timePeriod1, timePeriod2))

        assertNotException({
            time.setEndTimeBound(4)
        })
        assertEquals(4, time.getEndTimeBound())
    }

    @Test
    fun testSetEndTimeBound_ErrorIfValueLessThenMaxStartTimeOfTimePeriod(){
        val time = TimeModelImpl()
        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)
        time.putPeriods(listOf(timePeriod1, timePeriod2))

        assertException({
            time.setEndTimeBound(3)
        }, TimeBoundException::class)
    }

    @Test
    fun testAddTimePeriodsAfterSettingEndTimeBound_SuccessIfMaxSEndTimeOfPeriodLessOrEqualsEndTimeBound(){
        val time = TimeModelImpl()
        time.setEndTimeBound(5)

        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)

        assertNotException({
            time.putPeriods(listOf(timePeriod1, timePeriod2))
        })
        assertEquals(2, time.getTimePeriods().size)

        time.deletePeriod(timePeriod2)
        val timePeriod3 = createTimePeriodModel(3, 5)

        assertNotException({
            time.putPeriods(listOf(timePeriod3))
        })
        assertEquals(2, time.getTimePeriods().size)
    }

    @Test
    fun testAddTimePeriodsAfterSettingEndTimeBound_ErrorIfMaxEndTimeOfPeriodMoreThenEndTimeBound(){
        val time = TimeModelImpl()
        time.setEndTimeBound(3)

        val timePeriod1 = createTimePeriodModel(2,3)
        val timePeriod2 = createTimePeriodModel(3,4)

        assertException({
            time.putPeriods(listOf(timePeriod1, timePeriod2))
        }, TimeBoundException::class)
    }
    
    @Test
    fun testPutTimePeriod_SubscribeOnTimePeriodChanging(){
        val time = TimeModelImpl()
        
        val timePeriod1 = createTimePeriodModel(1,5)
        time.putPeriod(timePeriod1)
        assertEquals(time, timePeriod1.getChangingListeners()[0])

        val timePeriod2 = createTimePeriodModel(5,10)
        time.putPeriod(timePeriod2)
        assertEquals(time, timePeriod2.getChangingListeners()[0])
    }

    @Test
    fun testDeleteTimePeriod_UnsubscribeFromTimePeriodChanging(){
        val time = TimeModelImpl()

        val timePeriod1 = createTimePeriodModel(1,5)
        val timePeriod2 = createTimePeriodModel(5,10)
        time.putPeriod(timePeriod1)
        time.putPeriod(timePeriod2)

        time.deletePeriod(timePeriod1)
        assertEquals(0, timePeriod1.getChangingListeners().size)

        time.deletePeriod(timePeriod1)
        assertEquals(0, timePeriod1.getChangingListeners().size)
    }

    @Test
    fun testChangeEndTimeOfTimePeriod_ChangeStartTimeOfNextTimePeriod_IfNecessary_IfEndTimeBoundIsNotSet(){
        val time = TimeModelImpl()
        val timePeriod1 = createTimePeriodModel(1,5)
        val timePeriod2 = createTimePeriodModel(5,10)
        time.putPeriods(listOf(timePeriod1, timePeriod2))

        timePeriod1.endTimeMinutes = 6
        timePeriod1.notifyDataChanged()
        assertEquals(6, timePeriod2.startTimeMinutes)
        assertEquals(11, timePeriod2.endTimeMinutes)

        timePeriod1.endTimeMinutes = 8
        timePeriod1.notifyDataChanged()
        assertEquals(8, timePeriod2.startTimeMinutes)
        assertEquals(13, timePeriod2.endTimeMinutes)
    }

    @Test
    fun testChangeEndTimeOfTimePeriod_ChangeStartTimeOfNextTimePeriod_IfNecessary_IfStartTimeBoundIsNotSet(){
        val time = TimeModelImpl()
        val timePeriod1 = createTimePeriodModel(5,10)
        val timePeriod2 = createTimePeriodModel(10,15)
        time.putPeriods(listOf(timePeriod1, timePeriod2))

        timePeriod2.startTimeMinutes = 9
        timePeriod2.notifyDataChanged()
        assertEquals(9, timePeriod1.endTimeMinutes)
        assertEquals(4, timePeriod1.startTimeMinutes)

        timePeriod2.startTimeMinutes = 7
        timePeriod2.notifyDataChanged()
        assertEquals(7, timePeriod1.endTimeMinutes)
        assertEquals(2, timePeriod1.startTimeMinutes)
    }

    @Test
    fun testChangeEndTimeOfTimePeriod_ChangeStartTimeOfNextTimePeriod_IfNecessary_IfStartTimeBoundIsSet(){
        val time = TimeModelImpl()
        time.setEndTimeBound(22)
        val timePeriod1 = createTimePeriodModel(1,5)
        val timePeriod2 = createTimePeriodModel(5,10)
        val timePeriod3 = createTimePeriodModel(10,15)
        val timePeriod4 = createTimePeriodModel(15,20)
        time.putPeriods(listOf(timePeriod1, timePeriod2, timePeriod3, timePeriod4))

        timePeriod1.endTimeMinutes = 6
        timePeriod1.notifyDataChanged()
        assertEquals(1, timePeriod1.startTimeMinutes)
        assertEquals(6, timePeriod1.endTimeMinutes)

        assertEquals(6, timePeriod2.startTimeMinutes)
        assertEquals(11, timePeriod2.endTimeMinutes)

        assertEquals(11, timePeriod3.startTimeMinutes)
        assertEquals(16, timePeriod3.endTimeMinutes)

        assertEquals(16, timePeriod4.startTimeMinutes)
        assertEquals(21, timePeriod4.endTimeMinutes)

        timePeriod1.endTimeMinutes = 10
        timePeriod1.notifyDataChanged()
        assertEquals(1, timePeriod1.startTimeMinutes)
        assertEquals(10, timePeriod1.endTimeMinutes)

        assertEquals(10, timePeriod2.startTimeMinutes)
        assertEquals(15, timePeriod2.endTimeMinutes)

        assertEquals(15, timePeriod3.startTimeMinutes)
        assertEquals(20, timePeriod3.endTimeMinutes)

        assertEquals(20, timePeriod4.startTimeMinutes)
        assertEquals(22, timePeriod4.endTimeMinutes)

        timePeriod1.endTimeMinutes = 15
        timePeriod1.notifyDataChanged()
        assertEquals(1, timePeriod1.startTimeMinutes)
        assertEquals(15, timePeriod1.endTimeMinutes)

        assertEquals(15, timePeriod2.startTimeMinutes)
        assertEquals(20, timePeriod2.endTimeMinutes)

        assertEquals(20, timePeriod3.startTimeMinutes)
        assertEquals(22, timePeriod3.endTimeMinutes)

        assertEquals(3, time.getTimePeriods().size)
    }

    @Test
    fun testChangeEndTimeOfTimePeriod_DeleteLastTimePeriod_IfDurationIsEqualsZero(){
        val time = TimeModelImpl()
        time.setEndTimeBound(60)
        val timePeriod1 = createTimePeriodModel(0,20)
        val timePeriod2 = createTimePeriodModel(20,40)
        val timePeriod3 = createTimePeriodModel(40,60)
        time.putPeriods(listOf(timePeriod1, timePeriod2, timePeriod3))

        timePeriod1.endTimeMinutes = 40
        timePeriod1.notifyDataChanged()
        assertEquals(0, timePeriod1.startTimeMinutes)
        assertEquals(40, timePeriod1.endTimeMinutes)

        assertEquals(40, timePeriod2.startTimeMinutes)
        assertEquals(60, timePeriod2.endTimeMinutes)

        assertEquals(2, time.getTimePeriods().size)
    }

    @Test
    fun testChangeEndTimeOfTimePeriod_CropLastTimePeriod_IfNecessary(){
        val time = TimeModelImpl()
        time.setEndTimeBound(60)
        val timePeriod1 = createTimePeriodModel(0,20)
        time.putPeriod(timePeriod1)

        timePeriod1.endTimeMinutes = 80
        timePeriod1.notifyDataChanged()
        assertEquals(60, timePeriod1.endTimeMinutes)
    }

    @Test
    fun testAddOnTimePeriodsChangedListener(){
        val time = TimeModelImpl()

        val listener1 = Mockito.mock(TimeModelImpl.OnTimePeriodsChangedListener::class.java)
        time.addOnTimePeriodsChangedListener(listener1)
        assertEquals(1, time.getOnTimePeriodsChangedListeners().size)

        val listener2 = Mockito.mock(TimeModelImpl.OnTimePeriodsChangedListener::class.java)
        time.addOnTimePeriodsChangedListener(listener2)
        assertEquals(2, time.getOnTimePeriodsChangedListeners().size)
    }

    @Test
    fun testAddOnTimePeriodsChangedListener_TheSameListenersWillNotAdd(){
        val time = TimeModelImpl()

        val listener1 = Mockito.mock(TimeModelImpl.OnTimePeriodsChangedListener::class.java)
        time.addOnTimePeriodsChangedListener(listener1)
        assertEquals(1, time.getOnTimePeriodsChangedListeners().size)

        time.addOnTimePeriodsChangedListener(listener1)
        assertEquals(1, time.getOnTimePeriodsChangedListeners().size)
    }

    @Test
    fun testDeleteOnTimePeriodsChangedListener(){
        val time = TimeModelImpl()

        val listener1 = Mockito.mock(TimeModelImpl.OnTimePeriodsChangedListener::class.java)
        val listener2 = Mockito.mock(TimeModelImpl.OnTimePeriodsChangedListener::class.java)
        time.addOnTimePeriodsChangedListener(listener1)
        time.addOnTimePeriodsChangedListener(listener2)

        time.removeOnTimePeriodsChangedListener(listener1)
        assertEquals(1, time.getOnTimePeriodsChangedListeners().size)

        time.removeOnTimePeriodsChangedListener(listener2)
        assertEquals(0, time.getOnTimePeriodsChangedListeners().size)
    }

    @Test
    fun testPutPeriod_SortPeriods(){
        val time = TimeModelImpl()

        val timePeriod1 = createTimePeriodModel(1,5)
        val timePeriod2 = createTimePeriodModel(5,10)
        val timePeriod3 = createTimePeriodModel(10,15)

        time.putPeriod(timePeriod1)
        time.putPeriod(timePeriod3)
        time.putPeriod(timePeriod2)

        assertEquals(timePeriod2, time.getTimePeriod(1))

    }

    private fun createTimePeriodModel(startTime: Int, endTime: Int): TimePeriodModel{
        val action = ActionModel("any action", color = Color.BLUE)
        return TimePeriodModel(action, startTime, endTime)
    }
}
