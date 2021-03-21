package com.your.time.app.presentation.main.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

abstract class MainPresenter : MvpBasePresenter<MainView>() {

    abstract fun getTextDate(): String

}