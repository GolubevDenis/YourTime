package com.your.time.app.presentation.mark

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.your.time.app.MyApplication
import com.your.time.app.databinding.FragmentMarkBinding
import com.your.time.app.di.presentation.ActivityContextModule
import com.your.time.app.di.presentation.mark.DaggerMarkComponent
import com.your.time.app.di.presentation.mark.MarkComponent
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.presentation.explansions.invisible
import com.your.time.app.presentation.mark.mvp.MarkPresenter
import com.your.time.app.presentation.mark.mvp.MarkView
import com.your.time.app.presentation.explansions.ui
import com.your.time.app.presentation.explansions.visible
import com.your.time.app.presentation.mark.adapter.MarkTimePeriodsAdapter
import com.your.time.app.presentation.mark.dialog.ChooseActionsDialogFragment
import com.your.time.app.presentation.view.time_period.TimePeriodView
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.your.time.app.R
import com.your.time.app.presentation.explansions.gone

class MarkFragment :
        MvpFragment<MarkView, MarkPresenter>(),
        ChooseActionsDialogFragment.OnChooseActionsDialogListeners,
        MarkView
{

    private lateinit var binder: FragmentMarkBinding
    private lateinit var adapter: MarkTimePeriodsAdapter

    private val onClickCloseTimePeriodListener = object : TimePeriodView.OnCloseClickListener {
        override fun onClickClose(timePeriod: TimePeriodModel) {
            presenter.onClickTimePeriodClose(timePeriod)
        }
    }

    companion object {
        fun newInstance() = MarkFragment()
    }

    override fun createPresenter(): MarkPresenter = component.getPresenter()

    private val component: MarkComponent by lazy {
        DaggerMarkComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .activityContextModule(ActivityContextModule(activity!!))
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        fun inject() {
            adapter = component.getAdapter()
        }

        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binder = FragmentMarkBinding.inflate(inflater, container, false)

        fun initAddButton(){
            binder.add.setOnClickListener {
                activity?.let { showSelectActionsDialog() }
            }
        }

        fun initOk(){
            binder.ok.setOnClickListener {
                presenter.onClickOk()
            }
        }

        fun initRecycler() {
            binder.listTimePeriods.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binder.listTimePeriods.adapter = adapter
            adapter.onClickCloseListener = onClickCloseTimePeriodListener
        }

        fun initClearingTimeButtons(){
            binder.oneHour.setOnClickListener {
                presenter.onClickClearTime(60)
            }
            binder.twoHours.setOnClickListener { presenter.onClickClearTime(60 * 2) }
            binder.fiveHours.setOnClickListener { presenter.onClickClearTime(60 * 5) }
            binder.twelveHours.setOnClickListener { presenter.onClickClearTime(60 * 12) }
            binder.day.setOnClickListener { presenter.onClickClearTime(60 * 24) }
        }

        initAddButton()
        initOk()
        initRecycler()
        initClearingTimeButtons()

        return binder.root
    }

    private var chooseActionsDialog: ChooseActionsDialogFragment? = null
    private fun showSelectActionsDialog() {
        if(chooseActionsDialog != null && chooseActionsDialog!!.isVisible)
            return

        chooseActionsDialog = ChooseActionsDialogFragment.newInstance()
        chooseActionsDialog!!.show(activity!!.supportFragmentManager, "ChooseActionsDialog")
        chooseActionsDialog!!.listener = this
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun showTimePeriods(periods: List<TimePeriodModel>) {
        ui {
            if(periods.isEmpty()) showSelectActionsDialog()
            adapter.listItems.setItems(periods)
            updateEmptyListState()
        }
    }

    override fun onActionsSelected(actions: List<ActionModel>) {
        ui {
            presenter.onActionsSelected(actions)
            updateEmptyListState()
        }
    }

    override fun showMoreTimePeriods(periods: List<TimePeriodModel>) {
        ui {
            adapter.listItems.addItems(periods)
            updateEmptyListState()
        }
    }

    override fun showUpdatingTimePeriod(position: Int){}

    override fun showAddingTimePeriod(period: TimePeriodModel) {
        ui {
            adapter.listItems.addItem(period)
            updateEmptyListState()
        }
    }

    override fun showRemovingTimePeriod(period: TimePeriodModel) {
        ui {
            adapter.listItems.deleteItem(period)
            updateEmptyListState()
        }
    }

    override fun showErrorMessage(message: String) { ui {

    } }

    override fun showCommitSuccess() {
        ui {
            activity!!.onBackPressed()
        }
    }

    private fun updateEmptyListState(){
        if(adapter.listItems.getItems().isEmpty()){
            binder.unmarkedTime.visible()
            binder.noActionImage.visible()
        }else{
            binder.unmarkedTime.invisible()
            binder.noActionImage.invisible()
            binder.timeClearingInfoText.invisible()
            hideTimeClearingButtons()
        }
    }

    override fun showUnmarkedTime(textTime: String) {
        binder.unmarkedTime.text = textTime
    }

    override fun hideRemainingUnmurkedTime() {
        binder.remainingUnmarkedTime.invisible()
    }

    override fun showRemaxiningUnmurkedTime(leftUnmarkedTime: String) {
        binder.remainingUnmarkedTime.visible()
        binder.remainingUnmarkedTime.text = getString(R.string.remaining_time, leftUnmarkedTime)
    }

    private fun hideTimeClearingButtons(){
        binder.timeClearingInfoText.invisible()
        binder.oneHour.gone()
        binder.twoHours.gone()
        binder.fiveHours.gone()
        binder.twelveHours.gone()
        binder.day.gone()
    }

    override fun updateVisibilityOfTimeClearingButtons(unmarkedTime: Int) {
        if(adapter.listItems.getItems().isNotEmpty()){
            hideTimeClearingButtons()
            return
        }

        if(unmarkedTime > 60) {
            binder.oneHour.visible()
            binder.timeClearingInfoText.visible()
        }
        else {
            binder.oneHour.gone()
            binder.timeClearingInfoText.invisible()
        }

        if(unmarkedTime > 60 * 2) binder.twoHours.visible()
        else binder.twoHours.gone()

        if(unmarkedTime > 60 * 5) binder.fiveHours.visible()
        else binder.fiveHours.gone()

        if(unmarkedTime > 60 * 12) binder.twelveHours.visible()
        else binder.twelveHours.gone()

        if(unmarkedTime > 60 * 24) binder.day.visible()
        else binder.day.gone()
    }
}
