package com.your.time.app.presentation.home.adapters.tasks

import android.support.v7.widget.RecyclerView
import com.your.time.app.databinding.TaskListItemBinding
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.presentation.explansions.invisible
import com.your.time.app.presentation.explansions.visible

class HomeTaskHolder(
        private val binder: TaskListItemBinding
) : RecyclerView.ViewHolder(binder.root) {

    fun bind(task: TaskModel) {

        binder.description.text = task.description
        binder.actionView.action = task.action
        binder.isCompletedManually.isChecked = task.isManuallyCompleted

        if(task.isManuallyCompleted) binder.isNeedRemind.visible()
        else binder.isNeedRemind.invisible()
    }
}