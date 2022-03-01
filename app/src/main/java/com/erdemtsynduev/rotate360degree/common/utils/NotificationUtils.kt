package com.erdemtsynduev.rotate360degree.common.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtils {

    const val NOTIFICATION_TITLE = "TITLE"
    const val NOTIFICATION_DESCRIPTION = "DESCRIPTION"
    const val TEXT_NOTIFICATION_ID = 1234
    const val NOTIFICATION_CHANNEL_TEXT_ID = "NOTIFICATION_CHANNEL_ID"
    const val NOTIFICATION_CHANNEL_TEXT_MESSAGE = "Text Message"
    const val NOTIFICATION_CHANNEL_TEXT_DESCRIPTION = "This is text message related notification"

    @SuppressLint("ServiceCast")
    fun createNotificationChannel(
        context: Context,
        notificationChannelName: String,
        notificationChannelDescription: String,
        notificationChannelId: String,
        notificationChannelImportance: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannelCompat = NotificationChannelCompat.Builder(
                notificationChannelId,
                notificationChannelImportance
            ).setName(notificationChannelName)
                .setDescription(notificationChannelDescription)
                .build()

            NotificationManagerCompat.from(context)
                .createNotificationChannel(notificationChannelCompat)
        }
    }

    fun postTextNotification(
        context: Context,
        notificationTitle: String, notificationDescription: String,
        isNotificationSoundRequired: Boolean = false,
        @RawRes notificationSoundRes: Int?,
        @DrawableRes notificationSmallIcon: Int,
        isLargeIconRequired: Boolean = false,
        notificationLargeIcon: Bitmap? = null,
        isActionRequired: Boolean = false,
        actionIntent: Intent? = null,
        notificationId: Int,
        notificationChannelName: String,
        notificationChannelDescription: String,
        notificationChannelId: String,
        notificationChannelImportance: Int = NotificationManagerCompat.IMPORTANCE_DEFAULT
    ) {
        createNotificationChannel(
            context = context,
            notificationChannelName = notificationChannelName,
            notificationChannelDescription = notificationChannelDescription,
            notificationChannelId = notificationChannelId,
            notificationChannelImportance = notificationChannelImportance
        )

        val notificationCompat = NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(notificationTitle)
            .setContentText(notificationDescription)
            .setSmallIcon(notificationSmallIcon)

        if (isLargeIconRequired && notificationLargeIcon != null) {
            notificationCompat.setLargeIcon(notificationLargeIcon)
        }

        if (isActionRequired && actionIntent != null) {
            val pendingIntent = PendingIntent.getActivity(context, 0, actionIntent, 0)
            notificationCompat.setContentIntent(pendingIntent)
        }

        if (isNotificationSoundRequired && notificationSoundRes != null) {
            val notificationSoundUri = UriHelper.getNotificationSoundUri(
                context = context,
                notificationSoundFile = notificationSoundRes
            )
            notificationCompat.setSound(notificationSoundUri)
        }

        NotificationManagerCompat.from(context).notify(notificationId, notificationCompat.build())
    }
}