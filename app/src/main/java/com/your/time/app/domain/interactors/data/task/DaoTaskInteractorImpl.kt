package com.your.time.app.domain.interactors.data.task

import com.your.time.app.domain.data.repository.TasksRepository
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.Completable
import io.reactivex.Single

class DaoTaskInteractorImpl(
        private val tasksRepository: TasksRepository,
        private val timeUtil: TimeUtil
) : DaoTaskInteractor {

    override fun addTask(task: TaskModel): Completable {
        return tasksRepository.addTask(task)
    }

    override fun deleteTask(task: TaskModel): Completable {
        return tasksRepository.deleteTask(task)
    }

    override fun updateTask(task: TaskModel): Completable {
        return tasksRepository.updateTask(task)
    }

    override fun getAllTasks(): Single<List<TaskModel>> {
        return tasksRepository.getAllTasks()
                .map { it.sortedBy { it.timeInMinutes } }
    }

    override fun getAllFutureTasks(currentTimeInMinutes: Int): Single<List<TaskModel>> {
        return tasksRepository.getAllFutureTasks(currentTimeInMinutes)
                .map { it.sortedBy { it.timeInMinutes } }
    }

    override fun getTasksInInterval(startTimeInMinutes: Int, endTimeInMinutes: Int): Single<List<TaskModel>> {
        return tasksRepository.getTasksInInterval(startTimeInMinutes, endTimeInMinutes)
                .map { it.sortedBy { it.timeInMinutes } }
    }

    override fun getTasksForToday(): Single<List<TaskModel>> {
        val startOfDay = timeUtil.getStartOfThisDayInMinutes()
        val endOfDay = timeUtil.getEndOfThisDayInMinutes()
        return getTasksInInterval(startOfDay, endOfDay)
    }
}