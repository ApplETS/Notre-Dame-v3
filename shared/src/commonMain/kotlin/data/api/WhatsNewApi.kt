package data.api

import data.api.model.WhatsNewItems
import di.Inject
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom


/**
 * Created by May on 02-05-20.
 */

class WhatsNewApi @Inject constructor() {
    private val client = AppHttpClient.httpClient

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/whatsNew/"
    }

    suspend fun getWhatsNewEn(versionFrom: String, versionTo : String ): List<WhatsNewItems> = client.get {
        url {
            takeFrom(BASE_URL +"en/"+ versionTo +"?versionFrom="+versionFrom)
        }
        parameter("versionFrom", versionFrom)
        accept(ContentType.Application.Json)
    }

    suspend fun getWhatsNewFr(versionFrom: String, versionTo : String ): List<WhatsNewItems> = client.get {
        url {
            takeFrom(BASE_URL+"fr/" + versionTo)
        }
        parameter("versionFrom", versionFrom)
        accept(ContentType.Application.Json)
    }
}