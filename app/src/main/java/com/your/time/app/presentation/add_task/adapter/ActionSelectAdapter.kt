package com.your.time.app.presentation.add_task.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.mark.dialog.adapters.actions.ActionHolder
import com.your.time.app.presentation.view.action.ActionView
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

class ActionSelectAdapter(
        val items: ListForAdapter<ActionModel, ActionHolder>,
        private val context: Context
) : RecyclerView.Adapter<ActionHolder>() {

    init {
        items.setupAdapter(this)
    }

    private var selectedAction: ActionModel? = null

    override fun getItemCount() = items.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        return ActionHolder(ActionView(context))
    }

    override fun onBindViewHolder(holder: ActionHolder, position: Int) {
        val action = items.getItems()[position]

        holder.view.setOnClickListener {
            selectedAction = action
            notifyDataSetChanged()
        }

        holder.view.isActiveState = action == selectedAction

        holder.bind(action)
    }

    fun getSelectedAction() = selectedAction
}