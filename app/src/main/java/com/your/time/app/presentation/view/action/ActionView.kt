package com.your.time.app.presentation.view.action

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.your.time.app.R
import com.your.time.app.domain.model.actions.ActionModel


open class ActionView : LinearLayout {

    private lateinit var icon: ImageView
    private lateinit var text: TextView

    var backgroundResourceId: Int? = null
        private set

    var isActiveState: Boolean = false
        set(value) {
            field = value
            setupActiveBackground()
        }

    var action: ActionModel = DEFAULT_ACTION
        set(value) {
            field = value
            setupAction()
        }

    private companion object {
        val DEFAULT_ACTION = ActionModel("running", color = Color.BLUE)
    }

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    private fun setupAction() {
        text.text = action.action

        if(action.getIconId(context) != null){
            icon.setImageResource(action.getIconId(context)!!)
            icon.visibility = View.VISIBLE
        }else{
            icon.visibility = View.GONE
        }

        setupActiveBackground()
    }

    private fun init(){
        View.inflate(context, R.layout.action_view, this)

        icon = findViewById(R.id.icon)
        text = findViewById(R.id.text)

        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, resources.getDimension(R.dimen.action_view_height).toInt())

        setupAction()
    }

    private fun setupActiveBackground() {
        if(isActiveState) setActiveBackground()
        else setNotActiveBackground()
    }

    private fun setNotActiveBackground(){
        backgroundResourceId = when(action.usefulness){
            ActionModel.Usefulness.VERY_USEFULLY -> R.drawable.action_very_usefully
            ActionModel.Usefulness.USEFULLY -> R.drawable.action_usefully
            ActionModel.Usefulness.NEUTRALLY -> R.drawable.action_neutrally
            ActionModel.Usefulness.HARMFULLY -> R.drawable.action_harmfully
            ActionModel.Usefulness.VERY_HARMFULLY -> R.drawable.action_very_harmfully
            else -> R.drawable.action_neutrally
        }
        setBackgroundResource(backgroundResourceId!!)
    }

    private fun setActiveBackground(){
        backgroundResourceId = when(action.usefulness){
            ActionModel.Usefulness.VERY_USEFULLY -> R.drawable.action_very_usefully_active
            ActionModel.Usefulness.USEFULLY -> R.drawable.action_usefully_active
            ActionModel.Usefulness.NEUTRALLY -> R.drawable.action_neutrally_active
            ActionModel.Usefulness.HARMFULLY -> R.drawable.action_harmfully_active
            ActionModel.Usefulness.VERY_HARMFULLY -> R.drawable.action_very_harmfully_active
            else -> R.drawable.action_neutrally_active
        }
        setBackgroundResource(backgroundResourceId!!)
    }
}