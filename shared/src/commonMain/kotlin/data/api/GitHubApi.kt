package data.api

import data.api.model.ApiGitHubContributor
import di.Inject
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.takeFrom

/**
 * Created by Sonphil on 26-09-19.
 */

class GitHubApi @Inject constructor() {
    private val client = AppHttpClient.httpClient

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    suspend fun contributors(): List<ApiGitHubContributor> = client.get {
        url {
            takeFrom(BASE_URL + "repos/ApplETS/Notre-Dame/contributors")
        }
        parameter("anon", 0)
        accept(ContentType.Application.Json)
    }
}