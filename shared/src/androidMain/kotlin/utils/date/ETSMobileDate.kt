@file:JvmName("ETSMobileDateAndroid")

package utils.date

import java.util.Calendar
import java.util.Date

/**
 * Creates a new [ETSMobileDate] from the specified [timestamp]
 *
 * @param timestamp Unix time (Number of Epoch milliseconds)
 */
actual fun ETSMobileDate(timestamp: Long?): ETSMobileDate {
    return Calendar.getInstance().toETSMobileDate(timestamp)
}

/**
 * Creates a new [ETSMobileDate] from the provided date/time components
 */
actual fun ETSMobileDate(
    seconds: Int,
    minutes: Int,
    hours: Int,
    dayOfMonth: Int,
    month: Month,
    year: Int
): ETSMobileDate = Calendar.getInstance().apply {
    set(Calendar.YEAR, year)
    set(Calendar.MONTH, month.ordinal)
    set(Calendar.DAY_OF_MONTH, dayOfMonth)
    set(Calendar.HOUR_OF_DAY, hours)
    set(Calendar.MINUTE, minutes)
    set(Calendar.SECOND, seconds)
    set(Calendar.MILLISECOND, 0)
}.toETSMobileDate(null)

private fun Calendar.toETSMobileDate(timestamp: Long?): ETSMobileDate {
    timestamp?.let { timeInMillis = it }

    val seconds = get(Calendar.SECOND)
    val minutes = get(Calendar.MINUTE)
    val hours = get(Calendar.HOUR_OF_DAY)

    val numberOfDay = (get(Calendar.DAY_OF_WEEK) + 7 - 2) % 7
    val dayOfWeek = WeekDay.values()[numberOfDay]

    val dayOfMonth = get(Calendar.DAY_OF_MONTH)
    val dayOfYear = get(Calendar.DAY_OF_YEAR)

    val month = Month.values()[get(Calendar.MONTH)]
    val year = get(Calendar.YEAR)

    return ETSMobileDate(
        seconds,
        minutes,
        hours,
        dayOfWeek,
        dayOfMonth,
        dayOfYear,
        month,
        year,
        timeInMillis
    )
}

/**
 * Creates a new [ETSMobileDate] which represents the current time
 */
actual fun ETSMobileDate.now() = ETSMobileDate(Date().time)

fun ETSMobileDate.toJvmDate(): Date = Date(timeInMilliseconds)