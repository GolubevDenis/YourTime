package com.your.time.app.presentation.home.adapters.tasks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.your.time.app.databinding.TaskListItemBinding
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

class HomeTasksListAdapter(
        private val layoutInflater: LayoutInflater,
        private val listItems: ListForAdapter<TaskModel, HomeTaskHolder>
) : RecyclerView.Adapter<HomeTaskHolder>() {

    init {
        listItems.setupAdapter(this)
    }

    override fun getItemCount() = listItems.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTaskHolder {
        val binder = TaskListItemBinding.inflate(layoutInflater)
        return HomeTaskHolder(binder)
    }

    override fun onBindViewHolder(holder: HomeTaskHolder, position: Int) {
        val item = listItems.getItems()[position]
        holder.bind(item)
    }
}