package data.db

import kotlinx.coroutines.flow.Flow
import model.GitHubContributor

/**
 * Created by Sonphil on 26-09-19.
 */

expect class GitHubDatabase {
    /**
     * Returns the GitHub contributors
     */
    fun gitHubContributors(): Flow<List<GitHubContributor>>
}