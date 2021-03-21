package com.your.time.app.presentation.view.action

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.your.time.app.R
import com.your.time.app.domain.model.actions.ActionModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ActionViewTest : Assert() {


    @Test
    fun testCreating(){
        val view = ActionView(RuntimeEnvironment.application)
        assertNotNull(view)
    }

    @Test
    fun testSetActionModel(){
        val view = ActionView(RuntimeEnvironment.application)

        val action = ActionModel("any action", "imageId", color = Color.BLUE)
        view.action = action

        assertNotNull(action.action)
        assertEquals(action.action, view.findViewById<TextView>(R.id.text).text.toString())
        assertEquals(view.findViewById<ImageView>(R.id.icon).visibility, View.VISIBLE)
    }

    @Test
    fun testSetActionModelWithoutIcon(){
        val view = ActionView(RuntimeEnvironment.application)

        val action = ActionModel("any action", color = Color.BLUE)
        view.action = action

        assertEquals(view.findViewById<ImageView>(R.id.icon).visibility, View.GONE)
    }

    @Test
    fun testSetBackgroundWhenActionUsefulnessIsUsefully(){
        val view = ActionView(RuntimeEnvironment.application)

        val action = ActionModel("any action", usefulness = ActionModel.Usefulness.USEFULLY, color = Color.BLUE)
        view.action = action

        assertEquals(view.backgroundResourceId, R.drawable.action_usefully)
    }

    @Test
    fun testSetBackgroundWhenActionUsefulnessIsNeutrally(){
        val view = ActionView(RuntimeEnvironment.application)

        val action = ActionModel("any action", usefulness = ActionModel.Usefulness.NEUTRALLY, color = Color.BLUE)
        view.action = action

        assertEquals(view.backgroundResourceId, R.drawable.action_neutrally)
    }

    @Test
    fun testSetBackgroundWhenActionUsefulnessIsHarmfully(){
        val view = ActionView(RuntimeEnvironment.application)

        val action = ActionModel("any action", usefulness = ActionModel.Usefulness.HARMFULLY, color = Color.BLUE)
        view.action = action

        assertEquals(view.backgroundResourceId, R.drawable.action_harmfully)
    }

    @Test
    fun testIsActiveState_InitialValueIsFalse(){
        val view = ActionView(RuntimeEnvironment.application)

        val action = ActionModel("any action", usefulness = ActionModel.Usefulness.NEUTRALLY, color = Color.BLUE)
        view.action = action

        assertEquals(view.backgroundResourceId, R.drawable.action_neutrally)
        assertFalse(view.isActiveState)
    }

    @Test
    fun testSetIsActiveState_ChangeBackground(){
        val view = ActionView(RuntimeEnvironment.application)

        val action = ActionModel("any action", usefulness = ActionModel.Usefulness.NEUTRALLY, color = Color.BLUE)
        view.action = action

        view.isActiveState = true
        assertEquals(view.backgroundResourceId, R.drawable.action_neutrally_active)

        view.isActiveState = false
        assertEquals(view.backgroundResourceId, R.drawable.action_neutrally)
    }
}
