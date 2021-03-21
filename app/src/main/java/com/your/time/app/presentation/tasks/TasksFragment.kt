package com.your.time.app.presentation.tasks

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.your.time.app.MyApplication
import com.your.time.app.databinding.FragmentTasksBinding
import com.your.time.app.di.presentation.tasks.DaggerTasksComponent
import com.your.time.app.di.presentation.tasks.TasksComponent
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.presentation.explansions.*
import com.your.time.app.presentation.tasks.adapter.TaskHolder
import com.your.time.app.presentation.tasks.adapter.TasksListAdapter
import com.your.time.app.presentation.tasks.mvp.TasksPresenter
import com.your.time.app.presentation.tasks.mvp.TasksView
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import java.util.*

class TasksFragment : MvpFragment<TasksView, TasksPresenter>(), TasksView {

    private val binder: FragmentTasksBinding by lazy {
        FragmentTasksBinding.inflate(layoutInflater)
    }

    private val component: TasksComponent by lazy {
        DaggerTasksComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
    }

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    private val adapter: TasksListAdapter by lazy {
        TasksListAdapter(layoutInflater, listItems, component.getTimeUtil())
    }

    private val listItems = ListForAdapterBase<TaskModel, TaskHolder>()

    override fun createPresenter() = component.getPresenter()

    companion object {
        fun newInstance() = TasksFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fun initCalendarIcon(){
            binder.calendarIcon.setOnClickListener {
                binder.calendar.switchVisibilityGone()
            }
        }

        fun initCalendar(){
            binder.calendar.setCalendarListener(object : DateRangeCalendarView.CalendarListener {

                override fun onDateRangeSelected(startDate: Calendar?, endDate: Calendar?) {
                    binder.showAllGoals.visible()
                    presenter.onSelectedDateRange(startDate, endDate)
                }

                override fun onFirstDateSelected(startDate: Calendar?) {
                    binder.showAllGoals.visible()
                    presenter.onSelectedDate(startDate)
                }

            })
        }

        fun initShowAllGoals(){
            binder.showAllGoals.setOnClickListener {
                binder.showAllGoals.gone()
                binder.calendar.resetAllSelectedViews()
                presenter.onClickShowAllTasks()
            }
        }

        fun initAddButton(){
            binder.addBtn.setOnClickListener {
                onClickAddTaskListener?.onClickAddTask()
            }
            binder.scroll.attachFloatButton(binder.addBtn)
        }

        fun initListTasks(){
            binder.list.layoutManager = layoutManager
            binder.list.adapter = adapter
        }

        initListTasks()
        initCalendarIcon()
        initCalendar()
        initShowAllGoals()
        initAddButton()

        return binder.root
    }

    override fun showTasks(tasks: List<TaskModel>) {
        listItems.setItems(tasks)
    }

    override fun showError() {
        activity?.shortToast("Error")
    }

    var onClickAddTaskListener: OnClickAddTaskListener? = null
    interface OnClickAddTaskListener {
        fun onClickAddTask()
    }
}
