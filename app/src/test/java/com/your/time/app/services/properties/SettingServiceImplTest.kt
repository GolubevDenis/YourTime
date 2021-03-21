package com.your.time.app.services.properties

import android.content.Context
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SettingServiceImplTest {

    private lateinit var serviceImpl: PropertiesServiceImpl
    private lateinit var context: Context

    @Before
    fun init() {
        context = RuntimeEnvironment.application
        serviceImpl = PropertiesServiceImpl(context)
    }

    @Test
    fun setNotifyTimerInMinutes_ReturnsDefaultValue() {
        val minutes = serviceImpl.getNotifyingTimeBorder()
        assertEquals(PropertiesServiceImpl.DEFAULT_VALUE_NOTIFY_TIMER_DELAY, minutes)
    }

    @Test
    fun getNotifyTimerInMinutes() {
        serviceImpl.setNotifyingTimeBorder(100)
        val minutes = serviceImpl.getNotifyingTimeBorder()
        assertEquals(100, minutes)

        //write default value again
        serviceImpl.setNotifyingTimeBorder(PropertiesServiceImpl.DEFAULT_VALUE_NOTIFY_TIMER_DELAY)
    }

}