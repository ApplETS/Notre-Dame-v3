package ca.etsmtl.applets.etsmobile.extension

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Sonphil on 16-01-19.
 */

fun Date.toLocalizedString(
    style: Int = DateFormat.DEFAULT,
    locale: Locale = Locale.CANADA_FRENCH
): String = SimpleDateFormat.getDateInstance(
    style,
    locale
).format(this)