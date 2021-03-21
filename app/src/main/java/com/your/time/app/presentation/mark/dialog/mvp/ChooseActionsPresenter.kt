package com.your.time.app.presentation.mark.dialog.mvp

import com.your.time.app.domain.model.actions.ActionModel
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

abstract class ChooseActionsPresenter : MvpBasePresenter<ChooseActionsView>() {

    abstract fun onClickSearch(searchQueryText: String)
    abstract fun onClickAddAction(action: ActionModel)
    abstract fun onClickSearchImages(query: String)
    abstract fun onClickModeAdding()
    abstract fun onDeleteActions(actions: List<ActionModel>)
    abstract fun onClickModeEdit(action: ActionModel)
    abstract fun onClickEditAction(action: ActionModel)
}