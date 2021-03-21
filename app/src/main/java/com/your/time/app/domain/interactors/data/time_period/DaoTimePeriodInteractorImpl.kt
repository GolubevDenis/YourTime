package com.your.time.app.domain.interactors.data.time_period

import com.your.time.app.domain.data.repository.TimePeriodsRepository
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single


class DaoTimePeriodInteractorImpl(
        private val repository: TimePeriodsRepository,
        private val timeUtil: TimeUtil,
        private val actionFactory: ActionFactory,
        private val schedulersService: SchedulersService
) : DaoTimePeriodInteractor {


    override fun getLastestTimePeriod(): Maybe<TimePeriodModel> {
        return repository.getLastTimePeriod()
                .observeOn(schedulersService.io())
    }

    override fun getLastTimePeriods(count: Int): Single<List<TimePeriodModel>> {
        return repository.getLastPeriods(count)
                .observeOn(schedulersService.io())
    }

    override fun getTimePeriodByStartTime(time: Long): Single<List<TimePeriodModel>> {
        return repository.getTimePeriodsByStartTime(time)
                .observeOn(schedulersService.io())
    }

    override fun getTimePeriodByEndTime(time: Long): Single<List<TimePeriodModel>> {
        return repository.getTimePeriodsByEndTime(time)
                .observeOn(schedulersService.io())
    }

    override fun deleteTimePeriod(period: TimePeriodModel): Completable {
        return repository.deleteTimePeriod(period)
                .observeOn(schedulersService.io())

    }

    override fun updateTimePeriod(period: TimePeriodModel): Completable {
        return repository.updateTimePeriod(period)
                .observeOn(schedulersService.io())
    }

    override fun getAllTimePeriods(): Single<List<TimePeriodModel>> {
        return repository.getAllTimePeriods()
                .observeOn(schedulersService.io())
    }

    override fun addTimePeriods(periods: List<TimePeriodModel>): Completable {
        return repository.addTimePeriods(periods)
                .observeOn(schedulersService.io())
    }

    /**
     * Returns sorted list of time periods in the time interval.
     * If any period goes beyond the interval then it is cut off
     * */
    override fun getTimePeriodsForInterval(startTimeInMinutes: Int, endTimeInMinutes: Int): Single<List<TimePeriodModel>> {
        return repository.getTimePeriodsInInterval(startTimeInMinutes, endTimeInMinutes)
                .observeOn(schedulersService.io())
                .map { it.sortedBy { it.startTimeMinutes } }
                .map {
                    it.filter { it.startTimeMinutes <  startTimeInMinutes}
                            .forEach { it.startTimeMinutes = startTimeInMinutes }
                    it
                }.map {
                    it.filter { it.endTimeMinutes >  endTimeInMinutes}
                            .forEach { it.endTimeMinutes = endTimeInMinutes }
                    it
                }.map {
                    it.filter { it.getDurationInMinutes() > 0 }
                }
    }

    private fun getTimePeriodsByIntervalWithUnmarkedPeriods(start: Int, end: Int): Single<List<TimePeriodModel>>{
        fun createTimeUnmarkedTimePeriod(startTime: Int, endTime: Int): TimePeriodModel{
            val unmarkedActionModel = actionFactory.createUnmarkedAction()
            return TimePeriodModel(unmarkedActionModel, startTime, endTime)
        }
        fun createTimeUnmarkedTimePeriodForAllTheDay(): TimePeriodModel{
            val unmarkedActionModel = actionFactory.createUnmarkedAction()
            return TimePeriodModel(unmarkedActionModel, timeUtil.getStartOfThisDayInMinutes(), timeUtil.getEndOfThisDayInMinutes())
        }

        return getTimePeriodsForInterval(start, end)
                .map {

                    if(it.isEmpty()){
                        val unmarkedDay = createTimeUnmarkedTimePeriodForAllTheDay()
                        return@map arrayListOf(unmarkedDay)
                    }

                    val mutableList = ArrayList(it)

                    val minTime = it[0].startTimeMinutes
                    if(minTime > start){
                        val startOfDayPeriod = createTimeUnmarkedTimePeriod(start, minTime)
                        mutableList.add(0, startOfDayPeriod)
                    }

                    val endTime = it.last().endTimeMinutes
                    if(endTime < end){
                        val endOfDayPeriod = createTimeUnmarkedTimePeriod(endTime, end)
                        mutableList.add(endOfDayPeriod)
                    }

                    mutableList
                }
    }

    /**
     * If there are time in the start or in the end of the day
     * that aren't marked
     * then adds the default time periods for unmarked time
     * */
    override fun getTimePeriodsByTodayWithUnmarkedPeriods(): Single<List<TimePeriodModel>> {
        val startTimeOfThisDay = timeUtil.getStartOfThisDayInMinutes()
        val endTimeOfThisDay = timeUtil.getEndOfThisDayInMinutes()

        return getTimePeriodsByIntervalWithUnmarkedPeriods(startTimeOfThisDay, endTimeOfThisDay)
    }

    override fun getTimePeriodsByTodayPMWithUnmarkedPeriods(): Single<List<TimePeriodModel>> {
        val start = timeUtil.getNoonOfThisDayInMinutes()
        val end = timeUtil.getEndOfThisDayInMinutes()

        return getTimePeriodsByIntervalWithUnmarkedPeriods(start, end)
    }

    override fun getTimePeriodsByTodayAMWithUnmarkedPeriods(): Single<List<TimePeriodModel>> {
        val start = timeUtil.getStartOfThisDayInMinutes()
        val end = timeUtil.getNoonOfThisDayInMinutes()

        return getTimePeriodsByIntervalWithUnmarkedPeriods(start, end)
    }

    override fun getTimePeriodsForToday(): Single<List<TimePeriodModel>> {
        val start= timeUtil.getStartOfThisDayInMinutes()
        val end = timeUtil.getEndOfThisDayInMinutes()
        return getTimePeriodsForInterval(start, end)
    }

    override fun getTimePeriodsForYesterday(): Single<List<TimePeriodModel>> {
        val startTime = timeUtil.getStartOfYesterdayInMinutes()
        val endTime = timeUtil.getEndOfYesterdayInMinutes()
        return getTimePeriodsForInterval(startTime, endTime)
    }

    override fun getTimePeriodsForLast7Days(): Single<List<TimePeriodModel>> {
        val startTime = timeUtil.getTimeInMinutesForDay7DaysAgo()
        val endTime = timeUtil.getCurrentTimeInMinutes()
        return getTimePeriodsForInterval(startTime, endTime)
    }

    override fun getTimePeriodsForLast30Days(): Single<List<TimePeriodModel>> {
        val startTime = timeUtil.getTimeInMinutesForDay30DaysAgo()
        val endTime = timeUtil.getCurrentTimeInMinutes()
        return getTimePeriodsForInterval(startTime, endTime)
    }
}