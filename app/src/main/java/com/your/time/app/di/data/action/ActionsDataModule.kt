package com.your.time.app.di.data.action

import com.your.time.app.data.database.ApplicationDatabase
import com.your.time.app.data.database.action.ActionRoomModel
import com.your.time.app.data.database.action.mappers.ActionToDomainMapper
import com.your.time.app.data.database.action.mappers.ActionToRoomMapper
import com.your.time.app.data.database.action.ActionsDao
import com.your.time.app.data.database.time_period.TimePeriodDao
import com.your.time.app.data.repository.ActionsRepositoryImpl
import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.TimePeriodsRepository
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractorImpl
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.mappers.Mapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActionsDataModule {

    @Singleton
    @Provides
    fun provideActionsDao(applicationDatabase: ApplicationDatabase): ActionsDao
        = applicationDatabase.getActionsDao()

    @Singleton
    @Provides
    fun provideToRoomMapper(): Mapper<ActionModel, ActionRoomModel>
            = ActionToRoomMapper()

    @Singleton
    @Provides
    fun provideToDomainMapper(): Mapper<ActionRoomModel, ActionModel>
            = ActionToDomainMapper()

    @Singleton
    @Provides
    fun provideActionsRepository(
            dao: ActionsDao,
            toRoomMapper: Mapper<ActionModel, ActionRoomModel>,
            toDomainMapper: Mapper<ActionRoomModel, ActionModel>
    ): ActionsRepository
            = ActionsRepositoryImpl(dao, toRoomMapper, toDomainMapper)

}
