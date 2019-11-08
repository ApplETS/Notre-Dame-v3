package ca.etsmtl.applets.etsmobile.extension

import android.content.Context
import ca.etsmtl.applets.etsmobile.R
import model.Cours

/**
 * Created by Sonphil on 02-03-19.
 */

fun Cours.getDisplayableCote(context: Context): String {
    val notAvailableStr = context.getString(R.string.abbreviation_not_available)

    return when {
        !cote.isNullOrEmpty() && cote != notAvailableStr -> cote!!
        !noteSur100.isNullOrEmpty() -> {
            String.format(
                context.getString(R.string.text_grade_in_percentage),
                noteSur100
            )
        }
        else -> notAvailableStr
    }
}