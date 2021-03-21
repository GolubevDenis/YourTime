package com.your.time.app.presentation.view.time_period

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.your.time.app.R
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.explansions.gone
import com.your.time.app.presentation.explansions.visible
import com.your.time.app.presentation.view.action.ActionView

class TimePeriodView(
        context: Context,
        private val timeUtil: TimeUtil
) : FrameLayout(context), TimePeriodModel.TimePeriodChangingListener {

    var timePeriod: TimePeriodModel = DEFAULT_TIME_PERIOD
        set(value) {
            unsubscribeAndSubscribe(timePeriod, value)
            setupTimePeriod(value)
            field = value
        }

    init {
        init()
        setupTimePeriod(timePeriod)
    }

    private fun unsubscribeAndSubscribe(oldTimePeriod: TimePeriodModel, newTimePeriod: TimePeriodModel){
        oldTimePeriod.removeChangeListener(this)
        newTimePeriod.addChangeListener(this)
    }

    companion object {
        private val DEFAULT_ACTION = ActionModel("run", color = Color.BLUE)
        val DEFAULT_TIME_PERIOD = TimePeriodModel(DEFAULT_ACTION, 0, 30)
    }

    private lateinit var minus5M: Button
    private lateinit var minus20M: Button
    private lateinit var minus1H: Button
    private lateinit var minus4H: Button
    private lateinit var plus5M: Button
    private lateinit var plus20M: Button
    private lateinit var plus1H: Button
    private lateinit var plus4H: Button

    private lateinit var actionView: ActionView
    private lateinit var timeIntervalView: TextView
    private lateinit var timeDurationView: TextView
    private lateinit var closeView: ImageView

    var isFull: Boolean = false
        private set

    var onCloseClickListener: OnCloseClickListener? = null
    interface OnCloseClickListener{
        fun onClickClose(timePeriod: TimePeriodModel)
    }

    private fun setupTimePeriod(timePeriod: TimePeriodModel) {
        actionView.action = timePeriod.action
        actionView.isActiveState = true
        timeIntervalView.text = timeUtil.getTextTimeInterval(timePeriod)
        timeDurationView.setText(timeUtil.getTextTimeDuration(timePeriod))
    }

    private fun init(){
        View.inflate(context, R.layout.action_time_view, this)

        minus5M = findViewById(R.id.minus5M)
        minus20M = findViewById(R.id.minus20M)
        minus1H = findViewById(R.id.minus1H)
        minus4H = findViewById(R.id.minus4H)
        plus5M = findViewById(R.id.plus5M)
        plus20M = findViewById(R.id.plus20M)
        plus1H = findViewById(R.id.plus1H)
        plus4H = findViewById(R.id.plus4H)

        minus5M.setOnClickListener {
            timePeriod.minusDurationInMinutes(5)
            timePeriod.notifyDataChanged()
        }
        minus20M.setOnClickListener {
            timePeriod.minusDurationInMinutes(20)
            timePeriod.notifyDataChanged()
        }
        minus1H.setOnClickListener {
            timePeriod.minusDurationInMinutes(60)
            timePeriod.notifyDataChanged()
        }
        minus4H.setOnClickListener {
            timePeriod.minusDurationInMinutes(60 * 4)
            timePeriod.notifyDataChanged()
        }
        plus5M.setOnClickListener {
            timePeriod.plusDurationInMinutes(5)
            timePeriod.notifyDataChanged()
        }
        plus20M.setOnClickListener {
            timePeriod.plusDurationInMinutes(20)
            timePeriod.notifyDataChanged()
        }
        plus1H.setOnClickListener {
            timePeriod.plusDurationInMinutes(60)
            timePeriod.notifyDataChanged()
        }
        plus4H.setOnClickListener {
            timePeriod.plusDurationInMinutes(60 * 4)
            timePeriod.notifyDataChanged()
        }

        actionView = findViewById(R.id.action)
        timeIntervalView = findViewById(R.id.timePeriodText)
        closeView = findViewById(R.id.close)
        timeDurationView = findViewById(R.id.duration)

        closeView.setOnClickListener {
            onCloseClickListener?.onClickClose(timePeriod)
        }

        showMini()
        setOnClickListener {
            switchSize()
        }

        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun switchSize(){
        if(isFull) showMini()
        else showFull()
    }

    fun showFull() {
        minus5M.visible()
        minus20M.visible()
        minus1H.visible()
        minus4H.visible()
        plus5M.visible()
        plus20M.visible()
        plus1H.visible()
        plus4H.visible()
        isFull= true
    }

    fun showMini() {
        minus5M.gone()
        minus20M.gone()
        minus1H.gone()
        minus4H.gone()
        plus5M.gone()
        plus20M.gone()
        plus1H.gone()
        plus4H.gone()
        isFull = false
    }

    override fun onTimePeriodChanged(timePeriod: TimePeriodModel) {
        setupTimePeriod(timePeriod)
    }
}