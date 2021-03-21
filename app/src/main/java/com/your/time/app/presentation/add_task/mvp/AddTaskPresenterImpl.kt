package com.your.time.app.presentation.add_task.mvp

import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.interactors.data.task.DaoTaskInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil

class AddTaskPresenterImpl(
        private val tasksDaoTaskInteractor: DaoTaskInteractor,
        private val actionsDaoInteractor: DaoActionsInteractor,
        private val schedulersService: SchedulersService,
        private val timeUtil: TimeUtil
) : AddTaskPresenter() {

    override fun attachView(view: AddTaskView) {
        super.attachView(view)

        actionsDaoInteractor.getAllActions()
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({ actions ->
                    ifViewAttached { it.showActions(actions) }
                }, {
                    ifViewAttached { it.showError() }
                })

        showDate()
    }

    private fun showDate(){
        ifViewAttached { it.showDate(timeUtil.getDateText(selectedDate)) }
    }

    override fun onClickCreate(action: ActionModel?, description: String?, needRemind: Boolean) {
        if(action == null){
            ifViewAttached { it.showErrorSelectAction() }
            return
        }

        val isJustInTime = selectedHours != null || selectedMinutes != null

        val task = TaskModel(action, description, getTimeInMinutes(), isJustInTime, isNeedRemind = needRemind)

        tasksDaoTaskInteractor.addTask(task)
                .subscribeOn(schedulersService.io())
                .observeOn(schedulersService.ui())
                .subscribe({
                    ifViewAttached { it.taskIsAdded() }
                }, {
                    ifViewAttached { it.showError() }
                })
    }

    private var selectedDate = timeUtil.tomorrow()
    private var selectedHours: Int? = null
    private var selectedMinutes: Int? = null

    private fun getTimeInMinutes(): Int {
        var selectedDateInMinutes = timeUtil.millisecondsToMinutes(selectedDate).toLong()

        if(selectedHours != null)
            selectedDateInMinutes += selectedHours!! / 60

        if(selectedMinutes != null)
            selectedDateInMinutes += selectedMinutes!!

        return selectedDateInMinutes.toInt()
    }

    override fun onClickDatePicker() {
        ifViewAttached {
            val minDate = timeUtil.today()
            val maxDate = timeUtil.addYear(selectedDate)
            it.showDatePicker(minDate, selectedDate, maxDate)
        }
    }

    override fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        selectedDate = timeUtil.getTime(year, month, dayOfMonth)
        showDate()
    }

    override fun onClickTimePicker() {
        ifViewAttached { it.showTimePicker() }
    }

    override fun onTimeSelected(hours: Int, minutes: Int) {
        selectedHours = hours
        selectedMinutes = minutes
        val time = timeUtil.getTimeText(hours, minutes)
        ifViewAttached { it.showTime(time) }
    }

    override fun onClickSearch(searchQueryText: String) {
        actionsDaoInteractor.queryActiveActionByTitle(searchQueryText)
                .observeOn(schedulersService.ui())
                .subscribeOn(schedulersService.io())
                .subscribe({ actionsList ->
                    ifViewAttached { it.showActions(actionsList) }
                }, {
                    ifViewAttached { it.showError() }
                })
    }
}