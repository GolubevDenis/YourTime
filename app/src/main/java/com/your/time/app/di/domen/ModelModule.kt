package com.your.time.app.di.domen

import android.content.Context
import com.your.time.app.di.global.ApplicationContext
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.model.actions.ActionFactoryImpl
import com.your.time.app.domain.model.time.TimeModel
import com.your.time.app.domain.model.time.TimeModelImpl
import com.your.time.app.domain.services.ResourcesService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModelModule {

    @Singleton
    @Provides
    fun provideTimeModule(): TimeModel = TimeModelImpl()

    @Singleton
    @Provides
    fun provideActionFactory(
            @ApplicationContext context: Context,
            resourcesService: ResourcesService
    ): ActionFactory
            = ActionFactoryImpl(context, resourcesService)
}