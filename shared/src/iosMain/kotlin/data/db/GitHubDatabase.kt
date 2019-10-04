package data.db

import data.api.model.ApiGitHubContributor
import kotlinx.coroutines.flow.Flow
import model.GitHubContributor

/**
 * Created by Sonphil on 26-09-19.
 */

actual class GitHubDatabase {
    /**
     * Returns the GitHub contributors
     */
    actual fun getGitHubContributors(): Flow<List<GitHubContributor>> {
        TODO("not implemented")
    }

    /**
     * Update GitHub contributors
     */
    actual fun updateGitHubContributors(gitHubContributors: List<ApiGitHubContributor>) {
        TODO("not implemented")
    }
}