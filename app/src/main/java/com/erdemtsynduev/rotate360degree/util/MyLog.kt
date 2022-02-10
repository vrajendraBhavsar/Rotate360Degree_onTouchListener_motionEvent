package com.erdemtsynduev.rotate360degree.util

import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException


//import android.widget.Toast;


//import android.widget.Toast;
/**
 * http://stackoverflow.com/questions/2018263/android-logging
 *
 * improved by Max You
 *
 */
object MyLog {
    const val DEBUG = "debug"
    var LEVEL = Log.DEBUG

    /**
     * switch:
     * outputSwitch = 0
     * no output
     * outputSwitch = 1
     * output by tagFilter
     * outputSwitch = 2
     * output all
     */
    var outputSwitch = 2
    var tagFilter = ""
    var log2FileSwitch = 0

    /**
     * Log到文件
     */
    const val logFileName = "GestureButton_log"
    var logFile: File? = null
    fun log2File(log: String) {
        logFile = File(
            MyConfig.pathRoot,
            logFileName
        )
        try {
            val bw = BufferedWriter(
                FileWriter(
                    logFile,
                    true
                )
            )
            bw.write(
                """
                    $log
                    
                    """.trimIndent()
            )
            bw.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    fun d(tag: String, msgFormat: String?, vararg args: Any?) {
        if (outputSwitch == 0) return
        if (outputSwitch == 1) {
            if (tagFilter != tag) {
                return
            }
        }
        if (LEVEL <= Log.DEBUG) {
            Log.d(tag, String.format(msgFormat!!, *args))
            if (log2FileSwitch == 1) {
                log2File(tag + ": " + String.format(msgFormat, *args))
            }
        }
    } //	static public void d(String tag, Throwable t, String msgFormat,
    //			Object... args) {
    //
    //		if(output != 1)
    //			return;
    //
    //		if (LEVEL <= android.util.Log.DEBUG) {
    //			android.util.Log.d(tag, String.format(msgFormat, args), t);
    //		}
    //	}
}