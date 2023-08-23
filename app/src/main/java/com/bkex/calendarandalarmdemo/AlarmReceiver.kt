package com.bkex.calendarandalarmdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * Created by Lei Guoting on 2023/8/23.
 */
private const val CHANNEL_ID = "AlarmNotification"
private const val notificationId = 0x01

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val time = it.getStringExtra("time")
            Log.d(TAG, "#onReceive# 闹钟时间到: $time")
            context?.let { cxt ->
                time?.let { ti ->
                    createNotification(cxt, ti)
                }
            }
        }
    }

    private fun createNotification(context: Context, time: String) {
        createNotificationChannel(context)
        var builder =
            NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("时间到点通知").setContentText("$time, 该xxx了")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "AlarmNotification"
            val descriptionText = "Alarm-Notification-test"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}