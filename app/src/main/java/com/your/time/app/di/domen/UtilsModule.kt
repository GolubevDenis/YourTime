package com.your.time.app.di.domen

import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.domain.util.time.TimeUtilImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideTimeUtil(
            resourcesService: ResourcesService
    ) : TimeUtil
            = TimeUtilImpl(resourcesService)
}