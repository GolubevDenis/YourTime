package com.your.time.app.presentation.view.time

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import com.your.time.app.R
import com.your.time.app.domain.model.Weekdays

class WeekdaysView : ConstraintLayout {

    var weekdays: Weekdays? = null
        set(value) {
            field = value
            if(value != null)
                setupWeekdays() }

    private lateinit var monday: TextView
    private lateinit var tuesday: TextView
    private lateinit var wednesday: TextView
    private lateinit var thursday: TextView
    private lateinit var friday: TextView
    private lateinit var saturday: TextView
    private lateinit var sunday: TextView

    private fun setupWeekdays(){
        val week = weekdays!!

        if(week.isMonday) monday.setBackgroundResource(R.drawable.active_day)
        else monday.setBackgroundResource(R.drawable.inactive_day)

        if(week.isTuesday) tuesday.setBackgroundResource(R.drawable.active_day)
        else tuesday.setBackgroundResource(R.drawable.inactive_day)

        if(week.isWednesday) wednesday.setBackgroundResource(R.drawable.active_day)
        else wednesday.setBackgroundResource(R.drawable.inactive_day)

        if(week.isThursday) thursday.setBackgroundResource(R.drawable.active_day)
        else thursday.setBackgroundResource(R.drawable.inactive_day)

        if(week.isFriday) friday.setBackgroundResource(R.drawable.active_day)
        else friday.setBackgroundResource(R.drawable.inactive_day)

        if(week.isSaturday) saturday.setBackgroundResource(R.drawable.active_day)
        else saturday.setBackgroundResource(R.drawable.inactive_day)

        if(week.isSunday) sunday.setBackgroundResource(R.drawable.active_day)
        else sunday.setBackgroundResource(R.drawable.inactive_day)
    }

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    private fun init(){
        View.inflate(context, R.layout.weekdays, this)
        layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        monday = findViewById(R.id.is_monday)
        tuesday = findViewById(R.id.is_tuesday)
        wednesday = findViewById(R.id.is_wednesday)
        thursday = findViewById(R.id.is_thursday)
        friday = findViewById(R.id.is_friday)
        saturday = findViewById(R.id.is_saturday)
        sunday = findViewById(R.id.is_sunday)
    }
}