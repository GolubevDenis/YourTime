package com.your.time.app.presentation.main

import android.support.design.widget.BottomNavigationView
import android.view.View
import com.your.time.app.R
import com.your.time.app.presentation.main.mvp.MainPresenter
import com.your.time.app.presentation.home.HomeFragment
import com.your.time.app.presentation.mark.MarkFragment
import com.your.time.app.presentation.stats.stats_usefulness.StatsUsefulnessFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest : Assert() {

    private lateinit var activity: MainActivity
    private lateinit var presenter: MainPresenter
    private lateinit var navigationView: BottomNavigationView

    @Before
    fun init(){
        activity = Robolectric.setupActivity(MainActivity::class.java)

        presenter = Mockito.mock(MainPresenter::class.java)
        activity.presenter = presenter

        navigationView = activity.findViewById<BottomNavigationView>(R.id.navigation)
    }

    @Test
    fun testFirstOnCreateLoadShowHomeFragment(){
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        assertTrue(getFragment(activity) is HomeFragment)
    }

//    @Test
//    fun testClickSetting(){
//        navigationView.findViewById<View>(R.id.navigation_setting).performClick()
//        assertTrue(getFragment(activity) is SettingFragment)
//    }

    @Test
    fun testClickHome(){
        navigationView.findViewById<View>(R.id.navigation_home).performClick()
        assertTrue(getFragment(activity) is HomeFragment)
    }

    @Test
    fun testClickStats(){
        navigationView.findViewById<View>(R.id.navigation_stats).performClick()
        assertTrue(getFragment(activity) is StatsUsefulnessFragment)
    }

    @Test
    fun testOnClickMark_showMarkFragment(){
        activity.onClickMark()
        assertTrue(getFragment(activity) is MarkFragment)
    }

    private fun getFragment(activity: MainActivity) = activity.supportFragmentManager.findFragmentById(R.id.container)


}