package ca.etsmtl.etsmobile.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class Etudiant(
    @Json(name = "__type")
    var type: String? = null,
    @Json(name = "nom")
    var n: String? = null,
    @Json(name = "prenom")
    var p: String? = null,
    @PrimaryKey
    @Json(name = "codePerm")
    var codePerm: String,
    @Json(name = "soldeTotal")
    var soldeTotal: String? = null,
    @Json(name = "masculin")
    var masculin: Boolean? = null,
    @Json(name = "erreur")
    var erreur: String? = null
) : SignetsData() {
    val nom: String?
                get() = n?.trim()
        val prenom: String?
                get() = p?.trim()

    override fun getError(): String? {
        return erreur
    }
}
