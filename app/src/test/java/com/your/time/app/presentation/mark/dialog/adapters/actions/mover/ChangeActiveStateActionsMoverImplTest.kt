package com.your.time.app.presentation.mark.dialog.adapters.mover

import android.graphics.Color
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.mark.dialog.adapters.list.ListForActionsAdapter
import com.your.time.app.presentation.view.action.ActionView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ChangeActiveStateActionsMoverImplTest : Assert(){

    private lateinit var list: ListForActionsAdapter
    private lateinit var mover: ChangeActiveStateActionsMoverImpl

    @Before
    fun init(){
        list = Mockito.mock(ListForActionsAdapter::class.java)
        mover = ChangeActiveStateActionsMoverImpl(list)
    }

    @Test
    fun testInitValueForLastActiveItem_IsZero(){
        val newMover = ChangeActiveStateActionsMoverImpl(list)
        assertEquals(0, newMover.getCountActiveActions())
    }

    @Test
    fun testChangeActiveState_MovingView(){
        val view = newActionView()
        mover.changeActiveState(view, 1)
        Mockito.verify(list).move(1, 0)

        mover.changeActiveState(view, 0)
        Mockito.verify(list).move(0, 0)
    }

    @Test
    fun testChangeActiveState_ChangeState(){
        val view = newActionView()
        mover.changeActiveState(view, 0)
        assertTrue(view.isActiveState)

        mover.changeActiveState(view, 0)
        assertFalse(view.isActiveState)
    }

    private fun newActionView(): ActionView {
        val actionView = ActionView(RuntimeEnvironment.application)
        actionView.action = ActionModel("any action", color = Color.BLUE)
        return actionView
    }
}