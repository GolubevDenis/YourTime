package com.your.time.app.presentation.setting

import android.widget.CheckBox
import android.widget.Spinner
import com.your.time.app.R
import com.your.time.app.presentation.setting.mvp.SettingPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment

@RunWith(RobolectricTestRunner::class)
class SettingFragmentTest {

    private lateinit var fragment: SettingFragment
    private lateinit var presenter: SettingPresenter

    private lateinit var isNotify: CheckBox
    private lateinit var timeMarkingSpinner: Spinner

    @Before
    fun init(){
        fragment = SettingFragment.newInstance()
        startFragment(fragment)

        presenter = Mockito.mock(SettingPresenter::class.java)
        fragment.presenter = presenter

        isNotify = fragment.view!!.findViewById(R.id.is_notify_marking)
        timeMarkingSpinner = fragment.view!!.findViewById(R.id.time_marking_spinner)
    }

    @Test
    fun testIsCheckSetTrue_CallsPresenter(){
        val DEFAULT = false
        isNotify.isChecked = DEFAULT
        isNotify.isChecked = true
        Mockito.verify(presenter).onChangeCheckNotifyMarking(true)
    }

    @Test
    fun testIsCheckSetFalse_CallsPresenter(){
        val DEFAULT = true
        isNotify.isChecked = DEFAULT
        isNotify.isChecked = false
        Mockito.verify(presenter).onChangeCheckNotifyMarking(false)
    }

    @Test
    fun testTimeMarkingSpinner_CallsPresenter(){
        timeMarkingSpinner.setSelection(1)
        val textSelectedItem = timeMarkingSpinner.selectedItem.toString()
        Mockito.verify(presenter).onSelectTimeMarking(textSelectedItem)
    }
}