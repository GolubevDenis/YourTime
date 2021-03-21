package com.your.time.app.domain.exceptions

class TimePeriodConflictsException(
        timePeriod1StartTime: Int,
        timePeriod1EndTime: Int,
        timePeriod2StartTime: Int,
        timePeriod2EndTime: Int
) : Exception("Time period [$timePeriod1StartTime - $timePeriod1EndTime] " +
        "conflicts with [$timePeriod2StartTime - $timePeriod2EndTime]")