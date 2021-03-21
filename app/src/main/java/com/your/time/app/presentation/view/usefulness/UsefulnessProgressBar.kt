package com.your.time.app.presentation.view.usefulness

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.your.time.app.R

class UsefulnessProgressBar : FrameLayout {

    private lateinit var usefulness: ProgressBar
    private lateinit var usefulnessPercents: TextView

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    private fun init(){
        View.inflate(context, R.layout.usefulness_progress_bar, this)
        layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        usefulness = findViewById(R.id.usefulness_progress_bar)
        usefulnessPercents = findViewById(R.id.usefulness_percents)
    }

    fun setUsefulness(percent: Float){
        val percents = percent.toInt()
        usefulness.progress = percents
        usefulnessPercents.text = "$percents%"
    }
}