package com.your.time.app.di.presentation.setting

import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.setting.SettingInteractor
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.setting.mvp.SettingPresenter
import com.your.time.app.presentation.setting.mvp.SettingPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class SettingModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            settingInteractor: SettingInteractor,
            timeUtil: TimeUtil
    ): SettingPresenter
        = SettingPresenterImpl(settingInteractor, timeUtil)

}