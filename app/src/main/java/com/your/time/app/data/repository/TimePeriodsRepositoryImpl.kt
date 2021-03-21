package com.your.time.app.data.repository

import com.your.time.app.data.database.time_period.TimePeriodDao
import com.your.time.app.data.database.time_period.TimePeriodRoomModel
import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.TimePeriodsRepository
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.mappers.Mapper
import com.your.time.app.domain.model.mappers.MultiMapper
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class TimePeriodsRepositoryImpl(
        private val dao: TimePeriodDao,
        private val toRoomMapper: Mapper<TimePeriodModel, TimePeriodRoomModel>,
        private val toDomainMapper: MultiMapper<TimePeriodRoomModel, ActionModel, TimePeriodModel>,
        private val actionsRepository: ActionsRepository
) : TimePeriodsRepository {

    override fun getLastTimePeriod(): Maybe<TimePeriodModel> {
        return dao.selectLastest()
                .flatMap { timePeriod ->
                    actionsRepository.getActionById(timePeriod.actionId)
                            .map { action ->
                                toDomainMapper.map(timePeriod, action)
                            }.toMaybe()
                }
    }

    override fun addTimePeriods(periods: List<TimePeriodModel>): Completable {
        return Completable.fromAction {
            val mappedModels = toRoomMapper.map(periods)
            dao.insert(*mappedModels.toTypedArray())
        }
    }

    override fun getAllTimePeriods(): Single<List<TimePeriodModel>> {
        return dao.selectAll()
                .firstOrError()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { timePeriod ->
                    actionsRepository.getActionById(timePeriod.actionId)
                            .map { action ->
                                toDomainMapper.map(timePeriod, action)
                            }.toObservable()
                }.toList()
    }

    override fun getTimePeriodsByStartTime(time: Long): Single<List<TimePeriodModel>> {
        return dao.selectByStartTime(time)
                .firstOrError()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { timePeriod ->
                    actionsRepository.getActionById(timePeriod.actionId)
                            .map { action ->
                                toDomainMapper.map(timePeriod, action)
                            }.toObservable()
                }.toList()
    }

    override fun getTimePeriodsByEndTime(time: Long): Single<List<TimePeriodModel>> {
        return dao.selectByEndTime(time)
                .firstOrError()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { timePeriod ->
                    actionsRepository.getActionById(timePeriod.actionId)
                            .map { action ->
                                toDomainMapper.map(timePeriod, action)
                            }.toObservable()
                }.toList()
    }

    override fun deleteTimePeriod(period: TimePeriodModel): Completable {
        return Completable.fromAction {
            val mapped = toRoomMapper.map(period)
            dao.delete(mapped)
        }
    }

    override fun updateTimePeriod(period: TimePeriodModel): Completable {
        return Completable.fromAction {
            val mapped = toRoomMapper.map(period)
            dao.update(mapped)
        }
    }

    override fun getTimePeriodsInInterval(startTime: Int, endTime: Int): Single<List<TimePeriodModel>> {
        return dao.selectInInterval(startTime, endTime)
                .firstOrError()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { timePeriod ->
                    actionsRepository.getActionById(timePeriod.actionId)
                            .map { action ->
                                toDomainMapper.map(timePeriod, action)
                            }.toObservable()
                }.toList()
    }

    override fun getLastPeriods(count: Int): Single<List<TimePeriodModel>> {
        return dao.selectLast(count)
                .firstOrError()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { timePeriod ->
                    actionsRepository.getActionById(timePeriod.actionId)
                            .map { action ->
                                toDomainMapper.map(timePeriod, action)
                            }.toObservable()
                }.toList()
    }
}