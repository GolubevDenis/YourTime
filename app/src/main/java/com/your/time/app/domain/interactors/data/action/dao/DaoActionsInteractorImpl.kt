package com.your.time.app.domain.interactors.data.action.dao

import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.TimePeriodsRepository
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.model.actions.ActionModel
import io.reactivex.Completable
import io.reactivex.Single

class DaoActionsInteractorImpl(
        private val repository: ActionsRepository,
        private val timePeriodsInteractor: DaoTimePeriodInteractor,
        private val actionFactory: ActionFactory
) : DaoActionsInteractor {

    override fun getAllActions(): Single<List<ActionModel>> {
        return repository.getAllActions()
    }

    override fun getAllActiveActions(isFistOftenUsed: Boolean): Single<List<ActionModel>> {
        val notFilteredActions =  repository.getAllActiveActions(isFistOftenUsed)
        return filterServiceActions(notFilteredActions).map { actions ->
                    // most often used by last 7 days
                    if(isFistOftenUsed){
                        val lastPeriods = timePeriodsInteractor.getTimePeriodsForLast7Days().blockingGet()
                        return@map actions.sortedByDescending {
                            action -> lastPeriods.count { period -> period.action.id == action.id }
                        }
                    }
            actions
                }
    }

    private fun filterServiceActions(list: Single<List<ActionModel>>): Single<List<ActionModel>> {
        return list.map { actions ->
            val filteredActions = actions
                    .filter { !actionFactory.isServiceAction(it) }
            filteredActions

        }
    }

    override fun findActiveActionById(id: Long): Single<ActionModel> {
        return repository.getActionById(id)
    }

    override fun queryActiveActionByTitle(searchQueryText: String): Single<List<ActionModel>> {
        val notFilteredActions = repository.queryActiveActionByTitle(searchQueryText)
        return filterServiceActions(notFilteredActions)
    }

    override fun addAction(action: ActionModel): Completable {
        return repository.addAction(action)
    }

    override fun markAsNotActiveActions(actions: List<ActionModel>): Completable {
        return repository.markAsNotActiveActions(actions)
    }

    override fun updateAction(action: ActionModel): Completable {
        return repository.updateAction(action)
    }
}