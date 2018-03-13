package ca.etsmtl.etsmobile.data.model

import com.squareup.moshi.Json

class Etudiant : SignetsData() {

    @Json(name = "__type")
    var type: String? = null
    @Json(name = "nom")
    var nom: String? = null
        get() = field?.trim()
    @Json(name = "prenom")
    var prenom: String? = null
        get() = field?.trim()
    @Json(name = "codePerm")
    var codePerm: String? = null
    @Json(name = "soldeTotal")
    var soldeTotal: String? = null
    @Json(name = "masculin")
    var masculin: Boolean? = null
}