package com.your.time.app.presentation.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.jakewharton.rxbinding2.view.clicks
import com.your.time.app.MyApplication
import com.your.time.app.R
import com.your.time.app.databinding.FragmentHomeBinding
import com.your.time.app.di.presentation.ActivityContextModule
import com.your.time.app.di.presentation.home.DaggerHomeComponent
import com.your.time.app.di.presentation.home.HomeComponent
import com.your.time.app.domain.model.TaskModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.explansions.*
import com.your.time.app.presentation.home.adapters.tasks.HomeTaskHolder
import com.your.time.app.presentation.home.adapters.tasks.HomeTasksListAdapter
import com.your.time.app.presentation.home.adapters.time_periods.DiffTimePeriodCallbackFactory
import com.your.time.app.presentation.home.adapters.time_periods.HomeTimePeriodHolder
import com.your.time.app.presentation.home.adapters.time_periods.HomeTimePeriodsAdapter
import com.your.time.app.presentation.home.mvp.HomePresenter
import com.your.time.app.presentation.home.mvp.HomeView
import com.your.time.app.presentation.home.adapters.actions.ActionsHomeRecyclerViewAdapter
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.your.time.app.presentation.view.decorators.ListMarginDecoration
import com.your.time.app.presentation.view.spinner.SimpleImageArrayAdapter

class HomeFragment : MvpFragment<HomeView, HomePresenter>(), HomeView {

    private val component: HomeComponent by lazy {
        DaggerHomeComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .activityContextModule(ActivityContextModule(activity!!))
                .build()
    }
    private val binder: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater, null, false)
    }

    private val timePeriodsList = ListForAdapterBase<TimePeriodModel, HomeTimePeriodHolder>()
    private val timePeriodsAdapter: HomeTimePeriodsAdapter by lazy {
        HomeTimePeriodsAdapter(layoutInflater, timePeriodsList, DiffTimePeriodCallbackFactory(), component.getTimeUtil())
    }

    private val tasksList = ListForAdapterBase<TaskModel, HomeTaskHolder>()
    private val tasksAdapter: HomeTasksListAdapter by lazy {
        HomeTasksListAdapter(layoutInflater, tasksList)
    }

    private val actionsAdapter: ActionsHomeRecyclerViewAdapter by lazy {
        component.getFastMarkingActionsAdapter()
    }

    var onMarkClickListener: OnMarkClickListener? = null

    interface OnMarkClickListener {
        fun onClickMark()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnMarkClickListener)
            onMarkClickListener = context
    }

    override fun onDetach() {
        super.onDetach()
        onMarkClickListener = null
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun createPresenter() = component.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fun initMarkBtn() {
            binder.markBtn.clicks()
                    .subscribe { onMarkClickListener?.onClickMark() }
            binder.scrollContainer.attachFloatButton(binder.markBtn)
        }

        fun initTimePeriodsList() {
            binder.periodsList.setUnscrolableLinearLayoutManager()
            binder.periodsList.adapter = timePeriodsAdapter
        }

        fun initTasksList() {
            binder.tastsList.setUnscrolableLinearLayoutManager()
            binder.tastsList.adapter = tasksAdapter
        }

        fun initClearButton(){
            binder.clear.setOnClickListener {
                presenter.onClickClearTime()
            }
        }

        fun initFastMarkingPanel(){

            fun initFastMarkingListAdapter(){
                binder.fastMarkingActions.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                binder.fastMarkingActions.addItemDecoration(ListMarginDecoration(context!!, 3))
                binder.fastMarkingActions.adapter = actionsAdapter
                actionsAdapter.onActiveItemsChanged = { activeActions ->
                    if(activeActions.isEmpty() && binder.okFastMark.isVisible()) {
                        binder.okFastMark.gone()
                    }
                    else if(activeActions.isNotEmpty() && !binder.okFastMark.isVisible()) {
                        binder.okFastMark.visible()
                    }
                }
            }

            fun initOkButton(){
                binder.okFastMark.setOnClickListener {
                    val selectedActions = actionsAdapter.getSelectedActions()
                    presenter.onClickFastMarkOk(selectedActions)
                }
            }

            initFastMarkingListAdapter()
            initOkButton()

        }

        fun initUsefulnessModesSpinner(){
            val chartUsefulnessViewModeAdapter = SimpleImageArrayAdapter(context,
                    arrayOf(R.drawable.chart_normal_mode, R.drawable.chart_usefulness_mode)
            )
            binder.chartUsefulnessViewMode.adapter = chartUsefulnessViewModeAdapter
            binder.chartUsefulnessViewMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(position == 0) binder.chart.switchToNormalMode()
                    else binder.chart.switchToUsefulnessMode()
                }
            }
        }

        fun initPartOfDayModesSpinner(){
            val partOfDayAdapter = SimpleImageArrayAdapter(context,
                    arrayOf(R.drawable.full_day, R.drawable.pm, R.drawable.am)
            )
            binder.partOfDayMode.adapter = partOfDayAdapter
            binder.partOfDayMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> {
                            presenter.showFullDay()
                            showFulldayMarkers()
                        }
                        1 -> {
                            presenter.showPM()
                            showPMMarkers()
                        }
                        2 -> {
                            presenter.showAM()
                            showAMMarkers()
                        }
                    }
                }

            }
        }

        initMarkBtn()
        initTimePeriodsList()
        initTasksList()
        initClearButton()

        initFastMarkingPanel()

        initUsefulnessModesSpinner()
        initPartOfDayModesSpinner()

        return binder.root
    }

    private fun showFulldayMarkers() {
        binder.topTimeMarker.text = "24h/0h"
        binder.rightTimeMarker.text = "6h"
        binder.bottomTimeMarker.text = "12h"
        binder.leftTimeMarker.text = "18h"
    }

    private fun showPMMarkers() {
        binder.topTimeMarker.text = "24h/12h"
        binder.rightTimeMarker.text = "15h"
        binder.bottomTimeMarker.text = "18h"
        binder.leftTimeMarker.text = "21h"
    }

    private fun showAMMarkers() {
        binder.topTimeMarker.text = "12h/0h"
        binder.rightTimeMarker.text = "3h"
        binder.bottomTimeMarker.text = "6h"
        binder.leftTimeMarker.text = "9h"
    }

    override fun showError(message: String) {
        ui {

        }
    }

    override fun showTasks(tasks: List<TaskModel>) {
        tasksList.setItems(tasks)
    }

    override fun showTimePeriods(periods: List<TimePeriodModel>) {
        ui {
            timePeriodsList.setItems(periods)
            binder.chart.showTimePeriods(periods)
        }
    }

    override fun showUsefulness(usefulness: Float) {
        binder.usefulness.setUsefulness(usefulness)
    }


    /* marking panel */
    override fun showUnmarkedTime(unmarkedTime: String) {
        if(!binder.cardForPanel.isVisible())
            binder.cardForPanel.visible()
        binder.time.text = unmarkedTime
    }

    override fun hideMarkingPanel() {
        binder.cardForPanel.gone()
    }

    override fun showActionsForFastMarking(list: List<ActionModel>) {
        actionsAdapter.getListItems().setItems(list)
    }
}
