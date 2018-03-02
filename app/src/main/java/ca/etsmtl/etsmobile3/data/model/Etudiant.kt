package ca.etsmtl.etsmobile3.data.model

import com.squareup.moshi.Json

/**
 * Created by Sonphil on 02-03-18.
 */
data class Etudiant(
        @Json(name = "__type") var type: String? = "",
        @Json(name = "nom") var nom: String? = "",
        @Json(name = "prenom") var prenom: String? = "",
        @Json(name = "codePerm") var codePerm: String? = "",
        @Json(name = "soldeTotal") var soldeTotal: String? = "",
        @Json(name = "masculin") var masculin: Boolean? = false,
        @Json(name = "erreur") var erreur: String? = ""
)