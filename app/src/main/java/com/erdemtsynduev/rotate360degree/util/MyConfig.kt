package com.erdemtsynduev.rotate360degree.util

import android.content.Context
import android.os.Environment


object MyConfig {
    /**
     * 系统相关
     */
    var app: Context? = null
//    var gestureButton: GestureButtonShow? = null
    val pathRoot = Environment
        .getExternalStorageDirectory().path
    const val time_format = "yyyy-MM-dd"

    /**
     * 模式
     */
    var MODE_FIXED = 0
    var MODE_PATH = 1
    var MODE_LINE = 2
    var mode = MODE_LINE

    /**
     * 门限
     */
    var moveThreshold = 5 // 稍微move一段之后出现按钮
    var MODE_BASIC_GAP = 5 //FIXED模式下按钮的MOVE间隔
    var HandlerMargin = 0 //可以留若干间隔，防止手指挡住按钮

    class MovePath {
        var x = 0f
        var y = 0f
    }
}