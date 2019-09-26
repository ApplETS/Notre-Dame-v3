package data.db

import data.db.dao.GitHubContributorDao
import data.db.entity.mapper.toGitHubContributors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import model.GitHubContributor
import javax.inject.Inject

/**
 * Created by Sonphil on 26-09-19.
 */

actual class GitHubDatabase @Inject constructor(
    private val dao: GitHubContributorDao
) {
    /**
     * Returns the GitHub contributors
     */
    actual fun gitHubContributors(): Flow<List<GitHubContributor>> {
        return dao.getAll().map {
            it.toGitHubContributors()
        }
    }
}