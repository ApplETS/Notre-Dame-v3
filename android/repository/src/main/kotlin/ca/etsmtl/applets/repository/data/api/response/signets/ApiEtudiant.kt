package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiEtudiant(
    @Json(name = "__type")
    var type: String,
    @Json(name = "nom")
    var untrimmedNom: String,
    @Json(name = "prenom")
    var untrimmedPrenom: String,
    @Json(name = "codePerm")
    var codePerm: String,
    @Json(name = "soldeTotal")
    var soldeTotal: String,
    @Json(name = "masculin")
    var masculin: Boolean,
    @Json(name = "erreur")
    override var erreur: String? = null
) : ApiSignetsData()
