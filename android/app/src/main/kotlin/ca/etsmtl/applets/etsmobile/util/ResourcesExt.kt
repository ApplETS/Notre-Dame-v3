package ca.etsmtl.applets.etsmobile.util

import android.content.res.Resources

/**
 * Created by Sonphil on 17-01-19.*
 */

fun Resources.getAndroidDimensionInPixelSize(name: String): Int? {
    val resourceId = getIdentifier(name, "dimen", "android")

    return if (resourceId > 0) {
        getDimensionPixelSize(resourceId)
    } else {
        null
    }
}