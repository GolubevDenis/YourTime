package com.your.time.app.presentation.home.adapters.actions

import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.view.action.ActionView

class ActionHomeHolder(val view: ActionView) : RecyclerView.ViewHolder(view){

    fun bind(action: ActionModel, isActive: Boolean) {
        view.action = action
        view.isActiveState = isActive
    }

    fun changeActiveState(): Boolean {
        view.isActiveState = !view.isActiveState
        return view.isActiveState
    }
}