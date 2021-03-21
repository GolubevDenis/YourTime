package com.your.time.app.data.repository

import com.your.time.app.data.database.tasks.TaskRoomModel
import com.your.time.app.data.database.tasks.TasksDao
import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.TasksRepository
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.model.mappers.Mapper
import com.your.time.app.domain.model.mappers.MultiMapper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class TasksRepositoryImpl(
        private val tasksDao: TasksDao,
        private val actionsRepository: ActionsRepository,
        private val toDomainMapper: MultiMapper<TaskRoomModel, ActionModel, TaskModel>,
        private val toRoomMapper: Mapper<TaskModel, TaskRoomModel>
) : TasksRepository {

    override fun addTask(task: TaskModel): Completable {
        return Completable.fromAction {
            val mapped = toRoomMapper.map(task)
            tasksDao.insert(mapped)
        }
    }

    override fun deleteTask(task: TaskModel): Completable {
        return Completable.fromAction {
            val mapped = toRoomMapper.map(task)
            tasksDao.delete(mapped)
        }
    }

    override fun updateTask(task: TaskModel): Completable {
        return Completable.fromAction {
            val mapped = toRoomMapper.map(task)
            tasksDao.update(mapped)
        }
    }

    override fun getAllTasks(): Single<List<TaskModel>>{
        return tasksDao.selectAllTasks()
                .firstOrError()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { timePeriod ->
                    actionsRepository.getActionById(timePeriod.actionId)
                            .map { action ->
                                toDomainMapper.map(timePeriod, action)
                            }.toObservable()
                }.toList()
    }

    override fun getAllFutureTasks(currentTime: Int): Single<List<TaskModel>>{
        return tasksDao.selectAllFutureTasks(currentTime)
                .firstOrError()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { timePeriod ->
                    actionsRepository.getActionById(timePeriod.actionId)
                            .map { action ->
                                toDomainMapper.map(timePeriod, action)
                            }.toObservable()
                }.toList()
    }

    override fun getTasksInInterval(startTimeInMinutes: Int, endTimeInMinutes: Int): Single<List<TaskModel>> {
        return tasksDao.selectTasksInInterval(startTimeInMinutes, endTimeInMinutes)
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