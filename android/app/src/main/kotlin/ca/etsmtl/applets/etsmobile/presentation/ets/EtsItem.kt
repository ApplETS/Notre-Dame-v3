package ca.etsmtl.applets.etsmobile.presentation.ets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Created by Sonphil on 09-12-18.
 */

data class EtsItem(
    @DrawableRes val iconId: Int,
    @StringRes val label: Int?,
    val onItemClickHandler: ItemClickHandler
)