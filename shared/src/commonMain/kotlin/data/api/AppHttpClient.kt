package data.api

import data.api.model.ApiGitHubContributor
import io.ktor.client.HttpClient
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.list

/**
 * Created by Sonphil on 18-05-19.
 */

internal object AppHttpClient {
    val httpClient by lazy {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer().apply {
                    register<List<ApiGitHubContributor>>(ApiGitHubContributor.serializer().list)
                }
            }

            install(HttpCookies) {
                // Will keep an in-memory map with all the cookies from previous requests.
                storage = AcceptAllCookiesStorage()
            }
        }
    }
}