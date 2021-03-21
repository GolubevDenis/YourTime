package com.your.time.app.services.notification

import com.your.time.app.domain.services.NotificatorService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.your.time.app.R
import com.your.time.app.presentation.main.MainActivity
import android.app.NotificationChannel
import android.os.Build


class NotificatorServiceImpl(
        private val context: Context
) : NotificatorService {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private companion object {
        const val APP_CHANNEL_ID = "com.golubev.denis.clife"
        const val DEFAULT_ICON_ID = R.mipmap.ic_launcher

        const val MARK_TIME_NOTIFICATION_ID = 1
        const val MARK_TIME_ICON_ID = R.mipmap.ic_launcher
    }

    override fun simpleNotification(
            title: String, text: String, iconId: Int?, channelId: String?, notificationId: Int?, isNeedToClose: Boolean?
    ) {
        val mIconId = iconId ?: DEFAULT_ICON_ID
        val mChannelId = channelId ?: APP_CHANNEL_ID
        val mNotificationId = notificationId ?: MARK_TIME_NOTIFICATION_ID

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(mChannelId, name, importance)
            mNotificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, mChannelId)
                .setSmallIcon(mIconId)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(isNeedToClose ?: false)

        val resultIntent = Intent(context, MainActivity::class.java)
        val notifyPendingIntent = PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(notifyPendingIntent)

        mNotificationManager.notify(mNotificationId, builder.build())

    }

    override fun markTimeNotification(title: String, text: String){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(APP_CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, APP_CHANNEL_ID)
                .setSmallIcon(MARK_TIME_ICON_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)

        val resultIntent = Intent(context, MainActivity::class.java)
        val notifyPendingIntent = PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(notifyPendingIntent)

        notificationManager.notify(MARK_TIME_NOTIFICATION_ID, builder.build())
    }

    override fun closeMarkTimeNotification() {
        notificationManager.cancel(MARK_TIME_NOTIFICATION_ID)
    }
}