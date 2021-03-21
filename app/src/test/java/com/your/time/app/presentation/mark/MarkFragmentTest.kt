package com.your.time.app.presentation.mark

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.your.time.app.R
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.presentation.mark.mvp.MarkPresenter
import com.your.time.app.presentation.mark.adapter.MarkTimePeriodsAdapter
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowDialog
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment

@RunWith(RobolectricTestRunner::class)
class MarkFragmentTest : Assert(){

    private lateinit var fragment: MarkFragment
    private lateinit var recycler: RecyclerView
    private lateinit var recyclerAdapter: MarkTimePeriodsAdapter
    private lateinit var add: ImageView
    private lateinit var ok: TextView
    private lateinit var presenter: MarkPresenter
    private val action = ActionModel("any action", color = Color.BLUE)
    private val timePeriod = TimePeriodModel(action, 0, 1)

    @Before
    fun init(){
        fragment = MarkFragment.newInstance()
        startFragment(fragment)

        add = fragment.view!!.findViewById<ImageView>(R.id.add)
        ok = fragment.view!!.findViewById<TextView>(R.id.ok)
        recycler = fragment.view!!.findViewById<RecyclerView>(R.id.listTimePeriods)
        recyclerAdapter = recycler.adapter as MarkTimePeriodsAdapter
        presenter = Mockito.mock(MarkPresenter::class.java)

    }

    @Test
    fun testClickAdd_ShowDialog(){
        val dialogNull = ShadowDialog.getLatestDialog()
        assertNull(dialogNull)

        add.performClick()

        val dialog = ShadowDialog.getLatestDialog()
        assertNotNull(dialog)
    }

    @Test
    fun testOnSelectedItems_CallsPresenterCallback(){
        fragment.presenter = presenter

        val list = listOf(action)
        fragment.onActionsSelected(list)
        Mockito.verify(presenter).onActionsSelected(list)
    }

    @Test
    fun testShowMoreTimePeriods_AddTimePeriodsIntoRecyclerViewAdapter(){
        assertEquals(0, recyclerAdapter.itemCount)

        fragment.showMoreTimePeriods(listOf(timePeriod))
        assertEquals(1, recyclerAdapter.itemCount)

        fragment.showMoreTimePeriods(listOf(timePeriod, timePeriod))
        assertEquals(3, recyclerAdapter.itemCount)

        recyclerAdapter.listItems.clearItems()
    }

    @Test
    fun testSetOnCloseClickListenerIntoRecyclerAdapter(){
        assertNotNull(recyclerAdapter.onClickCloseListener)
    }

    @Test
    fun testOnClickClose_InvokePresenterCallback(){
        fragment.presenter = presenter

        recyclerAdapter.onClickCloseListener!!.onClickClose(timePeriod)
        Mockito.verify(presenter).onClickTimePeriodClose(timePeriod)
    }

    @Test
    fun testShowRemovingTimePeriod(){
        recyclerAdapter.listItems.addItem(timePeriod)
        assertEquals(1, recyclerAdapter.itemCount)

        fragment.showRemovingTimePeriod(timePeriod)
        assertEquals(0, recyclerAdapter.itemCount)
    }

    @Test
    fun testOnClickOk_CallsPresenterCallback(){
        fragment.presenter = presenter

        ok.performClick()
        Mockito.verify(presenter).onClickOk()
    }
}
