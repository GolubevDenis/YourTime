package com.your.time.app.presentation.main.mvp

import com.your.time.app.domain.services.NotificatorService
import com.your.time.app.domain.services.PropertiesService
import com.your.time.app.domain.util.time.TimeUtil

class MainPresenterImpl(
        private val timeUtil: TimeUtil,
        private val propertiesService: PropertiesService,
        private val notificatorService: NotificatorService
) : MainPresenter() {

    override fun attachView(view: MainView) {
        super.attachView(view)

        if(propertiesService.isFirstLaunch()){
            propertiesService.isFirstLaunch(false)
            ifViewAttached { it.showUploadingActionsActivity() }
        }

        notificatorService.closeMarkTimeNotification()
    }

    override fun getTextDate() = timeUtil.getCurrentDateText()
}