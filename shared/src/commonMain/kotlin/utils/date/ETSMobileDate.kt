package utils.date

/**
 * This class represents a specific instant in time
 *
 * Created by Sonphil on 21-05-19.
 */

enum class WeekDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
}

enum class Month {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;
}

/** Number of milliseconds in one second **/
const val MILLIS_PER_SECOND = 1000

data class ETSMobileDate internal constructor(
    val seconds: Int,
    val minutes: Int,
    val hours: Int,
    val dayOfWeek: WeekDay,
    val dayOfMonth: Int,
    val dayOfYear: Int,
    val month: Month,
    val year: Int,
    /** Number of milliseconds since Epoch **/
    val timeInMilliseconds: Long
) : Comparable<ETSMobileDate> {
    /** Number of seconds since Epoch **/
    val timeInSeconds: Long by lazy { timeInMilliseconds / MILLIS_PER_SECOND }

    override fun compareTo(other: ETSMobileDate): Int {
        return timeInMilliseconds.compareTo(other.timeInMilliseconds)
    }
}

/**
 * Creates a new [ETSMobileDate] from the specified [timestamp]
 *
 * @param timestamp Unix time (Number of Epoch milliseconds)
 */
@Suppress("FunctionName")
expect fun ETSMobileDate(timestamp: Long? = null): ETSMobileDate

/**
 * Creates a new [ETSMobileDate] from the provided date/time components
 */
@Suppress("FunctionName")
expect fun ETSMobileDate(
    seconds: Int,
    minutes: Int,
    hours: Int,
    dayOfMonth: Int,
    month: Month,
    year: Int
): ETSMobileDate

/**
 * Creates a new [ETSMobileDate] which represents the current time
 */
expect fun ETSMobileDate.now(): ETSMobileDate

/**
 * Adds the specified number of [milliseconds]
 */
operator fun ETSMobileDate.plus(milliseconds: Long): ETSMobileDate = ETSMobileDate(timeInMilliseconds + milliseconds)

/**
 * Subtracts the specified number of [milliseconds]
 */
operator fun ETSMobileDate.minus(milliseconds: Long): ETSMobileDate = ETSMobileDate(timeInMilliseconds - milliseconds)