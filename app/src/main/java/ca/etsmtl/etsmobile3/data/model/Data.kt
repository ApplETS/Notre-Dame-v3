package ca.etsmtl.etsmobile3.data.model

import com.squareup.moshi.Json


/**
 * Created by Sonphil on 02-03-18.
 */
data class Data<T>(
        @Json(name = "d") var data: T?
)