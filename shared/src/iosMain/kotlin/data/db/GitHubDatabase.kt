package data.db

import kotlinx.coroutines.flow.Flow
import model.GitHubContributor

/**
 * Created by Sonphil on 26-09-19.
 */

actual class GitHubDatabase {
    /**
     * Returns the GitHub contributors
     */
    actual fun gitHubContributors(): Flow<List<GitHubContributor>> {
        TODO("not implemented")
    }
}