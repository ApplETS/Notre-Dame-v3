package utils.localizable

/**
 * Created by Sonphil on 29-07-19.
 */

data class LocalizableString(
    private val fr: String,
    private val en: String
) {
    /**
     * Returns the string in english if the device is in english or returns in french otherwise
     */
    fun get(): String = when (Language.iso3Code) {
        "eng" -> en
        else -> fr
    }
}