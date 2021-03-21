package com.your.time.app.presentation.mark.dialog.adapters.list

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import com.your.time.app.assertException
import com.your.time.app.assertNotException
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMover
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBaseTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(ListForAdapterBaseTest.TestAdapter::class)
class ListForActionsAdapterImplTest : Assert() {

    private lateinit var mover: ChangeActiveStateActionsMover
    private lateinit var adapter: TestAdapter

    @Before
    fun init(){
        mover = Mockito.mock(ChangeActiveStateActionsMover::class.java)
        adapter = newAdapter()
    }

    @Test
    fun testGetActiveActions(){
        Mockito.`when`(mover.getCountActiveActions()).thenReturn(2)
        val list = ListForActionsAdapterImpl()
        list.setMover(mover)
        list.addItems(newActions("1", "2", "3", "4", "5"))

        assertEquals(newActions("1", "2"), list.getActiveActions())
    }

    @Test
    fun testThrowsExceptionIfMoverWasNotInjected(){
        assertException({
            val list = ListForActionsAdapterImpl()
            list.getActiveActions()
        }, IllegalStateException::class)
    }

    @Test
    fun testDoesNotThrowExceptionIfMoverWasInjected(){
        assertNotException({
            val list = ListForActionsAdapterImpl()
            val mover = Mockito.mock(ChangeActiveStateActionsMover::class.java)
            list.setMover(mover)
            list.getActiveActions()
        })
    }

    @Test
    fun testSetNoActiveActions(){
        val list = ListForActionsAdapterImpl()
        Mockito.`when`(mover.getCountActiveActions()).thenReturn(3)
        list.setMover(mover)
        list.addItems(newActions("1", "2", "3", "4", "5"))
        list.setNoActiveActions(newActions("6", "7"))
        assertEquals(newActions("1", "2", "3", "6", "7"), list.getItems())
    }

    @Test
    fun testSetNoActiveActions_DoNotAddTheSameActions(){
        val list = ListForActionsAdapterImpl()
        Mockito.`when`(mover.getCountActiveActions()).thenReturn(3)
        list.setMover(mover)
        list.addItems(newActions("1", "2", "3", "4", "5"))
        list.setNoActiveActions(newActions("3", "6"))
        assertEquals(newActions("1", "2", "3", "6"), list.getItems())
    }

    @Test
    fun testAddActiveItem(){
        val list = ListForActionsAdapterImpl()
        list.setMover(mover)
        list.addItems(newActions("1", "2", "3", "4", "5"))

        Mockito.`when`(mover.getCountActiveActions()).thenReturn(0)
        val activeAction1 = ActionModel("active 1", color = Color.BLUE)
        list.addActiveItem(activeAction1)
        assertEquals(6, list.getItems().size)
        assertEquals(0, list.getItems().indexOf(activeAction1))

        Mockito.`when`(mover.getCountActiveActions()).thenReturn(1)
        val activeAction2 = ActionModel("active 2", color = Color.BLUE)
        list.addActiveItem(activeAction2)
        assertEquals(1, list.getItems().indexOf(activeAction2))
    }

    @Test
    fun testAddActiveItem_NotifyDataChanged(){
        val list = ListForActionsAdapterImpl()
        list.setMover(mover)
        list.setupAdapter(adapter)

        Mockito.`when`(mover.getCountActiveActions()).thenReturn(0)
        val activeAction1 = ActionModel("active 1", color = Color.BLUE)
        list.addActiveItem(activeAction1)
        Mockito.verify(adapter).notifyDataSetChanged()
    }

    @Test
    fun testAddActiveItem_NotifyItemChanged_IfItemsTheSame(){
        val list = ListForActionsAdapterImpl()
        list.setMover(mover)
        list.setupAdapter(adapter)

        Mockito.`when`(mover.getCountActiveActions()).thenReturn(0)
        val activeAction1 = ActionModel("active 1", color = Color.BLUE)

        list.addActiveItem(activeAction1)
        list.addActiveItem(activeAction1)
        Mockito.verify(adapter).notifyItemChanged(0)
    }

    @Test
    fun testAddActiveItem_ChangeTheSame(){
        val list = ListForActionsAdapterImpl()
        list.setMover(mover)
        list.setupAdapter(adapter)

        Mockito.`when`(mover.getCountActiveActions()).thenReturn(0)
        val activeAction1 = ActionModel("active", "iconName1", color = Color.BLUE)
        val activeAction2 = ActionModel("active", "iconName2", color = Color.BLUE)

        list.addActiveItem(activeAction1)
        assertEquals(1, list.getItems().size)

        list.addActiveItem(activeAction2)
        assertEquals(1, list.getItems().size)
        assertEquals("iconName2", list.getItems()[0].iconName)
    }

    @Test
    fun testAddActiveItem_IncrementMover_IfActionIsNew(){
        val list = ListForActionsAdapterImpl()
        list.setMover(mover)
        list.setupAdapter(adapter)

        Mockito.`when`(mover.getCountActiveActions()).thenReturn(0)
        val activeAction1 = ActionModel("active", "iconName1", color = Color.BLUE)

        list.addActiveItem(activeAction1)
        Mockito.verify(mover).incrementCountActiveActions()
    }

    @Test
    fun testAddActiveItem_DoesNotIncrementMover_IfActionWasActive(){
        val list = ListForActionsAdapterImpl()
        list.setMover(mover)
        list.setupAdapter(adapter)

        Mockito.`when`(mover.getCountActiveActions()).thenReturn(0)
        val activeAction1 = ActionModel("active", "iconName1", color = Color.BLUE)

        list.addActiveItem(activeAction1)
        Mockito.verify(mover, Mockito.times(1)).incrementCountActiveActions()
        list.addActiveItem(activeAction1)
    }

    private fun newActions(vararg titles: String): List<ActionModel>{
        return List<ActionModel>(titles.size) { i -> ActionModel(titles[i], color = Color.BLUE) }
    }

    private fun newAdapter(): TestAdapter{
        val adapter = PowerMockito.mock(TestAdapter::class.java)

        PowerMockito.doNothing().`when`(adapter).notifyDataSetChanged()
        PowerMockito.doNothing().`when`(adapter).notifyItemRangeChanged(Mockito.anyInt(), Mockito.anyInt())

        return adapter
    }

    private abstract class TestAdapter : RecyclerView.Adapter<ActionHomeHolder>()
}