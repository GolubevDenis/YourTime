package com.your.time.app.presentation.mark.adapter

import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.your.time.app.presentation.view.time_period.factory.TimePeriodViewFactoryImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class MarkTimePeriodsAdapterTest : Assert(){

    private lateinit var activity: Activity
    private lateinit var activityController: ActivityController<Activity>
    private lateinit var recycler: RecyclerView

    @Before
    fun init(){
        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()

        recycler = RecyclerView(activity)
        recycler.layoutManager = LinearLayoutManager(activity)

        activity.setContentView(recycler)
        activityController.create().start().resume().visible()
    }

    @Test
    fun testAddItems_AddIntoRecyclerView(){
        val adapter = newAdapter()
        recycler.adapter = adapter

        assertEquals(0, recycler.childCount)

        adapter.listItems.addItems(newTimePeriods(1))
        assertEquals(1, recycler.childCount)

        adapter.listItems.addItems(newTimePeriods(2))
        assertEquals(3, recycler.childCount)
    }

    private fun newAdapter(): MarkTimePeriodsAdapter {
        val listItems = ListForAdapterBase<TimePeriodModel, MarkTimePeriodHolder>()
        val timeUtil = Mockito.mock(TimeUtil::class.java)
        val factory = TimePeriodViewFactoryImpl(activity, timeUtil)
        return MarkTimePeriodsAdapter(listItems, factory)
    }

    private fun newTimePeriods(count: Int): List<TimePeriodModel> {
        return List(count) { i -> TimePeriodModel(ActionModel(i.toString(), color = Color.BLUE), count - 1, count)}
    }
}