package com.your.time.app.presentation.home.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.your.time.app.domain.model.actions.ActionModel

abstract class HomePresenter : MvpBasePresenter<HomeView>(){

    abstract fun onClickClearTime()
    abstract fun onClickFastMarkOk(actions: List<ActionModel>)

    abstract fun showFullDay()
    abstract fun showPM()
    abstract fun showAM()
}