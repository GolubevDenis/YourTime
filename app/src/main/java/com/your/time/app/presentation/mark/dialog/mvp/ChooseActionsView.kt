package com.your.time.app.presentation.mark.dialog.mvp

import com.your.time.app.domain.model.ImageModel
import com.your.time.app.domain.model.actions.ActionModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChooseActionsView : MvpView {

    fun showActions(actions: List<ActionModel>)
    fun showMoreActions(actions: List<ActionModel>)
    fun showError(message: String)
    fun showNoActiveActions(actions: List<ActionModel>)
    fun showActionAdded(action: ActionModel)
    fun showImages(images: List<ImageModel>)
    fun deleteActions(actions: List<ActionModel>)
    fun showSearchResult(actions: List<ActionModel>)
    fun showMarkingTime(markingTime: String)
}
