package com.your.time.app.di.data.habit

import com.your.time.app.data.database.ApplicationDatabase
import com.your.time.app.data.database.habits.HabitRoomModel
import com.your.time.app.data.database.habits.HabitsDao
import com.your.time.app.data.database.habits.mappers.HabitToDomainMapper
import com.your.time.app.data.database.habits.mappers.HabitToRoomMapper
import com.your.time.app.data.repository.HabitsRepositoryImpl
import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.HabitsRepository
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.HabitModel
import com.your.time.app.domain.model.mappers.Mapper
import com.your.time.app.domain.model.mappers.MultiMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HabitDataModule {

    @Singleton
    @Provides
    fun provideHabitsDao(applicationDatabase: ApplicationDatabase): HabitsDao
            = applicationDatabase.getHabitsDao()

    @Singleton
    @Provides
    fun provideToRoomMapper(): Mapper<HabitModel, HabitRoomModel>
            = HabitToRoomMapper()

    @Singleton
    @Provides
    fun provideToDomainMapper(): MultiMapper<HabitRoomModel, ActionModel, HabitModel>
            = HabitToDomainMapper()

    @Singleton
    @Provides
    fun provideHabitsRepository(
            dao: HabitsDao,
            toRoomMapper: Mapper<HabitModel, HabitRoomModel>,
            toDomainMapper: MultiMapper<HabitRoomModel, ActionModel, HabitModel>,
            actionsRepository: ActionsRepository
    ): HabitsRepository
            = HabitsRepositoryImpl(dao, toRoomMapper, toDomainMapper, actionsRepository)

}
