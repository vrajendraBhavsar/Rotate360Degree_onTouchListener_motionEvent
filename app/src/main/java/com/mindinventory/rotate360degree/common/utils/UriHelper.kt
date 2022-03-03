package com.mindinventory.rotate360degree.common.utils

import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes

object UriHelper {
    fun getNotificationSoundUri(context: Context, @RawRes notificationSoundFile : Int): Uri {
        return Uri.parse("android.resource://" + context.packageName + "/" + notificationSoundFile)
    }
}