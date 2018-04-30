package ca.etsmtl.etsmobile.data.model.signets

import com.squareup.moshi.Json
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by Sonphil on 28-02-18.
 */
data class SignetsUserCredentials(
    @Json(name = "codeAccesUniversel") val codeAccesUniversel: String,
    @Json(name = "motPasse") val motPasse: String
) {
    companion object {
        @JvmStatic
        var INSTANCE: AtomicReference<SignetsUserCredentials> = AtomicReference()
    }
}