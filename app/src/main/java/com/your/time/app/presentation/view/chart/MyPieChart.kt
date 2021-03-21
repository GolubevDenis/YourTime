package com.your.time.app.presentation.view.chart

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.your.time.app.R
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.explansions.color
import com.your.time.app.presentation.explansions.invisible
import com.your.time.app.presentation.explansions.visible

class MyPieChart : PieChart {

    constructor(context: Context) : super(context){
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        initView()
    }

    private fun initView(){
        setDrawEntryLabels(false)
        setDrawCenterText(false)
        isDrawHoleEnabled = false
        rotationAngle = -90f
        isRotationEnabled = false
        isHighlightPerTapEnabled = false
        description = Description().also { it.text = "" }
        //            mChart.setOnChartValueSelectedListener(this);
        defineVisibility()
    }

    private var cachedPeriods: List<TimePeriodModel>? = null
    fun showTimePeriods(periods: List<TimePeriodModel>) {
        cachedPeriods = periods

        val colors = ArrayList<Int>()

        val entries = ArrayList<PieEntry>()
        periods
                .sortedBy { it.startTimeMinutes }
                .forEach {
                    val value = it.getDurationInMinutes().toFloat()
                    val label = it.action.action
                    val entry: PieEntry
                    entry = if (it.action.getIconId(context) == null) {
                        PieEntry(value, label)
                    } else {
                        val icon = ContextCompat.getDrawable(context!!, it.action.getIconId(context)!!)
                        PieEntry(value, label, icon)
                    }
                    entries.add(entry)

                    if(isUsefulnessMode){
                        val color = when(it.action.usefulness){
                            ActionModel.Usefulness.VERY_USEFULLY -> context.color(R.color.action_view_very_useful)
                            ActionModel.Usefulness.USEFULLY -> context.color(R.color.action_view_useful)
                            ActionModel.Usefulness.NEUTRALLY -> context.color(R.color.action_view_neutrally)
                            ActionModel.Usefulness.HARMFULLY -> context.color(R.color.action_view_harmfully)
                            ActionModel.Usefulness.VERY_HARMFULLY -> context.color(R.color.action_view_very_harmfully)
                            else -> context.color(R.color.light_gray)
                        }
                        colors.add(color)
                    } else {
                        colors.add(it.action.color)
                    }
                }

        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 0f
        dataSet.setDrawValues(false)
        dataSet.setDrawIcons(false)

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(false)
        this.data = data
        this.legend.isEnabled = false

        this.invalidate()
        defineVisibility()
    }

    private fun defineVisibility() {
        if(data == null || data.entryCount == 0) invisible()
        else visible()
    }

    private fun update(){
        if(cachedPeriods != null)
            showTimePeriods(cachedPeriods!!)
    }

    private var isUsefulnessMode = false
    fun switchToUsefulnessMode(){
        isUsefulnessMode = true
        update()
    }
    fun switchToNormalMode(){
        isUsefulnessMode = false
        update()
    }
}