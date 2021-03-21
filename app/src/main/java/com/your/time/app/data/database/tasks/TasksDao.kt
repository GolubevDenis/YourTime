package com.your.time.app.data.database.tasks

import android.arch.persistence.room.*
import com.your.time.app.data.database.tasks.TaskRoomModel.Companion.TABLE_NAME
import com.your.time.app.data.database.tasks.TaskRoomModel.Companion.TIME_IN_MINUTES
import io.reactivex.Flowable

@Dao
interface TasksDao {

    @Insert
    fun insert(task: TaskRoomModel)

    @Update
    fun update(task: TaskRoomModel)

    @Delete
    fun delete(task: TaskRoomModel)

    @Query("SELECT * FROM ${TABLE_NAME};")
    fun selectAllTasks(): Flowable<List<TaskRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${TIME_IN_MINUTES} >= :startTimeInMinutes AND ${TIME_IN_MINUTES} <= :endTimeInMinutes;")
    fun selectTasksInInterval(startTimeInMinutes: Int, endTimeInMinutes: Int): Flowable<List<TaskRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${TIME_IN_MINUTES} >= :currentTime;")
    fun selectAllFutureTasks(currentTime: Int): Flowable<List<TaskRoomModel>>
}