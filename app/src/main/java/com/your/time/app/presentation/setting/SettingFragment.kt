package com.your.time.app.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.your.time.app.MyApplication
import com.your.time.app.databinding.FragmentSettingBinding
import com.your.time.app.di.presentation.setting.DaggerSettingComponent
import com.your.time.app.di.presentation.setting.SettingComponent
import com.your.time.app.presentation.setting.mvp.SettingPresenter
import com.your.time.app.presentation.setting.mvp.SettingView
import com.hannesdorfmann.mosby3.mvp.MvpFragment

class SettingFragment : MvpFragment<SettingView, SettingPresenter>(), SettingView {

    private lateinit var binder: FragmentSettingBinding

    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun createPresenter() = component.getPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fun initCheckNotifyMarking(){
            binder.isNotifyMarking.setOnCheckedChangeListener { _, isChecked ->
                presenter.onChangeCheckNotifyMarking(isChecked)
            }
        }

        fun initTimeDurationMarkingSpinner(){
            binder.timeMarkingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = binder.timeMarkingSpinner.selectedItem
                    val textTime = selectedItem.toString()
                    presenter.onSelectTimeMarking(textTime)
                }
            }
        }


        binder = FragmentSettingBinding.inflate(inflater, container, false)

        initCheckNotifyMarking()
        initTimeDurationMarkingSpinner()

        return binder.root
    }

    override fun setNotifyMarking(isNotify: Boolean, time: Int){
        binder.isNotifyMarking.isChecked = isNotify


        val pos = when(time){
            15 -> 0
            30 -> 1
            1 * 60 -> 2
            2 * 60 -> 3
            3 * 60 -> 4
            4 * 60 -> 5
            5 * 60 -> 6
            12 * 60 -> 7
            24 * 60 -> 8
            else -> 0
        }
        binder.timeMarkingSpinner.setSelection(pos)
    }

    private val component: SettingComponent by lazy {
        DaggerSettingComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
    }
}
