package com.your.time.app.domain.services

interface PropertiesService {

    fun setNotifyingTimeBorder(minutes: Int)
    fun getNotifyingTimeBorder(): Int

    fun setNeedToNotifyMarking(isNeed: Boolean)
    fun isNeedToNotifyMarking(): Boolean

    fun isFirstLaunch(isFirstLaunch: Boolean)
    fun isFirstLaunch(): Boolean
}