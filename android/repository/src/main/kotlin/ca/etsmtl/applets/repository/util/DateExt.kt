package ca.etsmtl.applets.repository.util

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Sonphil on 09-01-19.
 */

const val MILLIS_PER_SECOND = 1000

/**
 * Formats Microsoft JSON date (e.g. /Date(1525093200000)/) to Unix time seconds (e.g. 1525093200)
 */
internal fun String.msDateToUnix() = substringAfter('(')
    .substringBefore(')')
    .toLong() / MILLIS_PER_SECOND

/**
 * Convert date [String] to Unix time in seconds
 *
 * @param format The current format of the date (e.g. yyyy-MM-dd)
 */
fun String.dateToUnix(format: String): Long {
    return try {
        SimpleDateFormat(format).parse(this@dateToUnix).timeInSeconds
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

internal fun String.signetsDefaultDateToUnix(): Long = dateToUnix("yyyy-MM-dd")

/**
 * Format unix date in seconds to Signets default date format
 */
internal fun Long.unixToDefaultSignetsDate() = SimpleDateFormat("yyyy-MM-dd")
    .format(Date(this * MILLIS_PER_SECOND))

inline var Date.timeInSeconds: Long
    get() = time / MILLIS_PER_SECOND
    set(value) {
        time = value * MILLIS_PER_SECOND
    }