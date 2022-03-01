package com.erdemtsynduev.rotate360degree.common.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    fun show(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, length).show()
    }

    fun show(context: Context, message: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, length).show()
    }
}
