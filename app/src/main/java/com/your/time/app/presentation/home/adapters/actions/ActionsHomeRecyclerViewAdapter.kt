package com.your.time.app.presentation.home.adapters.actions

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.view.action.ActionView
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

class ActionsHomeRecyclerViewAdapter(
        private val context: Context,
        private val listItems: ListForAdapter<ActionModel, ActionHomeHolder>
) : RecyclerView.Adapter<ActionHomeHolder>(){

    init {
        listItems.setupAdapter(this)
    }

    var onActiveItemsChanged: ((List<ActionModel>) -> Unit)? = null

    fun getListItems() = listItems

    override fun getItemCount(): Int = listItems.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHomeHolder {
        return ActionHomeHolder(ActionView(context))
    }

    override fun onBindViewHolder(holder: ActionHomeHolder, position: Int) {
        val action = listItems.getItems()[position]
        val isActive = listOfActiveItems.contains(action)
        holder.bind(action, isActive)
        holder.view.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION){
                onClick(holder, action)
            }
        }
    }

    private val listOfActiveItems = arrayListOf<ActionModel>()
    fun getSelectedActions() = listOfActiveItems as List<ActionModel>

    private fun onClick(holder: ActionHomeHolder, item: ActionModel){
        val isActive = holder.changeActiveState()
        if(isActive){
            if(!listOfActiveItems.contains(item))
                listOfActiveItems.add(item)
        }else{
            if(listOfActiveItems.contains(item))
                listOfActiveItems.remove(item)
        }

        onActiveItemsChanged?.invoke(listOfActiveItems)
    }
}