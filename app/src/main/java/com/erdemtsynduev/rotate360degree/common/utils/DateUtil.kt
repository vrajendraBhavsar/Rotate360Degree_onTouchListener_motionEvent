package com.erdemtsynduev.rotate360degree.common.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.ParseException
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.ConfigurationCompat
import com.erdemtsynduev.rotate360degree.common.extensions.append
import com.erdemtsynduev.rotate360degree.common.extensions.isRequiredField
import java.text.SimpleDateFormat
import java.util.*

const val APP_DATE_FORMAT = "dd/MM/yyyy"
const val MONTH_DATE_FORMAT = "MMMM dd"
const val MONTH_FORMAT = "MM-yyyy"
const val MONTH_FORMAT_NEW = "MMM-yyyy"
const val MONTH_FORMAT_APP = "MMM yyyy"
const val TIME_FORMAT = "HH:mm"
const val TIME_FORMAT_12_HOURS = "hh:mm a"
const val API_DATE_FORMAT = "yyyy-MM-dd"
const val API_TIME_FORMAT = "HH:mm:ss"
const val ISO_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val YYYY_MM_DD_HH_MM_SS_API = "yyyy-MM-dd, HH:mm:ss"
const val NOTIFICATION_DATE_FORMAT = "dd MMM yyyy hh:mm a"


@SuppressLint("SetTextI18n")
fun Context.showTimePicker(
    tv_date: AppCompatTextView,
    dateFormat: String = TIME_FORMAT,
    getSelectedDate: (hour: Int, minute: Int) -> Unit
) {
    /*var locale : Locale? = null
     locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
         this.resources.configuration.locales.get(0)
     } else {
         this.resources.configuration.locale
     }*/
    val cal = Calendar.getInstance(ConfigurationCompat.getLocales(this.resources.configuration).get(0))

    if (tv_date.text.toString().isRequiredField()) {
        val dateMillis = getMillisFromDate(tv_date.text.toString(), dateFormat)
        if (dateMillis != 0L) {
            cal.timeInMillis = dateMillis
        }
    }

    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        this,
        TimePickerDialog.OnTimeSetListener
        { _, selectedHour, selectedMinute ->
            getSelectedDate(selectedHour, selectedMinute)
        }, hour, minute, DateFormat.is24HourFormat(this)
    )
    timePickerDialog.show()
}


fun milliToDate(timeInMilliseconds: String, dateFormat: String): String {
    try {
        val l = java.lang.Long.parseLong(timeInMilliseconds)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = l
        val sdf = SimpleDateFormat(dateFormat)
        return sdf.format(calendar.time)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ""
}


fun getGmtTimeDiff(networkTime: Long?): Long {
    var timediff: Long = 0

    val deviceTime = getMilliSecFromGmt()

    networkTime?.let {
        if (networkTime > 0) {
            timediff = networkTime - deviceTime
        }
    }


    return timediff
}

fun getMilliSecFromGmt(): Long {
    val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
    return cal.timeInMillis
}

fun getMillisFromDate(str_date: String, dateFormat: String): Long {
    return try {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val date = formatter.parse(str_date)
        date.time
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

fun getChangedDateFormat(mDate: String, mOldDateFormat: String, newDateFormat: String): String {
    val inputFormat = SimpleDateFormat(mOldDateFormat, Locale.getDefault())
    val outputFormat = SimpleDateFormat(newDateFormat, Locale.getDefault())

    var date: Date? = null
    var str: String? = null

    try {
        date = inputFormat.parse(mDate)
        str = outputFormat.format(date)

        return str

    } catch (e: ParseException) {
        e.printStackTrace()
    } catch (e: java.text.ParseException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ""
}

fun getChangedDateFormatEnglish(
    mDate: String, mOldDateFormat: String,
    newDateFormat: String
): String {
    val inputFormat = SimpleDateFormat(mOldDateFormat, Locale.US)
    val outputFormat = SimpleDateFormat(newDateFormat, Locale.US)

    var date: Date? = null
    var str: String? = null

    try {
        date = inputFormat.parse(mDate)
        str = outputFormat.format(date)

        return str

    } catch (e: ParseException) {
        e.printStackTrace()
    } catch (e: java.text.ParseException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ""
}

fun pickPastDate(
    context: Context,
    startDate: AppCompatTextView? = AppCompatTextView(context),
    endDate: Calendar,
    tvDate: AppCompatTextView,
    tvTime: AppCompatTextView? = AppCompatTextView(context),
    dateFormat: String = APP_DATE_FORMAT,
    getSelectedDate: (date: String) -> Unit,
    futureDateOnly: Boolean = false
) {

    Locale.setDefault(context.resources.configuration.locale)
    val calendar = Calendar.getInstance()
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val maxDate = System.currentTimeMillis()

    if (tvDate.text.isNotEmpty()) {
        val mCurrentTime = tvDate.text.toString()
        if (mCurrentTime.isRequiredField()) {
            try {
                val mTime = getMillisFromDate(mCurrentTime, dateFormat)
                endDate.timeInMillis = mTime
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    } else {
        endDate.timeInMillis = maxDate
    }

    mYear = endDate.get(Calendar.YEAR)
    mMonth = endDate.get(Calendar.MONTH)
    mDay = endDate.get(Calendar.DAY_OF_MONTH)


    val datePickerDialog = DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)

            getSelectedDate(calendar.timeInMillis.toString())
            if (getMillisFromDate(tvTime?.text.toString(), TIME_FORMAT) != 0L) {
                tvTime?.text = ""
            }
        }, mYear, mMonth, mDay
    )

    if (startDate?.text.toString().isRequiredField()) {
        val formatter = SimpleDateFormat(APP_DATE_FORMAT, Locale.getDefault())
        val sDate = formatter.parse(startDate?.text.toString())
        datePickerDialog.datePicker.minDate = sDate.time + 86400000
    }

    if (futureDateOnly) datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    datePickerDialog.show()
}

fun pickPastDateIncludingCurrentDay(
    context: Context,
    startDate: String? = "",
    endDate: Calendar,
    tvDate: AppCompatTextView,
    tvTime: AppCompatTextView? = AppCompatTextView(context),
    dateFormat: String = APP_DATE_FORMAT,
    getSelectedDate: (date: String) -> Unit
) {

    Locale.setDefault(ConfigurationCompat.getLocales(context.resources.configuration).get(0))
    val calendar = Calendar.getInstance()
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val maxDate = System.currentTimeMillis()

    if (tvDate.text.isNotEmpty()) {
        val mCurrentTime = tvDate.text.toString()
        if (mCurrentTime.isRequiredField()) {
            try {
                val mTime = getMillisFromDate(mCurrentTime, dateFormat)
                endDate.timeInMillis = mTime
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    } else {
        endDate.timeInMillis = maxDate
    }

    mYear = endDate.get(Calendar.YEAR)
    mMonth = endDate.get(Calendar.MONTH)
    mDay = endDate.get(Calendar.DAY_OF_MONTH)


    val datePickerDialog = DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)

            getSelectedDate(calendar.timeInMillis.toString())
            if (getMillisFromDate(tvTime?.text.toString(), TIME_FORMAT) != 0L) {
                tvTime?.text = ""
            }
        }, mYear, mMonth, mDay
    )

    if (startDate.isRequiredField()) {
        val formatter = SimpleDateFormat(APP_DATE_FORMAT, Locale.US)
        val sDate = formatter.parse(startDate)
        datePickerDialog.datePicker.minDate = sDate.time
    }

    datePickerDialog.show()
}


fun Context.showDateTimePicker(
    context: Context,
    tv_date: AppCompatTextView,
    dateFormat: String = APP_DATE_FORMAT,
    futureDateOnly: Boolean = false,
    pastDateOnly: Boolean = false,
    getSelectedDate: (date: String) -> Unit,
    getSelectedTime: (hour: Int, minute: Int) -> Unit
) {

    Locale.setDefault(ConfigurationCompat.getLocales(context.resources.configuration).get(0))

    val cal = Calendar.getInstance()
    cal.timeInMillis = System.currentTimeMillis()

    if (tv_date.text.toString().isRequiredField()) {

        val dateMillis = getMillisFromDate(tv_date.text.toString(), dateFormat)

        if (dateMillis != 0L) {
            cal.timeInMillis = dateMillis
        }
    }

    val currentYear = cal.get(Calendar.YEAR)
    val currentMonth = cal.get(Calendar.MONTH)
    val currentDay = cal.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        this,
        { _, year, monthOfYear, dayOfMonth ->

            cal.set(year, monthOfYear, dayOfMonth)

            val mDate = getDateFromMillis("" + cal.timeInMillis, dateFormat)

//                tv_date.text = mDate
            getSelectedDate(cal.timeInMillis.toString())

        }, currentYear, currentMonth, currentDay
    )

    if (tv_date.text.toString().isRequiredField()) {
        val dateMillis = getMillisFromDate(tv_date.text.toString(), dateFormat)
        if (dateMillis != 0L) {
            cal.timeInMillis = dateMillis
        }
    }

    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        this,
        TimePickerDialog.OnTimeSetListener
        { _, selectedHour, selectedMinute ->
            getSelectedTime(selectedHour, selectedMinute)
        }, hour, minute, DateFormat.is24HourFormat(context)
    )
    timePickerDialog.show()

    if (futureDateOnly) datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    if (pastDateOnly) datePickerDialog.datePicker.minDate = System.currentTimeMillis()
    datePickerDialog.show()

}

fun getDateFromMillisForApi(mDateInMillis: String?, mDateFormat: String): String {

    var miliis: Long = 0

    if (mDateInMillis.isRequiredField()) {
        try {
            miliis = java.lang.Long.parseLong(mDateInMillis!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val formatter = SimpleDateFormat(mDateFormat, Locale.US)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = miliis

    return formatter.format(calendar.time)
}

fun getDateFromTimeStampForApi(mDateInMillis: String?, mDateFormat: String): String {

    var miliis: Long = 0

    if (mDateInMillis.isRequiredField()) {
        try {
            miliis = java.lang.Long.parseLong(mDateInMillis!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val formatter = SimpleDateFormat(mDateFormat, Locale.US)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = miliis * 1000L

    return formatter.format(calendar.time)
}


fun Context?.getDateFromMillis(mDateInMillis: String?, mDateFormat: String): String {
    if (this == null) return ""
    var miliis: Long = 0

    if (mDateInMillis != null && mDateInMillis.isNotEmpty()) {
        try {
            miliis = java.lang.Long.parseLong(mDateInMillis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val formatter = SimpleDateFormat(mDateFormat, this.resources.configuration.locale)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = miliis

    return formatter.format(calendar.time)
}


fun isYesterday(millis: String): Boolean {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = java.lang.Long.parseLong(millis)
    return DateUtils.isToday(calendar.timeInMillis + DateUtils.DAY_IN_MILLIS)
}

fun pickDatePicker(
    context: Context,
    startDate: Calendar,
    tvDate: AppCompatTextView,
    getSelectedDate: (date: String) -> Unit,
    isBirthDate: Boolean = false
) {
    val calendar = Calendar.getInstance()
    var mYear: Int
    var mMonth: Int
    var mDay: Int
    var maxDate = 0L
    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))

    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
    maxDate = calendar.timeInMillis

    mDay = startDate.get(Calendar.DAY_OF_MONTH)
    startDate.timeInMillis = maxDate
    mYear = startDate.get(Calendar.YEAR)
    mMonth = startDate.get(Calendar.MONTH)
    val cal2 = Calendar.getInstance()
    cal2.set(Calendar.YEAR, cal2.get(Calendar.YEAR) - 18)
    if (tvDate.text.isNotEmpty()) {
        val mCurrentTime = tvDate.text
        if (mCurrentTime.isNotEmpty()) {
            try {
                val mTime = getMillisFromDate(mCurrentTime.toString(), APP_DATE_FORMAT)
                startDate.timeInMillis = mTime
                mDay = startDate.get(Calendar.DAY_OF_MONTH)
                mYear = startDate.get(Calendar.YEAR)
                mMonth = startDate.get(Calendar.MONTH)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    val datePickerDialog = DatePickerDialog(
        context, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)

            getSelectedDate(calendar.timeInMillis.toString())
        }, mYear, mMonth, mDay
    )

    if (isBirthDate) {
        datePickerDialog.datePicker.maxDate = cal2.timeInMillis
    } else {
        datePickerDialog.datePicker.maxDate = maxDate
    }
    datePickerDialog.show()
}

fun getDateFromDays(minDays: String, maxDays: String): String {
    val minCal = Calendar.getInstance()
    val maxCal = Calendar.getInstance()
    minCal.time = Date()
    maxCal.time = Date()
    val currentMinMonth = minCal.get(Calendar.MONTH)
    val currentMaxMonth = minCal.get(Calendar.MONTH)
    minCal.add(Calendar.DAY_OF_MONTH, minDays.toInt())
    maxCal.add(Calendar.DAY_OF_MONTH, maxDays.toInt())
    val minMonth = minCal.get(Calendar.MONTH)
    val maxMonth = minCal.get(Calendar.MONTH)
    val fmt = Formatter()
    fmt.format("%tb", minCal)
    val fmtMax = Formatter()
    fmtMax.format("%tb", maxCal)
    if (currentMinMonth == minMonth && currentMaxMonth == maxMonth && fmt.toString() == fmtMax.toString()
    ) {
        return fmt.toString().append(" ").append(minCal.get(Calendar.DAY_OF_MONTH).toString())
            .append(" - ").append(maxCal.get(Calendar.DAY_OF_MONTH).toString())
    } else {
        return fmt.toString().append(" ").append(minCal.get(Calendar.DAY_OF_MONTH).toString())
            .append(" - ").append(fmtMax.toString()).append(" ")
            .append(maxCal.get(Calendar.DAY_OF_MONTH).toString())
    }
}

fun stringToDate(date: String, dateFormat: String): Date {
    var dtStart = Date()
    val format = SimpleDateFormat(dateFormat)
    try {
        dtStart = format.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return dtStart
}

fun dateToString(date: Date, dateFormat: String): String {
    var dateOfString = ""
    val dateFormat = SimpleDateFormat(dateFormat)
    try {
        dateOfString = dateFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return dateOfString
}


