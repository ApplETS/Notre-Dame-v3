package data.api

import data.api.requestbody.signets.EtudiantRequestBody
import data.api.response.signets.ApiEtudiant
import io.ktor.client.HttpClient
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.http.withCharset
import kotlinx.io.charsets.Charsets

/**
 * Created by Sonphil on 20-02-19.
 */

private const val URL = "https://signets-ens.etsmtl.ca/Secure/WebServices/SignetsMobile.asmx/"

class SignetsApi(private val client: HttpClient) {
    suspend fun getEtudiant(requestBody: EtudiantRequestBody): ApiEtudiant = client.post {
        url {
            takeFrom(URL + "infoEtudiant")
        }
        accept(ContentType.Application.Json.withCharset(Charsets.UTF_8))
        contentType(ContentType.Application.Json)
        body = requestBody
    }
}