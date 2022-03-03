package com.mindinventory.rotate360degree.common.extensions

import android.database.Cursor

fun Cursor.column(columnName: String) = this.getColumnIndex(columnName)
fun Cursor.intValue(columnName: String): Int = this.getInt(column(columnName))
fun Cursor.floatValue(columnName: String): Float = this.getFloat(column(columnName))
fun Cursor.stringValue(columnName: String): String = this.getString(column(columnName))
fun Cursor.doubleValue(columnName: String): Double = this.getDouble(column(columnName))
fun Cursor.longValue(columnName: String): Long = this.getLong(column(columnName))
