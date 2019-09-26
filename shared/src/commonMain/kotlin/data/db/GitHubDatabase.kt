package data.db

import data.api.model.ApiGitHubContributor
import kotlinx.coroutines.flow.Flow
import model.GitHubContributor

/**
 * Created by Sonphil on 26-09-19.
 */

expect class GitHubDatabase {
    /**
     * Returns the GitHub getContributors
     */
    fun getGitHubContributors(): Flow<List<GitHubContributor>>

    /**
     * Update the GitHub getContributors
     */
    fun setGitHubContributors(gitHubContributors: List<ApiGitHubContributor>)
}