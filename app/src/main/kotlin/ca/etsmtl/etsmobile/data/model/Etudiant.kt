package ca.etsmtl.etsmobile.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
class Etudiant : SignetsData() {

    @Json(name = "__type")
    var type: String? = null
    @Json(name = "nom")
    var nom: String? = null
        get() = field?.trim()
    @Json(name = "prenom")
    var prenom: String? = null
        get() = field?.trim()
    @PrimaryKey
    @Json(name = "codePerm")
    lateinit var codePerm: String
    @Json(name = "soldeTotal")
    var soldeTotal: String? = null
    @Json(name = "masculin")
    var masculin: Boolean? = null
}