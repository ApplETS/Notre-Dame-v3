package data.api.response.signets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiEtudiant(
    @SerialName("__type")
    var type: String,
    @SerialName("nom")
    var untrimmedNom: String,
    @SerialName("prenom")
    var untrimmedPrenom: String,
    var codePerm: String,
    var soldeTotal: String,
    var masculin: Boolean,
    override var erreur: String? = null
) : ApiSignetsData()
