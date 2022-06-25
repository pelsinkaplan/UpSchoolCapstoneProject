package com.pelsinkaplan.upschoolcapstoneproject.service.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.ui.activity.AfterLoginActivity

/**
 * Created by Pelşin KAPLAN on 23.06.2022.
 */
class BagWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        createNotification()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification() {

        val builder: NotificationCompat.Builder

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "channelId"
        val channelName = "channelName"
        val channelIntroduction = "channelIntroduction"
        val channelPriority = NotificationManager.IMPORTANCE_HIGH

        var channel: NotificationChannel? = notificationManager
            .getNotificationChannel(channelId)

        if (channel == null) {
            channel = NotificationChannel(channelId, channelName, channelPriority)
            channel.description = channelIntroduction
            notificationManager.createNotificationChannel(channel)
        }

        builder = NotificationCompat.Builder(applicationContext, channelId)
        builder
            .setContentTitle("Hi!")
            .setContentText("You forgot a product in your bag.")
            .setSmallIcon(R.drawable.ic_logo)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
    }
}