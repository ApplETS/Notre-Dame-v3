package data.repository

import data.db.GitHubDatabase
import di.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import model.GitHubContributor
import utils.EtsMobileDispatchers

/**
 * Created by Sonphil on 26-09-19.
 */

class GitHubContributorRepository @Inject constructor(private val database: GitHubDatabase) {
    suspend fun gitHubContributors(): Flow<List<GitHubContributor>> {
        return withContext(EtsMobileDispatchers.IO) {
            database.gitHubContributors()
        }
    }
}