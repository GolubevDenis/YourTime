package com.your.time.app.presentation.add_habit

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.your.time.app.MyApplication
import com.your.time.app.R
import com.your.time.app.databinding.FragmentAddHabitBinding
import com.your.time.app.di.presentation.add_habit.AddHabitComponent
import com.your.time.app.di.presentation.add_habit.DaggerAddHabitComponent
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.add_habit.mvp.AddHabitPresenter
import com.your.time.app.presentation.add_habit.mvp.AddHabitView
import com.your.time.app.presentation.add_task.adapter.ActionSelectAdapter
import com.your.time.app.presentation.explansions.shortToast
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.jakewharton.rxbinding2.widget.queryTextChanges
import com.your.time.app.presentation.mark.dialog.adapters.actions.ActionHolder
import java.util.concurrent.TimeUnit

class AddHabitFragment : MvpFragment<AddHabitView, AddHabitPresenter>(), AddHabitView {

    private val binder: FragmentAddHabitBinding by lazy { FragmentAddHabitBinding.inflate(layoutInflater) }
    private val component: AddHabitComponent by lazy {
        DaggerAddHabitComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
    }
    private val listActions = ListForAdapterBase<ActionModel, ActionHolder>()
    private val actionsAdapter: ActionSelectAdapter by lazy { ActionSelectAdapter(listActions, context!!) }

    override fun createPresenter() = component.getPresenter()

    companion object {
        fun newInstance(onHabitAddedListener: OnHabitAddedListener)
                = AddHabitFragment().also { it.onHabitAddedListener = onHabitAddedListener }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fun initCreateBtn(){
            binder.create.setOnClickListener {
                val action = actionsAdapter.getSelectedAction()

                val isMonday = binder.isMonday.isChecked
                val isTuesday = binder.isTuesday.isChecked
                val isWednesday = binder.isWednesday.isChecked
                val isThursday = binder.isThursday.isChecked
                val isFriday = binder.isFriday.isChecked
                val isSaturday = binder.isSaturday.isChecked
                val isSunday = binder.isSanday.isChecked

                val isMoreThenDuration = isMoreThenDuration()
                val isNeedToRemind = binder.isNeedRemind.isChecked

                val hours = binder.editHours.text.toString().toInt()
                val minutes = binder.editMinutes.text.toString().toInt()

                presenter.onClickCreate(action, hours, minutes, isMonday, isTuesday, isWednesday,
                        isThursday, isFriday, isSaturday, isSunday, isMoreThenDuration, isNeedToRemind)
            }
        }

        fun initActions(){
            binder.actions.layoutManager = GridLayoutManager(context, 4, LinearLayout.HORIZONTAL, false)
            binder.actions.adapter = actionsAdapter
        }

        fun initSearch(){
            binder.search.queryTextChanges()
                    .skipInitialValue()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        presenter.onClickSearch(it.toString())
                    }
        }

        initCreateBtn()
        initActions()
        initSearch()

        return binder.root
    }

    private fun isMoreThenDuration(): Boolean {
        return binder.isMoreThenDuration.selectedItemPosition == 0
    }

    override fun showNoActionSelectedError() {
        // TODO
        context!!.shortToast("Не выбрано действие")
    }

    override fun showErrorAddind() {
        // TODO
        context!!.shortToast("Ошибка")
    }

    override fun showErrorSearching() {
        // TODO
        context!!.shortToast("Ошибка поиска")
    }

    override fun showSuccessAddind() {
        onHabitAddedListener?.onHabitAdded()
    }

    override fun showNoDaySelected() {
        // TODO
        context!!.shortToast("Выберите дни")
    }

    override fun showActions(actions: List<ActionModel>) {
        listActions.setItems(actions)
    }

    override fun showTimeConflict() {
        context!!.shortToast(getString(R.string.time_conflict))
    }

    private var onHabitAddedListener: OnHabitAddedListener? = null
    interface OnHabitAddedListener {
        fun onHabitAdded()
    }
}
