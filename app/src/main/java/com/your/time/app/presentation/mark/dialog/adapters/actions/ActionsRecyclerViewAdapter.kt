package com.your.time.app.presentation.mark.dialog.adapters.actions

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.mark.dialog.adapters.list.ListForActionsAdapter
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMover
import com.your.time.app.presentation.view.action.ActionView

class ActionsRecyclerViewAdapter(
        private val context: Context,
        private val listItems: ListForActionsAdapter<ActionHolder>,
        private val actionMover: ChangeActiveStateActionsMover
) : RecyclerView.Adapter<ActionHolder>(){

    init {
        listItems.setupAdapter(this)
        listItems.setMover(actionMover)
    }

    fun getListItems() = listItems

    var onLongClickListener: ((ActionModel) -> Unit)? = null

    override fun getItemCount(): Int = listItems.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        return ActionHolder(ActionView(context))
    }

    override fun onBindViewHolder(holder: ActionHolder, position: Int) {
        fun setCurrentActiveStates(){
            if(position < actionMover.getCountActiveActions())
                holder.view.isActiveState = true
        }

        val action = listItems.getItems()[position]
        holder.bind(action)
        holder.view.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION){
                onClick(holder, adapterPosition)
            }
        }

        holder.view.setOnLongClickListener {
            onLongClickListener?.invoke(action)
            true
        }

        setCurrentActiveStates()
    }

    private fun onClick(holder: ActionHolder, position: Int){
        actionMover.changeActiveState(holder.view, position)
    }
}