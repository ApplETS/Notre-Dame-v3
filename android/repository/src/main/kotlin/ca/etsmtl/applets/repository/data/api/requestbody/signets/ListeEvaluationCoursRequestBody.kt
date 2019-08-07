package ca.etsmtl.applets.repository.data.api.requestbody.signets

import com.squareup.moshi.Json

data class ListeEvaluationCoursRequestBody(
    @Json(name = "codeAccesUniversel")
    val codeAccesUniversel: String,
    @Json(name = "motPasse")
    val motPasse: String,
    @Json(name = "pSession")
    val pSession: String
)