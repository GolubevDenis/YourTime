package com.your.time.app.data.repository

import com.your.time.app.data.database.habits.HabitRoomModel
import com.your.time.app.data.database.habits.HabitsDao
import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.HabitsRepository
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.HabitModel
import com.your.time.app.domain.model.mappers.Mapper
import com.your.time.app.domain.model.mappers.MultiMapper
import io.reactivex.Completable
import io.reactivex.Single

class HabitsRepositoryImpl(
        private val dao: HabitsDao,
        private val toRoomMapper: Mapper<HabitModel, HabitRoomModel>,
        private val toDomainMapper: MultiMapper<HabitRoomModel, ActionModel, HabitModel>,
        private val actionsRepository: ActionsRepository
) : HabitsRepository {

    override fun getAllHabits(): Single<List<HabitModel>>{
//        return dao.selectAll()
//                .firstOrError()
//                .flatMapObservable { Observable.fromIterable(it) }
//                .map {
//                    val action = actionsRepository.getActionById(it.action).blockingGet()
//                    toDomainMapper.map(it, action)
//                }
//                .toList()
        return Single.error(Exception())
    }

    override fun addHabit(habit: HabitModel): Completable {
        return Completable.fromAction {
            val mapped = toRoomMapper.map(habit)
            dao.insert(mapped)
        }
    }

    override fun deleteHabit(habit: HabitModel): Completable {
        return Completable.fromAction {
            val mapped = toRoomMapper.map(habit)
            dao.delete(mapped)
        }
    }

}