package com.mindinventory.rotate360degree.common.utils

import android.content.Context
import androidx.navigation.NavOptions
import com.mindinventory.rotate360degree.R


fun isApiCallRequired(currentItem: Int, totalItem: Int): Boolean {
    return totalItem > currentItem
}

fun log(message: Throwable?) {
    if (message?.message == null)
        return
/*    if (BuildConfig.DEBUG)
        Log.e("ERROR" + " -- TAG --> ", message.message!!)*/
}

fun buildAnimOptions(
    popupTo: Int?,
    inclusive: Boolean = false
): NavOptions {
    val builder = NavOptions.Builder()
        .setEnterAnim(R.anim.right_in)
        .setExitAnim(R.anim.left_out)
        .setPopEnterAnim(R.anim.left_in)
        .setPopExitAnim(R.anim.right_out)
    if (popupTo != null) builder.setPopUpTo(popupTo, inclusive)
    return builder.build()
}

fun pxToDp(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}

fun dpToPx(context: Context, dp: Float): Float {
    return dp * context.resources.displayMetrics.density
}