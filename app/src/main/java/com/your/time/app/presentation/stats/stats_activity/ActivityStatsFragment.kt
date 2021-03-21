package com.your.time.app.presentation.stats.stats_activity


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.your.time.app.MyApplication
import com.your.time.app.databinding.FragmentStatsActivityBinding
import com.your.time.app.di.presentation.stats.activity.DaggerStatsActivityComponent
import com.your.time.app.di.presentation.stats.activity.StatsActivityComponent
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.presentation.explansions.setUnscrolableLinearLayoutManager
import com.your.time.app.presentation.home.adapters.time_periods.DiffTimePeriodCallbackFactory
import com.your.time.app.presentation.stats.stats_activity.adapters.StatsActivityTimePeriodHolder
import com.your.time.app.presentation.stats.stats_activity.adapters.StatsActivityTimePeriodsAdapter
import com.your.time.app.presentation.stats.stats_activity.mvp.StatsActivityPresenter
import com.your.time.app.presentation.stats.stats_activity.mvp.StatsActivityView
import com.your.time.app.presentation.stats.stats_usefulness.StatsUsefulnessFragment
import com.your.time.app.presentation.stats.stats_usefulness.adapters.StatsUsefulnessDayHolder
import com.your.time.app.presentation.stats.stats_usefulness.adapters.StatsUsefulnessDayAdapter
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase

class ActivityStatsFragment : MvpFragment<StatsActivityView, StatsActivityPresenter>(), StatsActivityView {

    companion object {
        fun newInstance() = ActivityStatsFragment()
    }
    private val component: StatsActivityComponent by lazy {
        DaggerStatsActivityComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
    }
    override fun createPresenter() = component.getPresenter()

    private val binder: FragmentStatsActivityBinding by lazy {
        FragmentStatsActivityBinding.inflate(layoutInflater)
    }
    private val timePeriodsList = ListForAdapterBase<TimePeriodModel, StatsActivityTimePeriodHolder>()
    private val timePeriodsAdapter: StatsActivityTimePeriodsAdapter by lazy {
        StatsActivityTimePeriodsAdapter(layoutInflater, timePeriodsList, DiffTimePeriodCallbackFactory(), component.getTimeUtil())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fun initTimePeriodsList() {
            binder.list.setUnscrolableLinearLayoutManager()
            binder.list.adapter = timePeriodsAdapter
        }

        fun initTabs(){
            binder.activityStatsTabs.setOnSwitchListener { position, tabText ->
                when(position){
                    0 -> presenter.onClickYesterday()
                    1 -> presenter.onClickToday()
                    2 -> presenter.onClickLast7Days()
                    3 -> presenter.onClickLast30Days()
                }
            }
        }

        initTimePeriodsList()
        initTabs()
        binder.activityStatsTabs.selectedTab = 1
        return binder.root
    }

    override fun showPeriods(periods: List<TimePeriodModel>) {
        binder.chart.showTimePeriods(periods)
        timePeriodsList.setItems(periods)
    }

    override fun showLoadingDataError() {
        Snackbar.make(binder.root, "Error", Snackbar.LENGTH_LONG).show()
    }
}
