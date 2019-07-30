package utils.localizable

import java.util.Locale

/**
 * Created by Sonphil on 29-07-19.
 */

actual object Language {
    actual val iso3Code: String
        get() = Locale.getDefault().isO3Language
}