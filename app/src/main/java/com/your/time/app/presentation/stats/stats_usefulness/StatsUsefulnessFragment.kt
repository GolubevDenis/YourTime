package com.your.time.app.presentation.stats.stats_usefulness


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.your.time.app.MyApplication
import com.your.time.app.databinding.FragmentStatsUsefulnessBinding
import com.your.time.app.di.presentation.stats.usefulness.DaggerStatsUsefulnessComponent
import com.your.time.app.di.presentation.stats.usefulness.StatsUsefulnessComponent
import com.your.time.app.domain.model.UsefulnessOfDay
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.explansions.setUnscrolableLinearLayoutManager
import com.your.time.app.presentation.stats.stats_usefulness.adapters.DiffUsefulnessOfDayCallbackFactory
import com.your.time.app.presentation.stats.stats_usefulness.adapters.StatsUsefulnessDayHolder
import com.your.time.app.presentation.stats.stats_usefulness.adapters.StatsUsefulnessDayAdapter
import com.your.time.app.presentation.stats.stats_usefulness.mvp.StatsUsefulnessPresenter
import com.your.time.app.presentation.stats.stats_usefulness.mvp.StatsUsefulnessView
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.your.time.app.presentation.view.chart.UsefulnessBarChart

class StatsUsefulnessFragment : MvpFragment<StatsUsefulnessView, StatsUsefulnessPresenter>(), StatsUsefulnessView {

    companion object {
        fun newInstance() = StatsUsefulnessFragment()
    }
    private val component: StatsUsefulnessComponent by lazy {
        DaggerStatsUsefulnessComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
    }
    override fun createPresenter() = component.getPresenter()

    private val timeUtil: TimeUtil by lazy {
        component.getTimeUtil()
    }

    private val binder: FragmentStatsUsefulnessBinding by lazy {
        FragmentStatsUsefulnessBinding.inflate(layoutInflater)
    }
    private val usefulnessOfDayList = ListForAdapterBase<UsefulnessOfDay, StatsUsefulnessDayHolder>()
    private val timePeriodsAdapter: StatsUsefulnessDayAdapter by lazy {
        StatsUsefulnessDayAdapter(layoutInflater, usefulnessOfDayList, DiffUsefulnessOfDayCallbackFactory())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fun initUsefulnessOfDaysList() {
            binder.list.setUnscrolableLinearLayoutManager()
            binder.list.adapter = timePeriodsAdapter
        }

        fun initTabs(){
            binder.activityStatsTabs.setOnSwitchListener { position, tabText ->
                when(position){
                    0 -> presenter.onClickLast7Days()
                    1 -> presenter.onClickLast30Days()
                }
            }
        }

        fun initChart(){
            binder.chart.timeUtil = timeUtil
            binder.chart.format = UsefulnessBarChart.Format.DATE_WITH_SHORT_MONTH
        }

        initUsefulnessOfDaysList()
        initTabs()
        initChart()

        binder.activityStatsTabs.selectedTab = 1
        return binder.root
    }



    override fun showUsefulnessOfDays(usefulness: List<UsefulnessOfDay>) {
        if(usefulness.size <= 7) binder.chart.format = UsefulnessBarChart.Format.SHORT_WEEKDAY
        else binder.chart.format = UsefulnessBarChart.Format.DATE_WITH_SHORT_MONTH

        binder.chart.showUsefulnessOfDays(usefulness)

        usefulnessOfDayList.setItems(usefulness)
    }

    override fun showLoadingDataError() {
        Snackbar.make(binder.root, "Error", Snackbar.LENGTH_LONG).show()
    }
}
