package com.your.time.app.domain.interactors.setting

import com.your.time.app.domain.services.PropertiesService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.services.timer.workers.MarkingNotificationJob

class SettingInteractorImpl(
        private val propertiesService: PropertiesService
) : SettingInteractor {

    override fun notifyMarkingEveryTime() {
        propertiesService.setNeedToNotifyMarking(true)
        MarkingNotificationJob.schedule()
    }

    override fun notNotifyMarkingEveryTime() {
        propertiesService.setNeedToNotifyMarking(false)
    }

    override fun isNotifyMarkingEveryTime(): Boolean {
        return propertiesService.isNeedToNotifyMarking()
    }

    override fun setNotifyTimerInMinutes(minutes: Int) {
        propertiesService.setNotifyingTimeBorder(minutes)
    }

    override fun getNotifyTimerInMinutes(): Int {
        return propertiesService.getNotifyingTimeBorder()
    }
}