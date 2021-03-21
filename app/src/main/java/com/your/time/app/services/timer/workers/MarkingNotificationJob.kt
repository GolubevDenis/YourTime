package com.your.time.app.services.timer.workers

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.*
import com.your.time.app.MyApplication
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.services.NotificatorService
import com.your.time.app.domain.services.PropertiesService
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.util.time.TimeUtil
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MarkingNotificationJob(
        private val context: Context,
        private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    companion object {
        const val TAG = "TimeForMarkingNotificationJob"
        const val DELAY = 5L

        fun schedule(delay: Long = DELAY) {

            cancel()

            val work = OneTimeWorkRequest
                    .Builder(MarkingNotificationJob::class.java)
                    .setInitialDelay(DELAY, TimeUnit.MINUTES)
                    .addTag(TAG)
                    .build()
            WorkManager.getInstance().cancelAllWorkByTag(TAG)
            WorkManager.getInstance().enqueue(work)
        }
        
        fun cancel() {
            WorkManager.getInstance().cancelAllWorkByTag(MarkingNotificationJob.TAG)
        }
    }

    @Inject lateinit var notificationService: NotificatorService
    @Inject lateinit var markInteractor: MarkInteractor
    @Inject lateinit var timeUtil: TimeUtil
    @Inject lateinit var resourcesService: ResourcesService
    @Inject lateinit var propertiesService: PropertiesService

    @SuppressLint("CheckResult")
    override fun doWork(): ListenableWorker.Result {

        MyApplication.getApplicationComponent().inject(this)

        if(!propertiesService.isNeedToNotifyMarking()) {
            schedule()
            return ListenableWorker.Result.SUCCESS
        }

        markInteractor.getUnmarkedTime()
                .subscribe { unmarkedTimeInMinutes ->

                    val notifyingTimeBorder = propertiesService.getNotifyingTimeBorder()
                    if(unmarkedTimeInMinutes >= notifyingTimeBorder){
                        val notificationTitle = resourcesService.getTitleMarkingNotification()
                        val unmarkedTimeText = timeUtil.getTextTimeDuration(unmarkedTimeInMinutes)
                        val notificationText = resourcesService.getTextMarkingNotification(unmarkedTimeText)
                        notificationService.markTimeNotification(notificationTitle, notificationText)

                        schedule(notifyingTimeBorder.toLong())
                    }else{
                        val delay = notifyingTimeBorder - unmarkedTimeInMinutes
                        schedule(delay.toLong())
                    }
                }

        return ListenableWorker.Result.SUCCESS
    }
}