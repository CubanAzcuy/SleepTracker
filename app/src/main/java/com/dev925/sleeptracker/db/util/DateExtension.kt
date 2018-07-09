package com.dev925.sleeptracker.db.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robertgross on 4/14/18.
 */

fun Date.atEndOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.time
}

fun Date.atStartOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

fun Date?.toString(format: String) : String {
    if(this == null) return ""
    val df = SimpleDateFormat(format, Locale.US)
    return df.format(this)
}
