package com.your.time.app.presentation.mark.dialog.adapters.list

import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.mark.dialog.adapters.actions.ActionHolder
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMover
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

interface ListForActionsAdapter<T: RecyclerView.ViewHolder> : ListForAdapter<ActionModel, T> {

    fun setMover(mover: ChangeActiveStateActionsMover)
    fun getActiveActions(): List<ActionModel>
    fun setNoActiveActions(noActiveActions: List<ActionModel>)
    fun addActiveItem(action: ActionModel)
    fun markAllActionsAsNotActive()
    fun addNoActiveAction(noActiveAction: ActionModel)
    fun clearNoActiveActions()
}