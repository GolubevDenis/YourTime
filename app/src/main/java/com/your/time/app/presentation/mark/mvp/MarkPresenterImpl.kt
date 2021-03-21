package com.your.time.app.presentation.mark.mvp

import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.time.TimeModelImpl
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class MarkPresenterImpl(
        private val markInteractor: MarkInteractor,
        private val schedulersService: SchedulersService,
        private val timeUtil: TimeUtil
        ) : MarkPresenter(), TimeModelImpl.OnTimePeriodsChangedListener {

    init {
        markInteractor.subscribeOnTimePeriodsChanging(this)
    }

    private var compositeDisposable = CompositeDisposable()

    override fun  onStart() {
        showUnmarkedTime()
        startUnmarkedTimeTimer()

        val disposable = markInteractor.getActiveTimePeriods()
                .subscribe({ periods ->
                    ifViewAttached { it.showTimePeriods(periods) }
                }, {
                    // TODO
                })
        compositeDisposable.add(disposable)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }

    override fun onClickClearTime(minutesForClearing: Int) {
        markInteractor.markAsCleared(minutesForClearing)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({
                    showUnmarkedTime()
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showErrorMessage(error.localizedMessage) }
                })
    }

    private fun showUnmarkedTime() {
        val disposable = markInteractor.getUnmarkedTime()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ timeInMinutes ->
                    val textTime = timeUtil.getTextTimeDuration(timeInMinutes)
                    ifViewAttached { it.showUnmarkedTime(textTime) }
                    ifViewAttached { it.updateVisibilityOfTimeClearingButtons(timeInMinutes) }


                    val markedTime = markInteractor.getDuration()
                    if(markedTime == 0 || timeInMinutes == 0)
                        ifViewAttached { it.hideRemainingUnmurkedTime() }
                    else
                        ifViewAttached {
                            val leftTimeText = timeUtil.getTextTimeDuration(timeInMinutes)
                            it.showRemaxiningUnmurkedTime(leftTimeText)
                        }

                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showErrorMessage(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }

    private fun startUnmarkedTimeTimer(){
        val disposable = Observable.interval(10, 10, TimeUnit.SECONDS)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe { showUnmarkedTime() }
        compositeDisposable.add(disposable)
    }

    override fun onTimePeriodUpdated(timePeriod: TimePeriodModel, index: Int) {
        ifViewAttached { it.showUpdatingTimePeriod(index) }
        showUnmarkedTime()
    }

    override fun onTimePeriodAdded(timePeriod: TimePeriodModel) {
        ifViewAttached { it.showAddingTimePeriod(timePeriod) }
    }

    override fun onTimePeriodRemoved(timePeriod: TimePeriodModel) {
        ifViewAttached { it.showRemovingTimePeriod(timePeriod) }
    }

    override fun onActionsSelected(actions: List<ActionModel>) {
        val disposable = markInteractor.newTimePeriodsByActions(actions)
                .subscribe({
                    showUnmarkedTime()
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showErrorMessage(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }

    override fun onClickTimePeriodClose(timePeriod: TimePeriodModel) {
        markInteractor.deleteTimePeriod(timePeriod)
        showUnmarkedTime()
    }

    override fun onClickOk(){
        markInteractor.commitTimePeriods()
        ifViewAttached { it.showCommitSuccess() }
    }
}