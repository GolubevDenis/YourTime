package com.your.time.app.presentation.mark.dialog.adapters.actions

import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.view.action.ActionView

class ActionHolder(val view: ActionView) : RecyclerView.ViewHolder(view){

    fun bind(action: ActionModel) {
        view.action = action
    }
}