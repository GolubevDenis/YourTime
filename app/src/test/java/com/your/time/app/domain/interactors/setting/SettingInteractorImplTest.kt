package com.your.time.app.domain.interactors.setting

import com.your.time.app.domain.services.PropertiesService
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SettingInteractorImplTest {

    private lateinit var settingService: PropertiesService
    private lateinit var interactor: SettingInteractor

    @Before
    fun init(){
        settingService = Mockito.mock(PropertiesService::class.java)

        interactor = SettingInteractorImpl(settingService)
    }

    @Test
    fun setNotifyTimerInMinutes(){
        interactor.setNotifyTimerInMinutes(15)
        Mockito.verify(settingService).setNotifyingTimeBorder(15)
    }

    @Test
    fun getNotifyTimerInMinutes(){
        interactor.getNotifyTimerInMinutes()
        Mockito.verify(settingService).getNotifyingTimeBorder()
    }
}

