package ca.etsmtl.applets.repository.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Sonphil on 09-01-19.
 */

/**
 * Formats the [String] which must be in the following format: "yyyy-MM-dd"
 *
 * @return The formatted [String] or the [String] (without formatting) if it can't be parsed
 */
fun String.toLocaleDate(): String {
    with (SimpleDateFormat("yyyy-MM-dd")) {
        return try {
            DateFormat.getDateInstance().format(parse(this@toLocaleDate))
        } catch (e: ParseException) {
            e.printStackTrace()
            this@toLocaleDate
        }
    }
}

/**
 * Formats Microsoft JSON date (e.g. /Date(1525093200000)/) to Unix time (e.g. 1525093200000)
 */
internal fun String.msDateToUnix() = substringAfter('(').substringBefore(')').toLong()

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
    .format(Date(this * 1000))

inline var Date.timeInSeconds: Long
    get() = time / 1000
    set(value) {
        time = value * 1000
    }