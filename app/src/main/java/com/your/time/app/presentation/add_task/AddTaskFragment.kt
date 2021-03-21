package com.your.time.app.presentation.add_task


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.your.time.app.MyApplication
import com.your.time.app.databinding.FragmentAddTaskBinding
import com.your.time.app.di.presentation.add_task.AddTaskComponent
import com.your.time.app.di.presentation.add_task.DaggerAddTaskComponent
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.add_task.adapter.ActionSelectAdapter
import com.your.time.app.presentation.add_task.mvp.AddTaskPresenter
import com.your.time.app.presentation.add_task.mvp.AddTaskView
import com.your.time.app.presentation.explansions.shortToast
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.view.adapters.list.ListForAdapter
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.your.time.app.presentation.view.dialogs.pickers.DatePickerFragmentDialog
import com.your.time.app.presentation.view.dialogs.pickers.TimePickerFragmentDialog
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.jakewharton.rxbinding2.widget.queryTextChanges
import com.your.time.app.presentation.mark.dialog.adapters.actions.ActionHolder
import java.util.concurrent.TimeUnit

class AddTaskFragment : MvpFragment<AddTaskView, AddTaskPresenter>(), AddTaskView, DatePickerFragmentDialog.OnDataSelectedListener {

    private val component: AddTaskComponent by lazy {
        DaggerAddTaskComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
    }

    private val binder: FragmentAddTaskBinding by lazy {
        FragmentAddTaskBinding.inflate(layoutInflater)
    }

    private val listActions: ListForAdapter<ActionModel, ActionHolder> by lazy {
        ListForAdapterBase<ActionModel, ActionHolder>()
    }

    private val actionsAdapter: ActionSelectAdapter by lazy {
        ActionSelectAdapter(listActions, context!!)
    }

    private var onTaskAddedListener: OnTaskAddedListener? = null

    override fun createPresenter() = component.getPresenter()

    companion object {
        fun newInstance(onTaskAddedListener: OnTaskAddedListener) = AddTaskFragment()
                .also { it.onTaskAddedListener = onTaskAddedListener }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fun initCreateButton(){
            binder.create.setOnClickListener {
                val action = actionsAdapter.getSelectedAction()
                val description = binder.description.text.toString()
                val isNeedRemind = binder.isNeedRemind.isChecked
                presenter.onClickCreate(action, description, isNeedRemind)
            }
        }

        fun initSearch(){
            binder.search.queryTextChanges()
                    .skipInitialValue()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        presenter.onClickSearch(it.toString())
                    }
        }

        fun initListActions(){
            binder.actions.layoutManager = GridLayoutManager(context, 4, LinearLayout.HORIZONTAL, false)
            binder.actions.adapter = actionsAdapter
        }

        fun initDatePicker(){
            val onClick = View.OnClickListener {
                presenter.onClickDatePicker()
            }
            binder.dateIcon.setOnClickListener(onClick)
            binder.dateText.setOnClickListener(onClick)
        }

        fun initTimePicker(){
            val onClick = View.OnClickListener {
                presenter.onClickTimePicker()
            }
            binder.timeIcon.setOnClickListener(onClick)
            binder.timeText.setOnClickListener(onClick)
        }

        initCreateButton()
        initListActions()
        initDatePicker()
        initTimePicker()
        initSearch()

        return binder.root
    }

    override fun showTime(time: String) {
        binder.timeText.text = time
    }

    override fun showTimePicker() {
        val dialog = TimePickerFragmentDialog.newInstance(object : TimePickerFragmentDialog.OnTimeSelectedListener {
            override fun onTimeSelected(hours: Int, minutes: Int) {
                presenter.onTimeSelected(hours, minutes)
            }
        })
        dialog.show(fragmentManager, "TIME_PICKER")
    }

    override fun onDataSelected(year: Int, month: Int, dayOfMonth: Int) {
        presenter.onDateSelected(year, month, dayOfMonth)
    }

    override fun showDatePicker(minDate: Long, selectedTime: Long, maxDate: Long) {
        val dialog = DatePickerFragmentDialog.newInstance(
                minTime = minDate, selectedTime = selectedTime, maxTime = maxDate, onDataSelectedListener = this
        )
        dialog.show(fragmentManager, "DATE_PICKER")
    }

    override fun showDate(dateText: String) {
        binder.dateText.text = dateText
    }

    override fun taskIsAdded() {
        onTaskAddedListener?.onTaskIsAdded()
    }

    override fun showActions(actions: List<ActionModel>) {
        listActions.setItems(actions)
    }

    override fun showError() {
        activity?.shortToast("Error")
    }

    override fun showErrorSelectAction() {
        activity?.shortToast("Select action please")
    }

    interface OnTaskAddedListener {
        fun onTaskIsAdded()
    }
}
