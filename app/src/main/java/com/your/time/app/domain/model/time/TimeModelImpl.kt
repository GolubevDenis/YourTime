package com.your.time.app.domain.model.time

import com.your.time.app.domain.exceptions.TimeBoundException
import com.your.time.app.domain.exceptions.TimePeriodConflictsException
import com.your.time.app.domain.model.TimePeriodModel

class TimeModelImpl : TimeModel, TimePeriodModel.TimePeriodChangingListener {

    interface OnTimePeriodsChangedListener {
        fun onTimePeriodUpdated(timePeriod: TimePeriodModel, index: Int)
        fun onTimePeriodAdded(timePeriod: TimePeriodModel)
        fun onTimePeriodRemoved(timePeriod: TimePeriodModel)
    }
    private val countTimePeriodsChangeListeners = arrayListOf<OnTimePeriodsChangedListener>()
    override fun addOnTimePeriodsChangedListener(listener: OnTimePeriodsChangedListener){
        if(!countTimePeriodsChangeListeners.contains(listener))
            countTimePeriodsChangeListeners.add(listener)
    }
    override fun removeOnTimePeriodsChangedListener(listener: OnTimePeriodsChangedListener){
        countTimePeriodsChangeListeners.remove(listener)
    }
    override fun getOnTimePeriodsChangedListeners() = countTimePeriodsChangeListeners
    private fun notifyTimePeriodUpdated(timePeriod: TimePeriodModel){
        timePeriod.notifyDataChangedBy(this)
        val index = getTimePeriods().indexOf(timePeriod)
        countTimePeriodsChangeListeners.forEach { it.onTimePeriodUpdated(timePeriod, index) }
    }
    private fun notifyTimePeriodAdded(timePeriod: TimePeriodModel) {
        countTimePeriodsChangeListeners.forEach { it.onTimePeriodAdded(timePeriod) }
    }
    private fun notifyTimePeriodRemoved(timePeriod: TimePeriodModel) {
        countTimePeriodsChangeListeners.forEach { it.onTimePeriodRemoved(timePeriod) }
    }


    private val listPeriods = arrayListOf<TimePeriodModel>()

    override fun putPeriod(timePeriod: TimePeriodModel) {
        checkConflict(timePeriod)
        listPeriods.add(timePeriod)
        listPeriods.sortBy { it.startTimeMinutes }

        timePeriod.addChangeListener(this)

        notifyTimePeriodAdded(timePeriod)
    }

    private fun checkConflict(timePeriod: TimePeriodModel) {

        fun checkTimePeriodsConflicts(timePeriod: TimePeriodModel, timePeriod1: TimePeriodModel): Boolean{
            return timePeriod.startTimeMinutes in timePeriod1.startTimeMinutes until timePeriod1.endTimeMinutes ||
                    timePeriod1.startTimeMinutes in timePeriod.startTimeMinutes until timePeriod.endTimeMinutes
        }

        fun checkTimePeriodsConflictsWithBounds(timePeriod: TimePeriodModel): Boolean {

            getStartTimeBound()?.also {
                if(timePeriod.startTimeMinutes < it)
                    return true
            }

            getEndTimeBound()?.also {
                if(timePeriod.endTimeMinutes > it)
                    return true
            }
            return false
        }

        if(checkTimePeriodsConflictsWithBounds(timePeriod))
            throw TimeBoundException("Time period conflicts with time bounds.")

        getTimePeriods()
                .filter { it -> checkTimePeriodsConflicts(timePeriod, it) }
                .forEach { it -> throw TimePeriodConflictsException(timePeriod.startTimeMinutes, timePeriod.endTimeMinutes, it.startTimeMinutes, it.endTimeMinutes) }
    }

    override fun getTimePeriod(i: Int) = getTimePeriods()[i]

    override fun putPeriods(timePeriods: List<TimePeriodModel>) {
        timePeriods.forEach { putPeriod(it) }
    }

    override fun getTimePeriods(): List<TimePeriodModel> = listPeriods

    override fun getDuration(): Int {
        return getTimePeriods().map {
            it.getDurationInMinutes()
        }.sum()
    }

    override fun getMinStartTime(): Int {
        return if(getTimePeriods().isNotEmpty()) getTimePeriods().first().startTimeMinutes else 0
    }

    override fun getMaxEndTime(): Int {
        return if(getTimePeriods().isNotEmpty()) getTimePeriods().last().endTimeMinutes else 0
    }

    override fun clear() {
        listPeriods.clear()
    }

    override fun deletePeriod(timePeriod: TimePeriodModel) {
        listPeriods.remove(timePeriod)

        timePeriod.removeChangeListener(this)
        notifyTimePeriodRemoved(timePeriod)
    }

    override fun vacuum() {
        fun moveTimeBackwards(duration: Int){
            for(i in 0 until getTimePeriods().size){
                val period = listPeriods[i]
                period.startTimeMinutes -= duration
                period.endTimeMinutes -= duration
                notifyTimePeriodUpdated(period)
            }
        }

        if(getTimePeriods().isEmpty()) return

        for(i in 1 until listPeriods.size){
            val period1 = listPeriods[i - 1]
            val period2 = listPeriods[i]
            if(period1.endTimeMinutes < period2.startTimeMinutes){
                val startTime = period2.startTimeMinutes
                period2.startTimeMinutes = period1.endTimeMinutes
                period2.endTimeMinutes = startTime
                notifyTimePeriodUpdated(period2)
            }
        }

        if(getStartTimeBound() != null){
            val different = getTimePeriods().first().startTimeMinutes - getStartTimeBound()!!
            moveTimeBackwards(different)
        }
    }

    private var startTimeBound: Int? = null
    override fun getStartTimeBound(): Int? = startTimeBound
    override fun setStartTimeBound(startTimeBound: Int) {
        if(getTimePeriods().isNotEmpty() && getMinStartTime() < startTimeBound)
            throw TimeBoundException("Start time bound must not be less then max start time of time period or equals it.")

        this.startTimeBound = startTimeBound
    }

    private var endTimeBound: Int? = null
    override fun getEndTimeBound(): Int? = endTimeBound
    override fun setEndTimeBound(endTimeBound: Int) {
        if(getTimePeriods().isNotEmpty() && getMaxEndTime() > endTimeBound)
            throw TimeBoundException("End time bound must not be more then max end time of time period or equals it.")

        this.endTimeBound = endTimeBound
    }

    override fun onTimePeriodChanged(timePeriod: TimePeriodModel) {
        val index = getTimePeriods().indexOf(timePeriod)
        val isPeriodExists = index != -1
        if(!isPeriodExists || getTimePeriods().isEmpty()) return

        fun moveTimeForwards(startPosition: Int, duration: Int){
            for(i in startPosition until listPeriods.size){
                val period = listPeriods[i]
                period.startTimeMinutes += duration
                period.endTimeMinutes += duration
                notifyTimePeriodUpdated(period)
            }
        }

        fun moveTimeBackwards(startPosition: Int, duration: Int){
            for(i in 0 .. startPosition){
                val period = listPeriods[i]
                period.startTimeMinutes -= duration
                period.endTimeMinutes -= duration
                notifyTimePeriodUpdated(period)
            }
        }

        fun checkLastPeriods(){
            val lastTimePeriod = getTimePeriod(index - 1)

            val isStartTimeConflicts = timePeriod.startTimeMinutes < lastTimePeriod.endTimeMinutes
            val isStartTimeBoundIsNull = getStartTimeBound() == null

            if(!isStartTimeConflicts) return

            val duration = lastTimePeriod.endTimeMinutes - timePeriod.startTimeMinutes
            if(isStartTimeBoundIsNull){
                moveTimeBackwards(index - 1, duration)
            }
        }

        fun checkNextPeriods(){

            fun crop(){
                getTimePeriods().filter {
                    it.startTimeMinutes >= getEndTimeBound()!!
                }.forEach { deletePeriod(it) }
            }

            val nextTimePeriod = getTimePeriod(index + 1)
            val duration = timePeriod.endTimeMinutes - nextTimePeriod.startTimeMinutes
            moveTimeForwards(index + 1, duration)

            if(getEndTimeBound() != null) crop()
        }

        fun cropLast(){
            getTimePeriods().findLast {
                it.endTimeMinutes > getEndTimeBound()!!
            }!!.also {
                        it.endTimeMinutes = getEndTimeBound()!!
                        notifyTimePeriodUpdated(it)
                    }
        }

        val isExistsLastPeriod = index > 0
        if(isExistsLastPeriod) checkLastPeriods()

        val isExistsNextPeriod = index + 1 < getTimePeriods().size
        if(isExistsNextPeriod) checkNextPeriods()

        vacuum()

        val isExistsEndBound = getEndTimeBound() != null
        val isExistsPeriods = getTimePeriods().isNotEmpty()
        if(isExistsEndBound && isExistsPeriods){

            val isNecessaryCropLastPeriod = getTimePeriods().last().endTimeMinutes > getEndTimeBound()!!
            if(isNecessaryCropLastPeriod){
                cropLast()
            }
        }
    }
}