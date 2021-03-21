package com.your.time.app.di.data.task

import com.your.time.app.data.database.ApplicationDatabase
import com.your.time.app.data.database.tasks.TaskRoomModel
import com.your.time.app.data.database.tasks.TasksDao
import com.your.time.app.data.database.tasks.mappers.TaskToDomainMapper
import com.your.time.app.data.database.tasks.mappers.TaskToRoomMapper
import com.your.time.app.data.repository.TasksRepositoryImpl
import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.TasksRepository
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.model.mappers.Mapper
import com.your.time.app.domain.model.mappers.MultiMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TaskDataModule {

    @Singleton
    @Provides
    fun provideTasksDao(applicationDatabase: ApplicationDatabase): TasksDao
            = applicationDatabase.getTasksDao()

    @Singleton
    @Provides
    fun provideToRoomMapper(): Mapper<TaskModel, TaskRoomModel>
            = TaskToRoomMapper()

    @Singleton
    @Provides
    fun provideToDomainMapper(): MultiMapper<TaskRoomModel, ActionModel, TaskModel>
            = TaskToDomainMapper()

    @Singleton
    @Provides
    fun provideTasksRepository(
            tasksDao: TasksDao,
            toRoomMapper: Mapper<TaskModel, TaskRoomModel>,
            toDomainMapper: MultiMapper<TaskRoomModel, ActionModel, TaskModel>,
            actionsRepository: ActionsRepository
    ): TasksRepository
            = TasksRepositoryImpl(tasksDao, actionsRepository, toDomainMapper, toRoomMapper)

}