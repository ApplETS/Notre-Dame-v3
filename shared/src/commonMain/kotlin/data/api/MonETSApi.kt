package data.api

import data.api.model.MonETSAuthenticationRequestBody
import data.api.model.MonETSUser
import di.Inject
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom

/**
 * Created by Sonphil on 18-05-19.
 */

class MonETSApi @Inject constructor() {
    private val client = AppHttpClient.httpClient

    companion object {
        private const val BASE_URL = "https://portail.etsmtl.ca/api/"
    }

    suspend fun authenticateUser(requestBody: MonETSAuthenticationRequestBody): MonETSUser = client.post {
        url {
            takeFrom(BASE_URL + "authentification")
        }
        contentType(ContentType.Application.Json)
        body = requestBody
    }
}