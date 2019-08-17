package utils.date

import kotlinx.cinterop.memScoped

/**
 * Creates a new [ETSMobileDate] from the specified [timestamp]
 *
 * @param timestamp Unix time (Number of Epoch milliseconds) (it is `now` by default)
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