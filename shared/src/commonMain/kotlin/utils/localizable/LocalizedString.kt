package utils.localizable

/**
 * Created by Sonphil on 29-07-19.
 */

enum class LocalizedString(private val localizableString: LocalizableString) {
    FIELD_REQUIRED(LocalizableString("Le champ est requis", "The field is required")),
    INVALID_UNIVERSAL_CODE(LocalizableString("Le code universel est invalide", "This universal code is invalid")),
    GENERIC_ERROR(LocalizableString("Une erreur est survenue", "An error has occurred"));

    val value: String
        get() = localizableString.get()
}