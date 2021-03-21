package com.your.time.app.presentation.mark.dialog.adapters.actions

import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.home.adapters.actions.ActionsHomeRecyclerViewAdapter
import com.your.time.app.presentation.mark.dialog.adapters.list.ListForActionsAdapterImpl
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMoverImpl
import com.your.time.app.presentation.view.action.ActionView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class ActionsRecyclerViewAdapterTest : Assert(){

    private lateinit var activity: Activity
    private lateinit var activityController: ActivityController<Activity>
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ActionsHomeRecyclerViewAdapter

    @Before
    fun init(){
        activityController = Robolectric.buildActivity(Activity::class.java)
        activity = activityController.get()

        recycler = RecyclerView(activity)
        recycler.layoutManager = LinearLayoutManager(activity)

        activity.setContentView(recycler)
        activityController.create().start().resume().visible()

        adapter = newAdapter()
        recycler.adapter = adapter
    }

    @Test
    fun testSetCurrentActiveStates(){
        adapter.getListItems().addItems(
                listOf(
                        ActionModel("action 1", color = Color.BLUE),
                        ActionModel("action 2", color = Color.BLUE),
                        ActionModel("action 3", color = Color.BLUE)
                )
        )
        assertEquals(3, recycler.childCount)

        adapter.getListItems().setItems(
                listOf(
                        ActionModel("action 1", color = Color.BLUE),
                        ActionModel("action 2", color = Color.BLUE)
                )
        )
        assertEquals(2, recycler.childCount)
    }

    @Test
    fun testCreateItems(){
        adapter.getListItems().addItems(
                listOf(
                        ActionModel("action 1", color = Color.BLUE),
                        ActionModel("action 2", color = Color.BLUE),
                        ActionModel("action 3", color = Color.BLUE)
                )
        )
        assertEquals(3, recycler.childCount)

        adapter.getListItems().setItems(
                listOf(
                        ActionModel("action 1", color = Color.BLUE),
                        ActionModel("action 2", color = Color.BLUE)
                )
        )
        assertEquals(2, recycler.childCount)
    }

    @Test
    fun testClickItem_ChangeActiveState(){
        adapter.getListItems().addItems(
                listOf(
                        ActionModel("action 1", color = Color.BLUE),
                        ActionModel("action 2", color = Color.BLUE),
                        ActionModel("action 3", color = Color.BLUE)
                )
        )

        val actionView1 = recycler.getChildAt(0) as ActionView

        actionView1.performClick()
        assertTrue(actionView1.isActiveState)

        actionView1.performClick()
        assertFalse(actionView1.isActiveState)

        val actionView2 = recycler.getChildAt(2) as ActionView

        actionView2.performClick()
        assertTrue(actionView2.isActiveState)

        actionView2.performClick()
        assertFalse(actionView2.isActiveState)
    }

    @Test
    fun testClickItem_ChangingPosition(){
        val action1 = ActionModel("action 1", color = Color.BLUE)
        val action2 = ActionModel("action 2", color = Color.BLUE)
        val action3 = ActionModel("action 3", color = Color.BLUE)
        adapter.getListItems().addItems(listOf(action1, action2, action3))

        // Кликаем на средний элемент, он перемещается в начало
        (recycler.getChildAt(1) as ActionView).performClick()
        assertEquals(action2, adapter.getListItems().getItems()[0])
        assertEquals(action1, adapter.getListItems().getItems()[1])
        assertEquals(action3, adapter.getListItems().getItems()[2])

        // Кликаем на средний элемент, он остается в середине,
        // так как это последняя позиция для неактивных элементов
        (recycler.getChildAt(1) as ActionView).performClick()
        assertEquals(action2, adapter.getListItems().getItems()[0])
        assertEquals(action1, adapter.getListItems().getItems()[1])
        assertEquals(action3, adapter.getListItems().getItems()[2])

        // Кликаем на первый элемент, он в активном состоянии,
        // он принамает позицию после активных элементов
        (recycler.getChildAt(0) as ActionView).performClick()
        assertEquals(action1, adapter.getListItems().getItems()[0])
        assertEquals(action2, adapter.getListItems().getItems()[1])
        assertEquals(action3, adapter.getListItems().getItems()[2])
    }

    @Test
    fun testClickItems_ChangingPositions(){
        val action1 = ActionModel("action 1", color = Color.BLUE)
        val action2 = ActionModel("action 2", color = Color.BLUE)
        val action3 = ActionModel("action 3", color = Color.BLUE)
        adapter.getListItems().addItems(listOf(action1, action2, action3))

        // Кликаем на все элементы по очереди,
        // они все должны остаться на месте и поменять состояние на активное
        for(i in 0 until recycler.childCount){
            (recycler.getChildAt(i) as ActionView).performClick()
        }
        assertEquals(action1, adapter.getListItems().getItems()[0])
        assertEquals(action2, adapter.getListItems().getItems()[1])
        assertEquals(action3, adapter.getListItems().getItems()[2])

        // Кликаем каждый раз на первый элемент, он принимает неактивное состоянии
        // и перемещается на позицию перед первым неактивным.
        // Порядок должен восстановиться
        for(i in 0 until recycler.childCount){
            (recycler.getChildAt(0) as ActionView).performClick()
        }
        assertEquals(action3, adapter.getListItems().getItems()[0])
        assertEquals(action2, adapter.getListItems().getItems()[1])
        assertEquals(action1, adapter.getListItems().getItems()[2])
    }

    @Test
    fun testSaveActiveStateAfterDataChanging(){
        val action1 = ActionModel("action 1", color = Color.BLUE)
        val action2 = ActionModel("action 2", color = Color.BLUE)
        val action3 = ActionModel("action 3", color = Color.BLUE)
        adapter.getListItems().addItems(listOf(action1, action2, action3))

        (recycler.getChildAt(0) as ActionView).performClick()
        (recycler.getChildAt(1) as ActionView).performClick()

        adapter.notifyDataSetChanged()

        assertTrue((recycler.getChildAt(0) as ActionView).isActiveState)
        assertTrue((recycler.getChildAt(1) as ActionView).isActiveState)
        assertFalse((recycler.getChildAt(2) as ActionView).isActiveState)
    }


    private fun newAdapter(): ActionsHomeRecyclerViewAdapter {
        val list = ListForActionsAdapterImpl()
        val mover = ChangeActiveStateActionsMoverImpl(list)
        val adapter = ActionsRecyclerViewAdapter(activity, list, mover)
        return adapter
    }
}