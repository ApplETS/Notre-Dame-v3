package data.db

import data.api.model.ApiGitHubContributor
import data.db.dao.GitHubContributorDao
import data.db.entity.mapper.toGitHubContributorEntities
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
    actual fun getGitHubContributors(): Flow<List<GitHubContributor>> {
        return dao.getAll().map {
            it.toGitHubContributors()
        }
    }

    /**
     * Update GitHub contributors
     */
    actual fun updateGitHubContributors(gitHubContributors: List<ApiGitHubContributor>) {
        dao.clearAndInsertContributors(gitHubContributors.toGitHubContributorEntities())
    }
}