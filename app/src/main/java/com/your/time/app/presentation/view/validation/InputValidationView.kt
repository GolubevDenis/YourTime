package com.your.time.app.presentation.view.validation

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.TextView
import com.your.time.app.R

class InputValidationView : TextView {

    var validationState: ValidationLevel = DEFAULT_VALIDATION_STATE
        set(value) {
            field = value
            setupValidationState(validationState)
        }

    companion object {
        val DEFAULT_VALIDATION_STATE = ValidationLevel.VALID_LEVEL_3
    }

    constructor(context: Context): super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        init()
    }

    private fun init(){
        validationState = DEFAULT_VALIDATION_STATE
    }

    private fun setupValidationState(validationState: ValidationLevel) {
        when(validationState){
            ValidationLevel.VALID_LEVEL_5 -> setTextColor(ContextCompat.getColor(context, R.color.text_valid_level_5))
            ValidationLevel.VALID_LEVEL_4 -> setTextColor(ContextCompat.getColor(context, R.color.text_valid_level_4))
            ValidationLevel.VALID_LEVEL_3 -> setTextColor(ContextCompat.getColor(context, R.color.text_valid_level_3))
            ValidationLevel.VALID_LEVEL_2 -> setTextColor(ContextCompat.getColor(context, R.color.text_valid_level_2))
            ValidationLevel.VALID_LEVEL_1 -> setTextColor(ContextCompat.getColor(context, R.color.text_valid_level_1))
        }
    }
}