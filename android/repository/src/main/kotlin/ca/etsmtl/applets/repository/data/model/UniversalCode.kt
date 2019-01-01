package ca.etsmtl.applets.repository.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sonphil on 30-12-18.
 */
@Parcelize
data class UniversalCode(val value: String) : Parcelable {
    enum class Error {
        EMPTY,
        INVALID
    }

    val error: Error?
        get() = when {
            value.isEmpty() -> Error.EMPTY
            !value.matches(Regex("[a-zA-Z]{2}\\d{5}")) -> Error.INVALID
            else -> null
        }
}