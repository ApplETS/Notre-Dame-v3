package ca.etsmtl.applets.repository.data.model

import android.os.Parcelable
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials.Companion.INSTANCE
import kotlinx.android.parcel.Parcelize
import java.util.concurrent.atomic.AtomicReference

/**
 * User's credentials needed to access Signets' webservice
 * An instance of this class is stored in the [INSTANCE] AtomicReference.
 *
 * Created by Sonphil on 28-02-18.
 */
@Parcelize
data class SignetsUserCredentials(
    val codeAccesUniversel: UniversalCode,
    val motPasse: String
) : Parcelable {
    companion object {
        @JvmStatic
        var INSTANCE: AtomicReference<SignetsUserCredentials> = AtomicReference()
    }
}