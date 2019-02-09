package model

/**
 * Created by Sonphil on 30-12-18.
 */
@AndroidParcelize
data class UniversalCode(val value: String) : AndroidParcel {
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