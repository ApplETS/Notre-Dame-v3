package ca.etsmtl.applets.etsmobile.presentation.ets

import androidx.annotation.DrawableRes

/**
 * Created by Sonphil on 09-12-18.
 */

data class EtsItem(
    @DrawableRes val iconId: Int,
    val label: String,
    val onItemClickHandler: ItemClickHandler
)