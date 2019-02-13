package extensions

/**
 * Created by Sonphil on 06-02-19.
 */

expect fun Number.formatFractionDigits(maximumIntegerDigits: Int): String

fun Number.formatSingleFractionDigits() = formatFractionDigits(1)