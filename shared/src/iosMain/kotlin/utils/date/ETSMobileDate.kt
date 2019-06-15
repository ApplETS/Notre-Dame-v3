package utils.date

import kotlinx.cinterop.memScoped

/**
 * Creates a new [ETSMobileDate] from the specified [timestamp]
 *
 * @param timestamp Unix time (Number of Epoch milliseconds)
 */
actual fun ETSMobileDate(timestamp: Long?): ETSMobileDate = memScoped {
    TODO()
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
): ETSMobileDate {
    TODO()
}

/**
 * Creates a new [ETSMobileDate] which represents the current time
 */
actual fun ETSMobileDate.now(): ETSMobileDate {
    TODO()
}