package com.your.time.app.di.global

import com.your.time.app.di.data.DataModule
import com.your.time.app.di.domen.InteractorsModule
import com.your.time.app.di.domen.ModelModule
import com.your.time.app.di.domen.UtilsModule
import com.your.time.app.di.domen.validators.EmailQualifier
import com.your.time.app.di.domen.validators.PasswordQualifier
import com.your.time.app.di.domen.validators.ValidatorsModule
import com.your.time.app.di.services.ServicesModule
import com.your.time.app.domain.interactors.createAction.CreateActionInteractor
import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.data.action.uploading.UploadingFirstActionsInteractor
import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractor
import com.your.time.app.domain.interactors.data.task.DaoTaskInteractor
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.interactors.data.usefulness.UsefulnessInteractor
import com.your.time.app.domain.interactors.home.HomeInteractor
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.interactors.setting.SettingInteractor
import com.your.time.app.domain.interactors.stats.AcitivityInteractor
import com.your.time.app.domain.interactors.time.TimeInteractor
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.services.*
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.domain.validation.DataValidator
import com.your.time.app.domain.validation.password.PasswordValidator
import com.your.time.app.services.timer.workers.MarkingNotificationJob
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ApplicationModule::class,
            ServicesModule::class,
            InteractorsModule::class,
            ValidatorsModule::class,
            DataModule::class,
            UtilsModule::class,
            ModelModule::class
        ]
)
interface ApplicationComponent {

    fun getLoadActionsInteractor(): DaoActionsInteractor
    fun getUploadingFirstActionsInteractor(): UploadingFirstActionsInteractor
    fun getGetTimePeriodsForActionsInteractor(): MarkInteractor
    fun getDaoTimePeriodInteractor(): DaoTimePeriodInteractor
    fun getSettingInteractor(): SettingInteractor
    fun getDaoTaskInteractor(): DaoTaskInteractor
    fun getDaoHabitInteractor(): DaoHabitsInteractor
    fun getStatsInteractor(): AcitivityInteractor
    fun getTimeInteractor(): TimeInteractor
    fun getHomeInteractor(): HomeInteractor
    fun getCreateActionInteractor(): CreateActionInteractor
    fun getUsefulnessInteractor(): UsefulnessInteractor

    fun getActionFactory(): ActionFactory

    fun getTimeUtil(): TimeUtil

    fun getResourcesService(): ResourcesService
    fun getSchedulersService(): SchedulersService
    fun getNotificationService(): NotificatorService
    fun getSettingService(): PropertiesService
    fun getActionIconResourcesService(): ActionIconResourcesService

    @EmailQualifier fun getEmailValidator(): DataValidator
    @PasswordQualifier fun getPasswordValidator(): PasswordValidator

    fun inject(markingNotificationJob: MarkingNotificationJob)
}