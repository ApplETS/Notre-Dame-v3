package ca.etsmtl.applets.repository.util

/**
 * Created by Sonphil on 09-09-18.
 */

/**
 * Replaces this char sequence with `0` if if this nullable char sequence is either `null` or empty
 * or consists solely of whitespace characters.
 */
fun String?.zeroIfNullOrBlank() = this.replaceIfNullOrBlank("0")

/**
 * Replaces this char sequence with [replacement] if if this nullable char sequence is either `null`
 * or empty or consists solely of whitespace characters.
 *
 * @param replacement
 */
fun String?.replaceIfNullOrBlank(replacement: String) = this?.takeIf { it.isNotBlank() } ?: replacement

/**
 * Replaces the first comma of a [String] with a dot and tries to parse it into a [Float]
 */
fun String.replaceCommaAndParseToFloat() = replaceFirst(",", ".").toFloatOrNull()

/**
 * Replaces the first comma of a [String] with a dot and tries to parse it into a [Float]
 */
fun String.replaceCommaAndParseToDouble() = replaceFirst(",", ".").toDoubleOrNull()