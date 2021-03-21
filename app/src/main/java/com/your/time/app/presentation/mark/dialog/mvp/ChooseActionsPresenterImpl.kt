package com.your.time.app.presentation.mark.dialog.mvp

import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.createAction.CreateActionInteractor
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.disposables.CompositeDisposable

class ChooseActionsPresenterImpl(
        private val interactor: DaoActionsInteractor,
        private val createActionInteractor: CreateActionInteractor,
        private val schedulersService: SchedulersService,
        private val markInteractor: MarkInteractor,
        private val timeUtil: TimeUtil
) : ChooseActionsPresenter() {

    private var compositeDisposable = CompositeDisposable()

    override fun attachView(view: ChooseActionsView) {
        super.attachView(view)
        loadActions()
        showUnmarkedTime()
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }

    private fun showUnmarkedTime() {
        val disposable = markInteractor.getUnmarkedTime()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ timeInMinutes ->
                    val textTime = timeUtil.getTextTimeDuration(timeInMinutes)
                    ifViewAttached { it.showMarkingTime(textTime) }
                }, { error ->
                    error.printStackTrace()
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
        compositeDisposable.add(disposable)
    }

    private fun loadActions() {
        interactor.getAllActiveActions(true)
                .observeOn(schedulersService.ui())
                .subscribeOn(schedulersService.io())
                .subscribe({ list ->
                    ifViewAttached { it.showActions(list) }
                }, { error ->
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
    }

    override fun onClickSearch(searchQueryText: String) {
        interactor.queryActiveActionByTitle(searchQueryText)
                .observeOn(schedulersService.ui())
                .subscribeOn(schedulersService.io())
                .subscribe({ actionsList ->
                    ifViewAttached { it.showSearchResult(actionsList) }
                }, { error ->
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
    }


    override fun onClickSearchImages(query: String) {
        ifViewAttached { it.showImages(createActionInteractor.searchImages(query)) }
    }

    override fun onClickModeAdding(){
        ifViewAttached { it.showImages(createActionInteractor.getAllImages()) }
    }

    override fun onClickModeEdit(action: ActionModel) {
        val sortedImages = createActionInteractor
                .getAllImages()
                .sortedBy { it.imageName != action.iconName }
        ifViewAttached { it.showImages(sortedImages) }
    }

    override fun onClickAddAction(action: ActionModel) {
        interactor.addAction(action)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({
                    ifViewAttached { it.showActionAdded(action) }
                }, { error ->
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
    }

    override fun onClickEditAction(action: ActionModel) {
        interactor.updateAction(action)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({
                    ifViewAttached { it.showActionAdded(action) }
                }, { error ->
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
    }

    override fun onDeleteActions(actions: List<ActionModel>) {
        interactor.markAsNotActiveActions(actions)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({
                    ifViewAttached { it.deleteActions(actions) }
                }, { error ->
                    ifViewAttached { it.showError(error.localizedMessage) }
                })
    }
}