package com.your.time.app.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.your.time.app.data.database.action.ActionRoomModel
import com.your.time.app.data.database.action.ActionsDao
import com.your.time.app.data.database.habits.HabitRoomModel
import com.your.time.app.data.database.habits.HabitsDao
import com.your.time.app.data.database.tasks.TaskRoomModel
import com.your.time.app.data.database.tasks.TasksDao
import com.your.time.app.data.database.time_period.TimePeriodDao
import com.your.time.app.data.database.time_period.TimePeriodRoomModel

@Database(
        entities = arrayOf(
                ActionRoomModel::class,
                TimePeriodRoomModel::class,
                TaskRoomModel::class,
                HabitRoomModel::class
        ),
        version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getActionsDao(): ActionsDao
    abstract fun geTimePeriodDao(): TimePeriodDao
    abstract fun getTasksDao(): TasksDao
    abstract fun getHabitsDao(): HabitsDao
}