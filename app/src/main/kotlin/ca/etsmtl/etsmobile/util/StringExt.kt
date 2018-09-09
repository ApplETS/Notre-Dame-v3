package ca.etsmtl.etsmobile.util

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