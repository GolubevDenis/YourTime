package com.your.time.app.domain.interactors.mark

import com.your.time.app.domain.exceptions.TooManyActionsForPeriodException
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.interactors.setting.SettingInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.time.TimeModel
import com.your.time.app.domain.model.time.TimeModelImpl
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.services.NotificatorService
import com.your.time.app.domain.services.PropertiesService
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class MarkInteractorImpl(
        private val time: TimeModel,
        private val timeUtil: TimeUtil,
        private val dao: DaoTimePeriodInteractor,
        private val schedulersService: SchedulersService,
        private val settingInteractor: SettingInteractor,
        private val propertiesService: PropertiesService,
        private val notificatorService: NotificatorService,
        private val actionFactory: ActionFactory
) : MarkInteractor {

    override fun subscribeOnTimePeriodsChanging(listener: TimeModelImpl.OnTimePeriodsChangedListener){
        time.addOnTimePeriodsChangedListener(listener)
    }

    override fun getActiveTimePeriods(): Single<List<TimePeriodModel>> = Single.just(time.getTimePeriods())

    override fun addTimePeriod(timePeriod: TimePeriodModel) {
        time.putPeriod(timePeriod)
    }

    override fun deleteTimePeriod(timePeriod: TimePeriodModel) {
        time.deletePeriod(timePeriod)
        time.vacuum()
    }

    override fun commitTimePeriods(){

        fun resetNotifications() {
            notificatorService.closeMarkTimeNotification()
        }

        val timePeriodsForCommit = time.getTimePeriods()
        dao.addTimePeriods(timePeriodsForCommit)
                .subscribeOn(schedulersService.io())
                .subscribe {
                    time.clear()
                    resetNotifications()
                }
    }

    override fun newTimePeriodsByActions(actions: List<ActionModel>): Completable {

        if(actions.isEmpty())
            return Completable.complete()

        return getStartTime()
                .subscribeOn(schedulersService.io())
                .flatMap { startTime ->

                    if(time.getTimePeriods().isEmpty()) time.setStartTimeBound(startTime)

                    time.setEndTimeBound(timeUtil.getCurrentTimeInMinutes())

                    val countActions = actions.size
                    val duration = timeUtil.getCurrentTimeInMinutes() - startTime

                    if(duration < countActions)
                        return@flatMap Single.error<List<TimePeriodModel>>(TooManyActionsForPeriodException(duration, countActions))

                    val step = duration / countActions
                    val residue = duration % countActions

                    val listTimePeriods = (0 until countActions).
                            map {
                                val localStartTime = startTime + (step * it)
                                val localEndTime = startTime + (step * (it + 1))
                                TimePeriodModel(actions[it], localStartTime, localEndTime)
                            }.mapIndexed { index, period ->
                                if(index + 1 == countActions)
                                    period.setDurationInMinutesDeposeEndTime(period.getDurationInMinutes() + residue)
                        period
                            }
                    Single.just(listTimePeriods)
        }
                .doOnSuccess { time.putPeriods(it) }
                .toCompletable()
    }

    override fun getUnmarkedTime(): Single<Int> {
        val now = timeUtil.getCurrentTimeInMinutes()

        return getStartTime()
                .map { lastMarkedTime -> now - lastMarkedTime }
    }

    private fun getStartTime(): Single<Int>{
        if(time.getMaxEndTime() != 0) return Single.just(time.getMaxEndTime())

        return dao.getLastestTimePeriod()
                .subscribeOn(schedulersService.io())
                .map {
                    it.endTimeMinutes
                }.switchIfEmpty(Maybe.just(getDefaultTime()))
                .toSingle()
    }

    private fun getDefaultTime() = timeUtil.getCurrentTimeInMinutes() - 60

    override fun clearTimePeriods() {
        time.clear()
    }

    override fun markAsCleared(): Completable {
        clearTimePeriods()
        val clearAction = actionFactory.clearAction()
        return newTimePeriodsByActions(listOf(clearAction))
                .observeOn(schedulersService.io())
                .doOnComplete { commitTimePeriods() }
    }

    override fun markAsCleared(minutesForClearing: Int): Completable {
        clearTimePeriods()
        val clearAction1 = actionFactory.clearAction()
        val clearAction2 = actionFactory.clearAction()
        return newTimePeriodsByActions(listOf(clearAction1, clearAction2))
                .observeOn(schedulersService.io())
                .doOnComplete {
                    val firstClear = time.getTimePeriods().first()
                    firstClear.setDurationInMinutesDeposeEndTime(minutesForClearing)
                    commitTimePeriods()
                }
    }

    override fun getDuration(): Int {
        return time.getDuration()
    }
}