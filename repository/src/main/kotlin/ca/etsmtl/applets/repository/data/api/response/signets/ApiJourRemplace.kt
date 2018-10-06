package ca.etsmtl.applets.repository.data.api.response.signets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiJourRemplace(
    @Json(name = "dateOrigine") var dateOrigine: String,
    @Json(name = "dateRemplacement") var dateRemplacement: String,
    @Json(name = "description") var untrimmedDescription: String?
) {
    val description: String?
        get() = untrimmedDescription?.trim()
}