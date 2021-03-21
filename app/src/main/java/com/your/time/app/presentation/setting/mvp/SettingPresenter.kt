package com.your.time.app.presentation.setting.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

abstract class SettingPresenter : MvpBasePresenter<SettingView>() {

    abstract fun onChangeCheckNotifyMarking(isCheck: Boolean)
    abstract fun onSelectTimeMarking(textTime: String)
}