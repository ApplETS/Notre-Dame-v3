package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiListeHoraireExamensFinaux(
    @Json(name = "__type") var type: String? = "",
    @Json(name = "listeHoraire") var listeHoraire: List<ApiHoraireExamenFinal>? = listOf(),
    @Json(name = "erreur") var erreur: String? = ""
) : ApiSignetsData() {
    override fun getError(): String? {
        return erreur
    }
}