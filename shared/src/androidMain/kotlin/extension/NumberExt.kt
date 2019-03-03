@file:JvmName("NumberExtAndroid")

package extension

import java.text.NumberFormat

/**
 * Created by Sonphil on 06-02-19.
 */

actual fun Number.formatFractionDigits(maximumIntegerDigits: Int): String {
    return NumberFormat.getNumberInstance().apply {
        maximumFractionDigits = 1
    }.format(this)
}