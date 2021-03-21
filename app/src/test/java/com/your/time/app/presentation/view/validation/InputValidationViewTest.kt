package com.your.time.app.presentation.view.validation

import android.content.Context
import android.support.v4.content.ContextCompat
import com.your.time.app.R
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class InputValidationViewTest {

    private lateinit var view: InputValidationView
    private lateinit var context: Context

    private var validLevel5Color: Int? = null
    private var validLevel4Color: Int? = null
    private var validLevel3Color: Int? = null
    private var validLevel2Color: Int? = null
    private var validLevel1Color: Int? = null

    @Before
    fun init(){
        context = RuntimeEnvironment.application
        view = InputValidationView(context)

        validLevel5Color = ContextCompat.getColor(context, R.color.text_valid_level_5)
        validLevel4Color = ContextCompat.getColor(context, R.color.text_valid_level_4)
        validLevel3Color = ContextCompat.getColor(context, R.color.text_valid_level_3)
        validLevel2Color = ContextCompat.getColor(context, R.color.text_valid_level_2)
        validLevel1Color = ContextCompat.getColor(context, R.color.text_valid_level_1)
    }

    @Test
    fun setCreate_DefaultIsNEUTRALLY() {
        val view = InputValidationView(context)
        assertEquals(validLevel3Color, view.currentTextColor)
    }

    @Test
    fun setValidState_VALID_LEVEL_1() {
        view.validationState = ValidationLevel.VALID_LEVEL_1
        assertEquals(validLevel1Color, view.currentTextColor)
    }

    @Test
    fun setValidState_VALID_LEVEL_2() {
        view.validationState = ValidationLevel.VALID_LEVEL_2
        assertEquals(validLevel2Color, view.currentTextColor)
    }

    @Test
    fun setValidState_VALID_LEVEL_3() {
        view.validationState = ValidationLevel.VALID_LEVEL_3
        assertEquals(validLevel3Color, view.currentTextColor)
    }

    @Test
    fun setValidState_VALID_LEVEL_4() {
        view.validationState = ValidationLevel.VALID_LEVEL_4
        assertEquals(validLevel4Color, view.currentTextColor)
    }

    @Test
    fun setValidState_VALID_LEVEL_5() {
        view.validationState = ValidationLevel.VALID_LEVEL_5
        assertEquals(validLevel5Color, view.currentTextColor)
    }
}