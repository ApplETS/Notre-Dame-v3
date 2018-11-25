package ca.etsmtl.applets.etsmobile.presentation.more

import androidx.annotation.DrawableRes

/**
 * Created by Sonphil on 19-04-18.
 */

data class MoreItem(
    @DrawableRes val iconId: Int,
    val label: String
)