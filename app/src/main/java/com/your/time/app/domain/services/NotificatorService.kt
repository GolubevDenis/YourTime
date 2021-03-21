package com.your.time.app.domain.services

interface NotificatorService {

    fun simpleNotification(
            title: String, text: String, iconId: Int?, channelId: String?, notificationId: Int?, isNeedToClose: Boolean?)

    fun markTimeNotification(title: String, text: String)
    fun closeMarkTimeNotification()
}