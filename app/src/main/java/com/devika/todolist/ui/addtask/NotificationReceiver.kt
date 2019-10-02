package com.devika.todolist.ui.addtask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.devika.todolist.R

class NotificationReceiver : BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager
    lateinit var builder: NotificationCompat.Builder
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Fragment", "Alarm Received")

        val task = intent?.getStringExtra(TASK)

        if (task != null) {
            notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            builder = NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.todo)
                .setContentTitle("Todo Task")
                .setContentText(task)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(task))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("1", "A", importance).apply {
                    description = "B"
                }
                // Register the channel with the system
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(123, builder.build())
        }


    }

    companion object {
        const val TASK="task"
    }
}