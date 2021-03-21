package com.your.time.app.domain.services

import io.reactivex.Scheduler

interface SchedulersService {

    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
    fun newThread(): Scheduler
}