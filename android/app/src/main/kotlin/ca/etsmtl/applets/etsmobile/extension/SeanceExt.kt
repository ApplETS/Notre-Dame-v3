package ca.etsmtl.applets.etsmobile.extension

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import model.Seance

/**
 * Created by Sonphil on 13-08-19.
 */

fun Seance.generateColor() = ColorUtils.blendARGB(
    sigleCours.hashCode(),
    Color.BLACK,
    0.3f
)