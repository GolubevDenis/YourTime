package com.your.time.app.data.repository

import com.your.time.app.data.database.action.ActionRoomModel
import com.your.time.app.data.database.action.ActionsDao
import com.your.time.app.data.database.time_period.TimePeriodDao
import com.your.time.app.data.database.time_period.TimePeriodRoomModel
import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.mappers.Mapper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class ActionsRepositoryImpl(
        private val dao: ActionsDao,
        private val toRoomMapper: Mapper<ActionModel, ActionRoomModel>,
        private val toDomainMapper: Mapper<ActionRoomModel, ActionModel>
) : ActionsRepository {

    override fun getActionById(id: Long): Single<ActionModel> {
        return dao.selectActionById(id)
                .toSingle()
                .map { toDomainMapper.map(it) }
    }

    override fun getAllActions(): Single<List<ActionModel>> {
        return dao.selectAllActions()
                .firstOrError()
                .map {
                    toDomainMapper.map(it)
                }
    }

    override fun getAllActiveActions(isFirstOftenUsed: Boolean): Single<List<ActionModel>> {
        return dao.selectAllActiveActions()
                .firstOrError()
                .map {
                    toDomainMapper.map(it)
                }
    }

    override fun addAction(action: ActionModel): Completable {
        return Completable.fromAction {
            val id = dao.insertAction(toRoomMapper.map(action))
            action.id = id
        }
    }

    override fun updateAction(action: ActionModel): Completable {
        return Completable.fromAction {
            dao.update(toRoomMapper.map(action))
        }
    }

    override fun queryActiveActionByTitle(searchQueryText: String): Single<List<ActionModel>> {
        return dao.queryActiveActionsByTitle(searchQueryText)
                .firstOrError()
                .map { toDomainMapper.map(it) }
    }

    override fun markAsNotActiveActions(actions: List<ActionModel>): Completable {
        return Completable.create {
            try {
                val roomActions = toRoomMapper.map(actions)
                roomActions.forEach { action -> action.isActive = 0 }
                dao.update(roomActions)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}