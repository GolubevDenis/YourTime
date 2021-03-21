package com.your.time.app.domain.data.repository

import com.your.time.app.domain.model.actions.ActionModel
import io.reactivex.Completable
import io.reactivex.Single

interface ActionsRepository {
    fun getActionById(id: Long): Single<ActionModel>
    fun getAllActions(): Single<List<ActionModel>>
    fun addAction(action: ActionModel): Completable
    fun queryActiveActionByTitle(searchQueryText: String): Single<List<ActionModel>>
    fun markAsNotActiveActions(actions: List<ActionModel>): Completable
    fun getAllActiveActions(isFirstOftenUsed: Boolean): Single<List<ActionModel>>
    fun updateAction(action: ActionModel): Completable
}