package ca.etsmtl.repository.data.api.response.signets

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
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
    var erreur: String? = null
) : ApiSignetsData() {
    override fun getError() = erreur
}
