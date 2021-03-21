package com.your.time.app.presentation.home.mvp

import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.data.habit.DaoHabitsInteractor
import com.your.time.app.domain.interactors.data.task.DaoTaskInteractor
import com.your.time.app.domain.interactors.data.usefulness.UsefulnessInteractor
import com.your.time.app.domain.interactors.home.HomeInteractor
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class HomePresenterImpl(
        private val homeInteractor: HomeInteractor,
        private val markInteractor: MarkInteractor,
        private val timeUtil: TimeUtil,
        private val habitsInteractor: DaoHabitsInteractor,
        private val taskInteractor: DaoTaskInteractor,
        private val daoActionsInteractor: DaoActionsInteractor,
        private val schedulersService: SchedulersService,
        private val usefulnessInteractor: UsefulnessInteractor
) : HomePresenter() {

    private var compositeDisposable = CompositeDisposable()

    override fun attachView(view: HomeView) {
        super.attachView(view)

        init()
    }

    private fun init(){
        showTimePeriods()
        showTasks()
        showHabits()
        showUsefulness()

        showUnmarkedTime()
        startUnmarkedTimeTimer()
        loadActions()
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }


    override fun showFullDay() {
        showTimePeriods()
    }

    override fun showPM() {
        val disposable = homeInteractor.getTimePeriodsPM()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ periods ->
                    ifViewAttached { it.showTimePeriods(periods) }
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }

    override fun showAM() {
        val disposable = homeInteractor.getTimePeriodsAM()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ periods ->
                    ifViewAttached { it.showTimePeriods(periods) }
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }

    override fun onClickFastMarkOk(actions: List<ActionModel>) {
        markInteractor.clearTimePeriods()
        markInteractor.newTimePeriodsByActions(actions)
                .observeOn(schedulersService.ui())
                .subscribeOn(schedulersService.io())
                .subscribe({
                    markInteractor.commitTimePeriods()
                    init()
                }, { error ->
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
    }

    private fun loadActions() {
        daoActionsInteractor.getAllActiveActions(true)
                .observeOn(schedulersService.ui())
                .subscribeOn(schedulersService.io())
                .subscribe({ list ->
                    ifViewAttached { it.showActionsForFastMarking(list) }
                }, { error ->
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
    }

    private fun showUnmarkedTime() {
        val disposable = markInteractor.getUnmarkedTime()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ timeInMinutes ->
                    if(timeInMinutes < 10){
                        ifViewAttached { it.hideMarkingPanel() }
                        return@subscribe
                    }
                    val textTime = timeUtil.getTextTimeDuration(timeInMinutes)
                    ifViewAttached { it.showUnmarkedTime(textTime) }
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }

    override fun onClickClearTime() {
        markInteractor.markAsCleared()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({
                    ifViewAttached { it.hideMarkingPanel() }
                    init()
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
    }

    private fun startUnmarkedTimeTimer(){
        val disposable = Observable.interval(30, 30, TimeUnit.SECONDS)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe { showUnmarkedTime() }
        compositeDisposable.add(disposable)
    }

    private fun showUsefulness() {
        val disposable = usefulnessInteractor.getUsefulnessOfCurrentDay()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ usefulness ->
                    ifViewAttached { it.showUsefulness(usefulness) }
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }

    private fun showTimePeriods(){
        val disposable = homeInteractor.getTimePeriods()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ periods ->
                    ifViewAttached { it.showTimePeriods(periods) }
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }










    private fun showHabits(){}

    private fun showTasks(){
        val disposable = taskInteractor.getTasksForToday()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ tasks ->
                    ifViewAttached { it.showTasks(tasks) }
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }

}