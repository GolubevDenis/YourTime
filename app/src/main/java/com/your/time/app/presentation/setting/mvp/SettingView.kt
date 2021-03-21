package com.your.time.app.presentation.setting.mvp

import com.hannesdorfmann.mosby3.mvp.MvpView

interface SettingView : MvpView {

    fun setNotifyMarking(isNotify: Boolean, time: Int)
}