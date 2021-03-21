package com.your.time.app.presentation.mvp.setting

import com.your.time.app.domain.interactors.setting.SettingInteractor
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.setting.mvp.SettingPresenter
import com.your.time.app.presentation.setting.mvp.SettingPresenterImpl
import org.junit.Test

import org.junit.Before
import org.mockito.Mockito

class SettingPresenterImplTest {

    private lateinit var presenter: SettingPresenter
    private lateinit var interactor: SettingInteractor
    private lateinit var timeUtil: TimeUtil

    @Before
    fun init(){
        interactor = Mockito.mock(SettingInteractor::class.java)
        timeUtil = Mockito.mock(TimeUtil::class.java)

        presenter = SettingPresenterImpl(interactor, timeUtil)
    }

    @Test
    fun onChangeCheckNotifyMarking_IfTrue() {
        presenter.onChangeCheckNotifyMarking(true)
        Mockito.verify(interactor).notifyMarkingEveryTime()
    }

    @Test
    fun onChangeCheckNotifyMarking_IfFalse() {
        presenter.onChangeCheckNotifyMarking(false)
        Mockito.verify(interactor).notNotifyMarkingEveryTime()
    }

    @Test
    fun onSelectTimeMarking() {
        Mockito.`when`(timeUtil.timeInMinutesByText("15 минут")).thenReturn(15)
        presenter.onSelectTimeMarking("15 минут")
        Mockito.verify(interactor).setNotifyTimerInMinutes(15)
    }
}