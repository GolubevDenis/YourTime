package com.your.time.app.domain.exceptions

class TooManyActionsForPeriodException(
        duration: Int, countActions: Int
) : Exception("Too many actions for this period of time. \n Duration is $duration, count actions is $countActions")