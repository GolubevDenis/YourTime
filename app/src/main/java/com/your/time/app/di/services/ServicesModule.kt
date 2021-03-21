package com.your.time.app.di.services

import android.content.Context
import com.your.time.app.di.global.ApplicationContext
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.services.*
import com.your.time.app.services.ActionImagesServiceImpl
import com.your.time.app.services.action_icon_resources.ActionIconResourcesServiceImpl
import com.your.time.app.services.notification.NotificatorServiceImpl
import com.your.time.app.services.properties.PropertiesServiceImpl
import com.your.time.app.services.resources.ResourcesServiceImpl
import com.your.time.app.services.schedulers.SchedulersServiceImpl
import com.your.time.app.services.upload_first_actions.GetFirstActionsFromDeviceService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServicesModule {

    @Singleton
    @Provides
    fun provideUploadingFirstActionsService(
            actionFactory: ActionFactory,
            @ApplicationContext context: Context
    ): GetFirstActionsService
            = GetFirstActionsFromDeviceService(actionFactory, context)

    @Singleton
    @Provides
    fun provideResourcesService(
            @ApplicationContext context: Context
    ): ResourcesService
            = ResourcesServiceImpl(context)

    @Singleton
    @Provides
    fun provideSchedulersService(): SchedulersService
            = SchedulersServiceImpl()

    @Singleton
    @Provides
    fun provideNotificator(
            @ApplicationContext context: Context
    ): NotificatorService
            = NotificatorServiceImpl(context)

    @Singleton
    @Provides
    fun provideSettingService(
            @ApplicationContext context: Context
    ): PropertiesService
            = PropertiesServiceImpl(context)

    @Singleton
    @Provides
    fun provideActionIconResourcesService(): ActionIconResourcesService
            = ActionIconResourcesServiceImpl()

    @Singleton
    @Provides
    fun provideActionImagesService(): ActionImagesService
            = ActionImagesServiceImpl()
}