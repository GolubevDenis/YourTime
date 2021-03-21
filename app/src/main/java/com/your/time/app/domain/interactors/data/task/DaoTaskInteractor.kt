package com.your.time.app.domain.interactors.data.task

import com.your.time.app.domain.model.TaskModel
import io.reactivex.Completable
import io.reactivex.Single

interface DaoTaskInteractor {

    fun addTask(task: TaskModel): Completable
    fun deleteTask(task: TaskModel): Completable
    fun updateTask(task: TaskModel): Completable
    fun getAllTasks(): Single<List<TaskModel>>
    fun getAllFutureTasks(currentTimeInMinutes: Int): Single<List<TaskModel>>
    fun getTasksInInterval(startTimeInMinutes: Int, endTimeInMinutes: Int): Single<List<TaskModel>>
    fun getTasksForToday(): Single<List<TaskModel>>
}