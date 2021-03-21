package com.your.time.app.presentation.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.your.time.app.databinding.TaskListItemBinding
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

class TasksListAdapter(
        private val layoutInflater: LayoutInflater,
        private val listItems: ListForAdapter<TaskModel,TaskHolder>,
        private val timeUtil: TimeUtil
) : RecyclerView.Adapter<TaskHolder>() {

    init {
        listItems.setupAdapter(this)
    }

    override fun getItemCount() = listItems.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val binder = TaskListItemBinding.inflate(layoutInflater)
        return TaskHolder(binder)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val item = listItems.getItems()[position]

        fun getLastTask() = listItems.getItems()[position - 1]

        val isFirstTaskInList = position == 0

        val isNeedShowHeader = isFirstTaskInList
                || timeUtil.isAfterThisDay(getLastTask().timeInMinutes, item.timeInMinutes)

        val timeImMilliseconds = timeUtil.minutesToMilliseconds(item.timeInMinutes)
        val dateText = timeUtil.getDateText(timeImMilliseconds)

        holder.bind(item, isNeedShowHeader, dateText)
    }
}