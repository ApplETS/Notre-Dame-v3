package data.repository

import data.api.GitHubApi
import data.db.GitHubDatabase
import di.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
        val flow: Flow<Resource<List<GitHubContributor>>> = flow {
            emit(Resource.loading(null))

            val dbContributors = database
                .getGitHubContributors()
                .first()

            emit(Resource.loading(dbContributors))

            try {
                val apiContributors = api.getContributors()

                // Update contributors in database
                database.updateGitHubContributors(apiContributors)

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

        return flow.flowOn(EtsMobileDispatchers.IO)
    }
}