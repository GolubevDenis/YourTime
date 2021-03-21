package com.your.time.app.presentation.uploading_actions.mvp

import com.your.time.app.domain.interactors.data.action.uploading.UploadingFirstActionsInteractor
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.services.SchedulersService
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class UploadingActionsPresenterImpl(
        private val uploadingInteractor: UploadingFirstActionsInteractor,
        private val resourcesService: ResourcesService,
        private val schedulersService: SchedulersService
) : UploadingActionsPresenter() {

    override fun attachView(view: UploadingActionsView) {
        super.attachView(view)

        uploadingInteractor.uploadFirstActions()
                .observeOn(schedulersService.ui())
                .concatMap { i-> Observable.just(i).delay(400, TimeUnit.MILLISECONDS) }
                .subscribe({
                    showProgress(it)
                }, {
                    showError(it)
                }, {
                    showMainView()
                })
        startTimer()
    }

    private fun showProgress(progress: Int){
        ifViewAttached { it.showProgressUploading(progress) }
    }

    private fun showError(error: Throwable){
        ifViewAttached { it.showErrorMessage(error.localizedMessage) }
    }

    private fun showMainView(){
        ifViewAttached { it.showMainView() }
    }

    private val arrayOfInfo = resourcesService.getTimeInfoArray()!!
    private val random = Random(System.currentTimeMillis())

    private companion object {
        private const val INFO_DELAY = 5000L
    }

    private val timer = Timer()
    private val timerTask = object : TimerTask(){
        override fun run() {
            if(!isViewAttached){
                timer.cancel()
            }
            showRandomInfo()
        }
    }

    private fun showRandomInfo(){
        ifViewAttached {
            val randomInfo = getRandomInfo()
            it.showInfo(randomInfo)
        }
    }

    private fun getRandomInfo(): String {
        val randomIndex = random.nextInt(arrayOfInfo.size)
        return arrayOfInfo[randomIndex]
    }

    private fun startTimer() {
        timer.schedule(timerTask, 500, INFO_DELAY)
    }
}