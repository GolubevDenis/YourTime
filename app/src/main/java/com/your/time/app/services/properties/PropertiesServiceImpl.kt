package com.your.time.app.services.properties

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.your.time.app.domain.services.PropertiesService

class PropertiesServiceImpl(
        context: Context
) : PropertiesService {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE )

    companion object {
        private const val PREFERENCES_NAME = "setting_preferences"

        const val PROPERTY_NOTIFY_TIMER_DELAY = "notify_timer_delay"
        const val DEFAULT_VALUE_NOTIFY_TIMER_DELAY = 60

        const val IS_FIRST_LAUNCH = "is_first_launch"
        const val DEFAULT_IS_FIRST_LAUNCH = true


        const val DEFAULT_IS_NEED_TO_NOTIFY_MARKING = false
        const val IS_NEED_TO_NOTIFY_MARKING = "is_need_to_notify_marking"
    }

    override fun isFirstLaunch(isFirstLaunch: Boolean) {
        preferences.edit().putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).apply()
    }

    override fun isFirstLaunch(): Boolean {
        return preferences.getBoolean(IS_FIRST_LAUNCH, DEFAULT_IS_FIRST_LAUNCH)
    }

    override fun setNotifyingTimeBorder(minutes: Int) {
        preferences.edit().putInt(PROPERTY_NOTIFY_TIMER_DELAY, minutes).apply()
    }

    override fun getNotifyingTimeBorder() : Int {
        return preferences.getInt(PROPERTY_NOTIFY_TIMER_DELAY, DEFAULT_VALUE_NOTIFY_TIMER_DELAY)
    }

    override fun setNeedToNotifyMarking(isNeed: Boolean) {
        preferences.edit().putBoolean(IS_NEED_TO_NOTIFY_MARKING, isNeed).apply()
    }

    override fun isNeedToNotifyMarking(): Boolean {
        return preferences.getBoolean(IS_NEED_TO_NOTIFY_MARKING, DEFAULT_IS_NEED_TO_NOTIFY_MARKING)
    }
}