package com.your.time.app.domain.util.time

import com.your.time.app.domain.model.TimePeriodModel

interface TimeUtil {

    fun getCurrentTimeInSeconds(): Long
    fun getCurrentTimeInMinutes(): Int
    fun getTextTimeInterval(period: TimePeriodModel): String
    fun getTextTimeInterval(startTimeInMinutes: Int, endTimeInMinutes: Int): String

    /**
     * Returns time in HH hours, MM minutes formate
     * */
    fun getTextTimeDuration(timePeriod: TimePeriodModel): String
    fun getTextTimeDuration(durationInMinutes: Int): String

    fun getEndOfThisDayInMinutes(): Int
    fun getStartOfThisDayInMinutes(): Int

    fun getStartOfYesterdayInMinutes(): Int
    fun getEndOfYesterdayInMinutes(): Int

    fun getTimeInMinutesForDay7DaysAgo(): Int
    fun getTimeInMinutesForDay30DaysAgo(): Int

    fun countOfMinutesInDay(): Int

    fun timeInMinutesByText(text: String): Int
    fun getCurrentTimeText(): String
    fun getCurrentDateText(): String
    fun getCurrentTimeInMilliseconds(): Long
    fun getTimeText(time: Long): String
    fun getDateText(time: Long): String

    fun getShortWeekday(fullDate: String): String
    fun getWeekday(fullDate: String): String
    fun getDateWithShortMonthOnly(fullDate: String): String

    fun tomorrow(): Long
    fun today(): Long
    fun getYear(selectedDate: Long): Int
    fun getMonth(selectedDate: Long): Int
    fun getDayOfMonth(selectedDate: Long): Int
    fun getTime(year: Int, month: Int, dayOfMonth: Int): Long
    fun addYear(selectedDate: Long): Long
    fun getTime(hours: Int, minutes: Int): Long
    fun getTimeText(hours: Int, minutes: Int): String
    fun millisecondsToMinutes(milliseconds: Long): Int
    fun hoursToMilliseconds(hours: Int): Long
    fun endOfDay(timeInMillis: Long): Long
    fun startOfDay(timeInMillis: Long): Long
    fun minutesToMilliseconds(minutes: Int): Long
    fun isAfterThisDay(timeInMinutes1: Int, timeInMinutes2: Int): Boolean
    fun countOfMillisInDay(): Long
    fun getNoonOfThisDayInMinutes(): Int
}