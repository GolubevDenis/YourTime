package com.your.time.app.presentation.view.chart

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.your.time.app.domain.model.UsefulnessOfDay
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.explansions.invisible
import com.your.time.app.presentation.explansions.visible


class UsefulnessBarChart : BarChart {

    var timeUtil: TimeUtil? = null
    var format: Format = Format.DATE_WITH_SHORT_MONTH

    constructor(context: Context) : super(context){
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        initView()
    }

    private fun initView() {
        isHighlightPerTapEnabled = false
        description = Description().also { it.text = "" }
        setDrawBarShadow(true)
        //            mChart.setOnChartValueSelectedListener(this);
        setPinchZoom(false)
        setDrawGridBackground(false)
        setScaleEnabled(false)

        axisRight.isEnabled = false
        axisLeft.mAxisMaximum = 100f
        axisLeft.axisMaximum = 100f
        axisLeft.axisMinimum = 0f
        axisLeft.mAxisMinimum = 0f
    }

    fun showUsefulnessOfDays(usefulness: List<UsefulnessOfDay>) {

        val xAxisFormatter = DayAxisValueFormatter(usefulness, timeUtil, format)
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f

//        xAxis.setGranularity(1f) // only intervals of 1 day
//        xAxis.setLabelCount(7)
        xAxis.valueFormatter = xAxisFormatter


        val colors = ArrayList<Int>()
        colors.add(Color.GREEN)

        var x = 0f
        val entries = ArrayList<BarEntry>()
        usefulness
                .forEach {
                    val value = it.usefulness
                    val label = it.fullDate
                    val entry = BarEntry(x++, value, label)
                    entries.add(entry)
                }

        val dataSet = BarDataSet(entries, "")
        dataSet.setDrawValues(false)
        dataSet.setDrawIcons(false)

        dataSet.colors = colors

        val data = BarData(dataSet)
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

    enum class Format {
        WEEKDAY, SHORT_WEEKDAY, FULL, DATE_WITH_SHORT_MONTH
    }

    internal class DayAxisValueFormatter(
            private val data: List<UsefulnessOfDay>,
            private val timeUtil: TimeUtil? = null,
            private val format: Format
    ) : IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            val fullDate = data[value.toInt()].fullDate

             if(timeUtil == null)
                 return fullDate

            return when(format){
                Format.WEEKDAY -> timeUtil.getWeekday(fullDate)
                Format.SHORT_WEEKDAY -> timeUtil.getShortWeekday(fullDate)
                Format.FULL -> fullDate
                Format.DATE_WITH_SHORT_MONTH -> timeUtil.getDateWithShortMonthOnly(fullDate)
                else -> fullDate
            }
        }
    }

}
