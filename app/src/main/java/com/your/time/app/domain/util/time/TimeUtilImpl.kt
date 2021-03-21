package com.your.time.app.domain.util.time

import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.expansions.extractDigits
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeUtilImpl(
        private val resourcesService: ResourcesService
) : TimeUtil {

    private val dateFormat = SimpleDateFormat("d MMMM - EEEE", Locale.getDefault())
    private val dateWithMonthFormat = SimpleDateFormat("d MMM", Locale.getDefault())
    private val weekdayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    private val shortWeekdayFormat = SimpleDateFormat("E", Locale.getDefault())

    override fun getCurrentTimeInMilliseconds(): Long = System.currentTimeMillis()
    override fun getCurrentTimeInSeconds(): Long = System.currentTimeMillis() / 1000
    override fun getCurrentTimeInMinutes(): Int = (getCurrentTimeInSeconds() / 60).toInt()

    override fun getShortWeekday(fullDate: String): String {
        val parsed = dateFormat.parse(fullDate)
        return shortWeekdayFormat.format(parsed)
    }

    override fun getDateWithShortMonthOnly(fullDate: String): String {
        val parsed = dateFormat.parse(fullDate)
        return dateWithMonthFormat.format(parsed)
    }

    override fun getWeekday(fullDate: String): String {
        val parsed = dateFormat.parse(fullDate)
        return weekdayFormat.format(parsed)
    }

    override fun getDateText(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return dateFormat.format(calendar.time)
    }

    override fun millisecondsToMinutes(milliseconds: Long): Int {
        return (milliseconds / 1000 / 60).toInt()
    }

    override fun minutesToMilliseconds(minutes: Int): Long {
        return minutes.toLong() * 1000 * 60
    }

    override fun hoursToMilliseconds(hours: Int): Long {
        return hours.toLong() * 1000 * 60 * 60
    }

    override fun getCurrentDateText() = getDateText(getCurrentTimeInMilliseconds())

    override fun getTimeText(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time

        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val hoursText = if(hours.toString().length == 1) "0$hours" else hours.toString()

        val minutes = calendar.get(Calendar.MINUTE)
        val minutesText = if(minutes.toString().length == 1) "0$minutes" else minutes.toString()

        return "$hoursText:$minutesText"
    }

    override fun getTimeText(hours: Int, minutes: Int): String {
        val time = getTime(hours, minutes)
        return getTimeText(time)
    }

    override fun getTime(hours: Int, minutes: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        return calendar.timeInMillis
    }

    override fun getTime(year: Int, month: Int, dayOfMonth: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return calendar.timeInMillis
    }

    override fun getCurrentTimeText() = getTimeText(getCurrentTimeInMilliseconds())

    override fun getTextTimeInterval(period: TimePeriodModel): String {
        val sinceTime = period.startTimeMinutes * 60 * 1000L
        val since = getTimeText(sinceTime)
        val duration = (period.getDurationInMinutes() - 1) * 60 * 1000L
        val until = getTimeText(sinceTime + duration)
        return "$since-$until"
    }

    override fun getTextTimeInterval(startTimeInMinutes: Int, endTimeInMinutes: Int): String {
        val since = getTimeText(minutesToMilliseconds(startTimeInMinutes))
        val until = getTimeText(minutesToMilliseconds(endTimeInMinutes))
        return "$since-$until"
    }

    override fun getTextTimeDuration(timeInMinutes: Int): String {

        return if(timeInMinutes < 60){
            "$timeInMinutes ${resourcesService.getStringMinutes(timeInMinutes)}"
        }else {
            val hours = timeInMinutes / 60
            val minutes = timeInMinutes % 60

            val hoursString = "$hours ${resourcesService.getStringHours(hours)}"
            if(minutes > 0) "$hoursString, $minutes ${resourcesService.getStringMinutes(minutes)}"
            else hoursString
        }
    }

    override fun getTextTimeDuration(timePeriod: TimePeriodModel): String {
        return this.getTextTimeDuration(timePeriod.getDurationInMinutes())
    }

    override fun timeInMinutesByText(text: String): Int {

        val firstMinutesSymbols = resourcesService.getMinutesReduction()
        if(text.contains(firstMinutesSymbols))
            return text.extractDigits()

        val firstHoursSymbols = resourcesService.getHoursReduction()
        if(text.contains(firstHoursSymbols))
            return text.extractDigits() * 60

        return -1
    }

    override fun tomorrow(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        return calendar.timeInMillis
    }

    override fun endOfDay(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    override fun startOfDay(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    override fun addYear(selectedDate: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDate
        calendar.add(Calendar.YEAR, 1)
        return calendar.timeInMillis
    }


    override fun today(): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = getCurrentTimeInMilliseconds()
        return calendar.timeInMillis
    }

    override fun getYear(selectedDate: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDate
        return calendar[Calendar.YEAR]
    }

    override fun getMonth(selectedDate: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDate
        return calendar[Calendar.MONTH] + 1
    }

    override fun getDayOfMonth(selectedDate: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDate
        return calendar[Calendar.DAY_OF_MONTH] + 1
    }

    override fun isAfterThisDay(timeInMinutes1: Int, timeInMinutes2: Int): Boolean {
        val milliseconds1 = minutesToMilliseconds(timeInMinutes1)
        val milliseconds2 = minutesToMilliseconds(timeInMinutes2)
        val endOfFirstDayMilliseconds1 = endOfDay(milliseconds1)
        val endOfFirstDayMilliseconds2 = endOfDay(milliseconds2)

        return endOfFirstDayMilliseconds1 < endOfFirstDayMilliseconds2
    }



    override fun getEndOfThisDayInMinutes(): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return TimeUnit.MILLISECONDS.toMinutes(calendar.time.time).toInt()
    }

    override fun getStartOfThisDayInMinutes(): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return TimeUnit.MILLISECONDS.toMinutes(calendar.time.time).toInt()
    }

    override fun getNoonOfThisDayInMinutes(): Int {
        return getStartOfThisDayInMinutes() + (countOfMinutesInDay() / 2)
    }

    override fun getStartOfYesterdayInMinutes(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis -= countOfMillisInDay()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return TimeUnit.MILLISECONDS.toMinutes(calendar.time.time).toInt()
    }

    override fun getEndOfYesterdayInMinutes(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis -= countOfMillisInDay()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return TimeUnit.MILLISECONDS.toMinutes(calendar.time.time).toInt()
    }

    override fun getTimeInMinutesForDay7DaysAgo(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = getCurrentTimeInMilliseconds() - (6 * countOfMillisInDay())
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return TimeUnit.MILLISECONDS.toMinutes(calendar.time.time).toInt()
    }

    override fun getTimeInMinutesForDay30DaysAgo(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = getCurrentTimeInMilliseconds() - (30 * countOfMillisInDay())
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return TimeUnit.MILLISECONDS.toMinutes(calendar.time.time).toInt()
    }

    override fun countOfMinutesInDay(): Int {
        return 1440
    }

    override fun countOfMillisInDay(): Long {
        return 86400000
    }
}