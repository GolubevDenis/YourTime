package com.your.time.app.di.data.room

import android.arch.persistence.room.Room
import android.content.Context
import com.your.time.app.data.database.ApplicationDatabase
import com.your.time.app.di.global.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideApplicationDatabase(@ApplicationContext context: Context): ApplicationDatabase
        = Room
            .databaseBuilder(context, ApplicationDatabase::class.java, "ApplicationDatabase")
            .build()

}