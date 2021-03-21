package com.your.time.app.domain.interactors.data.action.dao

import com.your.time.app.domain.model.actions.ActionModel
import io.reactivex.Completable
import io.reactivex.Single


interface DaoActionsInteractor {
    fun getAllActions(): Single<List<ActionModel>>
    fun addAction(action: ActionModel): Completable
    fun findActiveActionById(id: Long): Single<ActionModel>
    fun queryActiveActionByTitle(searchQueryText: String): Single<List<ActionModel>>
    fun markAsNotActiveActions(actions: List<ActionModel>): Completable
    fun getAllActiveActions(isFistOftenUsed: Boolean): Single<List<ActionModel>>
    fun updateAction(action: ActionModel): Completable
}