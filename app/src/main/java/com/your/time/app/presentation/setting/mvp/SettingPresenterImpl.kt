package com.your.time.app.presentation.setting.mvp

import com.your.time.app.domain.interactors.setting.SettingInteractor
import com.your.time.app.domain.util.time.TimeUtil

class SettingPresenterImpl(
        private val settingInteractor: SettingInteractor,
        private val timeUtil: TimeUtil
) : SettingPresenter() {

    override fun attachView(view: SettingView) {
        super.attachView(view)

        setCurrentNotifyMarkingValues()
    }

    private fun setCurrentNotifyMarkingValues(){
        ifViewAttached {
            val isNotify = settingInteractor.isNotifyMarkingEveryTime()
            val notifyTime = settingInteractor.getNotifyTimerInMinutes()
            it.setNotifyMarking(isNotify, notifyTime)
        }
    }

    override fun onChangeCheckNotifyMarking(isCheck: Boolean) {
        if(isCheck){
            settingInteractor.notifyMarkingEveryTime()
        }else{
            settingInteractor.notNotifyMarkingEveryTime()
        }
    }

    override fun onSelectTimeMarking(textTime: String) {
        val markingNotificationDuration = timeUtil.timeInMinutesByText(textTime)
        settingInteractor.setNotifyTimerInMinutes(markingNotificationDuration)
    }
}