package data.api

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

/**
 * Created by Sonphil on 20-02-19.
 */

object AppHttpClient {
    val httpClient by lazy {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}