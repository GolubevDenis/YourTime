package com.your.time.app.di.data

import com.your.time.app.di.data.action.ActionsDataModule
import com.your.time.app.di.data.habit.HabitDataModule
import com.your.time.app.di.data.room.RoomModule
import com.your.time.app.di.data.task.TaskDataModule
import com.your.time.app.di.data.time_period.TimePeriodsDataModule
import dagger.Module

@Module(
        includes = [
                (ActionsDataModule::class),
                (TimePeriodsDataModule::class),
                (TaskDataModule::class),
                (HabitDataModule::class),
                (RoomModule::class)
        ]
)
class DataModule