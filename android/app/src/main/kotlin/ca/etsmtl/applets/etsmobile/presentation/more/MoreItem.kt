package ca.etsmtl.applets.etsmobile.presentation.more

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Created by Sonphil on 19-04-18.
 */

typealias MoreItemClickHandler = (index: Int) -> Unit

data class MoreItem(
    @DrawableRes val iconId: Int,
    @StringRes val label: Int,
    val moreItemClickHandler: MoreItemClickHandler
)