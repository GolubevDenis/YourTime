package com.your.time.app.domain.model

import com.your.time.app.domain.exceptions.ConstructTimePeriodModelException
import com.your.time.app.domain.model.actions.ActionModel

data class TimePeriodModel(
        var action: ActionModel,
        var startTimeMinutes: Int,
        var endTimeMinutes: Int,
        var comment: String? = null
) {

    init {
        if(endTimeMinutes < startTimeMinutes){
            throw ConstructTimePeriodModelException("End time must be more then start time." +
                    " \n Start time = $startTimeMinutes, end time = $endTimeMinutes")
        }
        if(endTimeMinutes < 0 || startTimeMinutes < 0){
            throw ConstructTimePeriodModelException("Start time and end time must be positive." +
                    "\n Start time = $startTimeMinutes, end time = $endTimeMinutes")
        }
    }

    fun getDurationInMinutes(): Int {
        return endTimeMinutes - startTimeMinutes
    }

    fun setDurationInMinutesDeposeEndTime(minutes: Int) {
        endTimeMinutes = startTimeMinutes + minutes
    }

    fun setDurationInMinutesDeposeStartTime(minutes: Int) {
        startTimeMinutes = endTimeMinutes - minutes
    }

    private val listeners = arrayListOf<TimePeriodChangingListener>()
    fun getChangingListeners(): List<TimePeriodChangingListener> = listeners
    fun removeChangeListener(listener: TimePeriodChangingListener) { listeners.remove(listener) }
    interface TimePeriodChangingListener{
        fun onTimePeriodChanged(timePeriod: TimePeriodModel)
    }

    fun addChangeListener(listener: TimePeriodChangingListener) {
        if(!listeners.contains(listener))
            listeners.add(listener)
    }

    fun notifyDataChanged() {
        listeners.forEach { it.onTimePeriodChanged(this) }
    }

    fun notifyDataChangedBy(listener: TimePeriodChangingListener) {
        listeners
                .filter { it != listener }
                .forEach { it.onTimePeriodChanged(this) }
    }

    fun plusDurationInMinutes(minutes: Int) {
        minusDurationInMinutes(minutes * -1)
    }

    fun minusDurationInMinutes(minutes: Int) {
        val alpha = getDurationInMinutes() - minutes
        if(alpha > 0) setDurationInMinutesDeposeEndTime(alpha)
        else setDurationInMinutesDeposeEndTime(1)
    }
}