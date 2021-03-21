package com.your.time.app.domain.interactors.setting


interface SettingInteractor {

    fun notifyMarkingEveryTime()
    fun notNotifyMarkingEveryTime()
    fun isNotifyMarkingEveryTime(): Boolean

    fun setNotifyTimerInMinutes(minutes: Int)
    fun getNotifyTimerInMinutes(): Int

}