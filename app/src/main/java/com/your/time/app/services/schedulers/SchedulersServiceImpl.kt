package com.your.time.app.services.schedulers

import com.your.time.app.domain.services.SchedulersService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersServiceImpl : SchedulersService {

    override fun ui(): io.reactivex.Scheduler = AndroidSchedulers.mainThread()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun io(): Scheduler = Schedulers.io()

    override fun newThread(): Scheduler = Schedulers.newThread()
}