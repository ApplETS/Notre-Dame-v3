package ca.etsmtl.applets.etsmobile.extension

import android.content.Context
import android.graphics.Color
import androidx.core.graphics.ColorUtils
import model.Seance

/**
 * Created by Sonphil on 13-08-19.
 */

fun Seance.generateColor(context: Context): Int {
    val secondColor = if (context.isDarkMode) {
        Color.WHITE
    } else {
        Color.BLACK
    }

    return ColorUtils.blendARGB(
        sigleCours.hashCode(),
        secondColor,
        0.3f
    )
}