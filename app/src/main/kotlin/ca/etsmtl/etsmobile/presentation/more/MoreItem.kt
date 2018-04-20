package ca.etsmtl.etsmobile.presentation.more

import android.support.annotation.DrawableRes

/**
 * Created by Sonphil on 19-04-18.
 */

data class MoreItem(
    @DrawableRes val iconId: Int,
    val label: String
)