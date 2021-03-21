package com.your.time.app.presentation.mark.dialog.adapters.actions

import android.graphics.Color
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.view.action.ActionView
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ActionHolderTest {

    @Test
    fun testBind_SetActionIntoView(){
        val view = ActionView(RuntimeEnvironment.application)
        val holder = ActionHomeHolder(view)

        val action = ActionModel("any action", color = Color.BLUE)
        holder.bind(action)

        Assert.assertEquals(action, view.action)
    }
}