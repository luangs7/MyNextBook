package com.lgdevs.mynextbook.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lgdevs.mynextbook.R
import com.lgdevs.mynextbook.cloudservices.messaging.Message
import kotlin.random.Random

@JvmInline
value class NotificationBuilder(private val context: Context) {
    fun onMessageReceive(message: Message) {
        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.default_notification_channel_id)
        )
            .setContentTitle(message.title)
            .setContentText(message.notification)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        with(NotificationManagerCompat.from(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(createChannel(context.getString(R.string.default_notification_channel_id)))
            }
            notify(Random.nextInt(99), notification)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createChannel(channelId: String) = NotificationChannel(
        channelId,
        "General Notifications",
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description = "This is default channel used for all other notifications"
    }
}
