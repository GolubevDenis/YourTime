package com.your.time.app.di.domen

import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.data.repository.HabitsRepository
import com.your.time.app.domain.data.repository.TasksRepository
import com.your.time.app.domain.data.repository.TimePeriodsRepository
import com.your.time.app.domain.interactors.createAction.CreateActionInteractor
import com.your.time.app.domain.interactors.createAction.CreateActionInteractorImpl
import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractorImpl
import com.your.time.app.domain.interactors.data.action.uploading.UploadingFirstActionsInteractor
import com.your.time.app.domain.interactors.data.action.uploading.UploadingFirstActionsInteractorImpl
import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractor
import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractorImpl
import com.your.time.app.domain.interactors.data.task.DaoTaskInteractor
import com.your.time.app.domain.interactors.data.task.DaoTaskInteractorImpl
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractorImpl
import com.your.time.app.domain.interactors.data.usefulness.UsefulnessInteractorImpl
import com.your.time.app.domain.interactors.data.usefulness.UsefulnessInteractor
import com.your.time.app.domain.interactors.home.HomeInteractor
import com.your.time.app.domain.interactors.home.HomeInteractorImpl
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.interactors.mark.MarkInteractorImpl
import com.your.time.app.domain.interactors.setting.SettingInteractor
import com.your.time.app.domain.interactors.setting.SettingInteractorImpl
import com.your.time.app.domain.interactors.stats.AcitivityInteractor
import com.your.time.app.domain.interactors.stats.AcitivityInteractorImpl
import com.your.time.app.domain.interactors.time.TimeInteractor
import com.your.time.app.domain.interactors.time.TimeInteractorImpl
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.model.time.TimeModel
import com.your.time.app.domain.services.*
import com.your.time.app.domain.util.time.TimeUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorsModule {

    @Singleton
    @Provides
    fun provideDaoTimePeriodInteractor(
            repository: TimePeriodsRepository,
            timeUtil: TimeUtil,
            actionFactory: ActionFactory,
            schedulersService: SchedulersService
    ): DaoTimePeriodInteractor
            = DaoTimePeriodInteractorImpl(repository, timeUtil, actionFactory, schedulersService)

    @Singleton
    @Provides
    fun provideLoadActionsInteractor(
            repository: ActionsRepository,
            timePeriodInteractor: DaoTimePeriodInteractor,
            actionFactory: ActionFactory
    ): DaoActionsInteractor
            = DaoActionsInteractorImpl(repository, timePeriodInteractor, actionFactory)

    @Singleton
    @Provides
    fun provideGetTimePeriodsForActionsInteractor(
            timeModel: TimeModel,
            timeUtil: TimeUtil,
            dao: DaoTimePeriodInteractor,
            schedulersService: SchedulersService,
            settingInteractor: SettingInteractor,
            propertiesService: PropertiesService,
            notificatorService: NotificatorService,
            actionFactory: ActionFactory
    ): MarkInteractor
            = MarkInteractorImpl(
                timeModel,
                timeUtil,
                dao,
                schedulersService,
                settingInteractor,
                propertiesService,
                notificatorService,
                actionFactory
            )

    @Singleton
    @Provides
    fun provideUploadingFirstActionsInteractor(
            service: GetFirstActionsService,
            repository: ActionsRepository,
            schedulersService: SchedulersService
    ): UploadingFirstActionsInteractor
            = UploadingFirstActionsInteractorImpl(service, repository, schedulersService)

    @Singleton
    @Provides
    fun provideSettingInteractor(
            settingService: PropertiesService
    ): SettingInteractor
            = SettingInteractorImpl(settingService)

    @Singleton
    @Provides
    fun provideDaoTaskInteractor(
            tasksRepository: TasksRepository,
            timeUtil: TimeUtil
    ): DaoTaskInteractor
            = DaoTaskInteractorImpl(tasksRepository, timeUtil)

    @Singleton
    @Provides
    fun provideDaoHabitInteractor(
            habitsRepository: HabitsRepository
    ): DaoHabitsInteractor
            = DaoHabitsInteractorImpl(habitsRepository)

    @Singleton
    @Provides
    fun provideTimeInteractor(
            timeUtil: TimeUtil
    ): TimeInteractor = TimeInteractorImpl(timeUtil)

    @Singleton
    @Provides
    fun provideHomeInteractor(
            daoTimePeriodInteractor: DaoTimePeriodInteractor,
            timeInteractor: TimeInteractor
    ): HomeInteractor
            = HomeInteractorImpl(daoTimePeriodInteractor, timeInteractor)

    @Singleton
    @Provides
    fun provideStatsInteractor(
            daoTimePeriodInteractor: DaoTimePeriodInteractor,
            timeInteractor: TimeInteractor
    ): AcitivityInteractor
            = AcitivityInteractorImpl(daoTimePeriodInteractor, timeInteractor)

    @Singleton
    @Provides
    fun provideCreateActionInteractor(
            actionImagesService: ActionImagesService
    ): CreateActionInteractor
            = CreateActionInteractorImpl(actionImagesService)

    @Singleton
    @Provides
    fun provideUsefulnessInteractor(
            timePeriodInteractor: DaoTimePeriodInteractor,
            timeInteractor: TimeInteractor
    ): UsefulnessInteractor
            = UsefulnessInteractorImpl(timePeriodInteractor, timeInteractor)
}