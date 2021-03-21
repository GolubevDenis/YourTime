package com.your.time.app.domain.util.time

import android.graphics.Color
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.services.ResourcesService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import java.util.*

class TimeUtilImplTest : Assert(){

    private lateinit var util: TimeUtilImpl
    private lateinit var resourcesService: ResourcesService
    private val action = ActionModel("any action", color = Color.BLUE)

    @Before
    fun init(){
        resourcesService = Mockito.mock(ResourcesService::class.java)
        util = TimeUtilImpl(resourcesService)
    }

    @Test
    fun testGetCurrentTimeInSeconds() {
        assertEquals(System.currentTimeMillis() / 1000, util.getCurrentTimeInSeconds())
    }

    @Test
    fun testGetCurrentTimeInMinutes() {
        assertEquals((System.currentTimeMillis() / 1000 / 60).toInt(), util.getCurrentTimeInMinutes())
    }

    @Test
    fun testGetTextTimeInterval(){
        val START_TIME = 1000
        val END_TIME = 2000
        val timePeriod = TimePeriodModel(action, START_TIME, END_TIME)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = START_TIME * 1000L * 60L
        val since = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
        calendar.timeInMillis += timePeriod.getDurationInMinutes() * 1000L * 60L
        val until = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

        assertEquals("$since-$until", util.getTextTimeInterval(timePeriod))
    }

    @Test
    fun testGetTextTimeDuration_Minutes() {
        Mockito.`when`(resourcesService.getStringMinutes(Mockito.anyInt())).thenReturn("минут")

        val START_TIME = 1
        val END_TIME = 11
        val timePeriod = TimePeriodModel(action, START_TIME, END_TIME)

        assertEquals("10 минут", util.getTextTimeDuration(timePeriod))
    }

    @Test
    fun testGetTextTimeDuration_HoursAndMinutes() {
        Mockito.`when`(resourcesService.getStringMinutes(Mockito.anyInt())).thenReturn("минут")
        Mockito.`when`(resourcesService.getStringHours(Mockito.anyInt())).thenReturn("час")

        val START_TIME = 1
        val END_TIME = 71
        val timePeriod = TimePeriodModel(action, START_TIME, END_TIME)

        assertEquals("1 час, 10 минут", util.getTextTimeDuration(timePeriod))
    }

    @Test
    fun testGetTextTimeDuration_Hours() {
        Mockito.`when`(resourcesService.getStringHours(Mockito.anyInt())).thenReturn("час")

        val START_TIME = 1
        val END_TIME = 61
        val timePeriod = TimePeriodModel(action, START_TIME, END_TIME)

        assertEquals("1 час", util.getTextTimeDuration(timePeriod))
    }

    @Test
    fun getEndOfThisDayAndStartOfThisDay(){
        val minutesEnd = util.getEndOfThisDayInMinutes()
        val minutesStart = util.getStartOfThisDayInMinutes()

        assertEquals(24 * 60 - 1, minutesEnd - minutesStart)
    }

    @Test
    fun timeInMinutesByText(){
        Mockito.`when`(resourcesService.getMinutesReduction()).thenReturn("мин")
        Mockito.`when`(resourcesService.getHoursReduction()).thenReturn("час")

        val minutesIn15Minutes = util.timeInMinutesByText("15 минут")
        assertEquals(15, minutesIn15Minutes)

        val minutesIn30Minutes = util.timeInMinutesByText("30 минут")
        assertEquals(30, minutesIn30Minutes)

        val minutesIn1Hour = util.timeInMinutesByText("1 час")
        assertEquals(60, minutesIn1Hour)

        val minutesIncorrect = util.timeInMinutesByText("1 некоректное значение")
        assertEquals(-1, minutesIncorrect)
    }

    @Test
    fun getStartAndEndOfYesterdayInMinutes(){
        val yesterdayStart = util.getStartOfYesterdayInMinutes()
        val yesterdayEnd = util.getEndOfYesterdayInMinutes()
        assertEquals(24 * 60 - 1, yesterdayEnd - yesterdayStart)

        val todayStart = util.getStartOfThisDayInMinutes()
        val todayEnd = util.getEndOfThisDayInMinutes()
        assertEquals(todayStart, yesterdayStart + util.countOfMinutesInDay())
        assertEquals(todayEnd, yesterdayEnd + util.countOfMinutesInDay())
    }

    @Test
    fun getTimeInMinutesForDay7DaysAgo(){
        val sevenDaysAgo = util.getTimeInMinutesForDay7DaysAgo()
        val now = util.getCurrentTimeInMinutes()
        val minutesInWeek = 7 * 24 * 60 - 1
        assert((now - sevenDaysAgo) < minutesInWeek)
    }

    @Test
    fun getTimeInMinutesForDay30DaysAgo(){
        val thirtyDaysAgo = util.getTimeInMinutesForDay30DaysAgo()
        val now = util.getCurrentTimeInMinutes()
        val minutesInMonth = 30 * 24 * 60 - 1
        assert((now - thirtyDaysAgo) < minutesInMonth)
    }
}