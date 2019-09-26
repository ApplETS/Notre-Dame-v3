package data.repository

import data.api.GitHubApi
import data.db.GitHubDatabase
import di.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import model.GitHubContributor
import model.Resource
import utils.EtsMobileDispatchers

/**
 * Created by Sonphil on 26-09-19.
 */

class GitHubContributorRepository @Inject constructor(
    private val database: GitHubDatabase,
    private val api: GitHubApi
) {
    suspend fun gitHubContributors(): Flow<Resource<List<GitHubContributor>>> {
        return flow {
            withContext(EtsMobileDispatchers.IO) {
                val dbContributors = database
                    .getGitHubContributors()
                    .singleOrNull()
                    .orEmpty()

                emit(Resource.loading(dbContributors))

                try {
                    val apiContributors = api.getContributors()

                    // Update contributors in database
                    database.setGitHubContributors(apiContributors)

                    val contributorsResFlow = database
                        .getGitHubContributors()
                        .map { dbContributors ->
                            Resource.success(dbContributors)
                        }

                    emitAll(contributorsResFlow)
                } catch (cause: Throwable) {
                    emit(Resource.error(cause.message ?: cause.toString(), dbContributors))
                }
            }
        }
    }
}