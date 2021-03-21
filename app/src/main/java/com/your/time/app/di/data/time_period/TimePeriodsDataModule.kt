package com.your.time.app.di.data.time_period

import com.your.time.app.data.database.ApplicationDatabase
import com.your.time.app.data.database.time_period.TimePeriodDao
import com.your.time.app.data.database.time_period.TimePeriodRoomModel
import com.your.time.app.data.database.time_period.mappers.TimePeriodToDomainMapper
import com.your.time.app.data.database.time_period.mappers.TimePeriodToRoomMapper
import com.your.time.app.data.repository.TimePeriodsRepositoryImpl
import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.TimePeriodsRepository
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.mappers.Mapper
import com.your.time.app.domain.model.mappers.MultiMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TimePeriodsDataModule {

    @Singleton
    @Provides
    fun provideTimePeriodDao(applicationDatabase: ApplicationDatabase): TimePeriodDao
            = applicationDatabase.geTimePeriodDao()

    @Singleton
    @Provides
    fun provideToRoomMapper(): Mapper<TimePeriodModel, TimePeriodRoomModel>
            = TimePeriodToRoomMapper()

    @Singleton
    @Provides
    fun provideToDomainMapper(): MultiMapper<TimePeriodRoomModel, ActionModel, TimePeriodModel>
            = TimePeriodToDomainMapper()

    @Singleton
    @Provides
    fun provideTimePeriodRepository(
            timePeriodDao: TimePeriodDao,
            toRoomMapper: Mapper<TimePeriodModel, TimePeriodRoomModel>,
            toDomainMapper: MultiMapper<TimePeriodRoomModel, ActionModel, TimePeriodModel>,
            actionsRepository: ActionsRepository
    ): TimePeriodsRepository
            = TimePeriodsRepositoryImpl(timePeriodDao, toRoomMapper, toDomainMapper, actionsRepository)

}
