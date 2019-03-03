package ca.etsmtl.applets.etsmobile.util

import android.content.Context
import ca.etsmtl.applets.etsmobile.R
import model.Cours

/**
 * Created by Sonphil on 02-03-19.
 */

fun Cours.adjustCote(context: Context) {
    cote = when {
        !cote.isNullOrEmpty() -> cote!!
        !noteSur100.isNullOrEmpty() -> {
            String.format(
                context.getString(R.string.text_grade_in_percentage),
                noteSur100
            )
        }
        else -> context.getString(R.string.abbreviation_not_available)
    }
}