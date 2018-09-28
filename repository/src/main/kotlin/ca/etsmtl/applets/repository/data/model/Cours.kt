package ca.etsmtl.repository.data.model

import android.os.Parcel
import android.os.Parcelable

data class Cours(
    var sigle: String,
    var groupe: String,
    var session: String,
    var programmeEtudes: String,
    var cote: String?,
    /** Grade is null if cote is not null **/
    var noteSur100: String,
    var nbCredits: Int = 0,
    var titreCours: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString(),
            parcel.readString() ?: "",
            parcel.readInt(),
            parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with (parcel) {
            writeString(sigle)
            writeString(groupe)
            writeString(session)
            writeString(programmeEtudes)
            writeString(cote)
            writeString(noteSur100)
            writeInt(nbCredits)
            writeString(titreCours)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cours> {
        override fun createFromParcel(parcel: Parcel): Cours {
            return Cours(parcel)
        }

        override fun newArray(size: Int): Array<Cours?> {
            return arrayOfNulls(size)
        }
    }
}