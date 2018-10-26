package ca.etsmtl.applets.repository.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cours(
    var sigle: String,
    var groupe: String,
    var session: String,
    var programmeEtudes: String,
    var cote: String?,
    /** Grade is null if cote is not null **/
    var noteSur100: String?,
    var nbCredits: Int = 0,
    var titreCours: String
) : Parcelable {
    /**
     * Returns true if the course has a valid session (i.e. H2018)
     *
     * @return True if the course has a valid session (i.e. H2018)
     */
    fun hasValidSession() = session.matches(Regex("[aheéAHEÉ]\\d{4}"))
}