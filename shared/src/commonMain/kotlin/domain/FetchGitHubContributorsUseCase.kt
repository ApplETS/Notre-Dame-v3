package domain

import data.repository.GitHubContributorRepository
import di.Inject
import kotlinx.coroutines.flow.Flow
import model.GitHubContributor
import model.Resource

/**
 * Created by Sonphil on 26-09-19.
 */

class FetchGitHubContributorsUseCase @Inject constructor(private val repository: GitHubContributorRepository) {
    suspend operator fun invoke(): Flow<Resource<List<GitHubContributor>>> = repository.gitHubContributors()
}