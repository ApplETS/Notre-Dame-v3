package presentation

import di.Inject
import domain.FetchGitHubContributorsUseCase
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.GitHubContributor
import model.Resource

/**
 * Created by Sonphil on 02-10-19.
 */

class GitHubContributorsViewModel @Inject constructor(
    private val fetchGitHubContributorsUseCase: FetchGitHubContributorsUseCase
) : ViewModel() {
    val showLoading: ConflatedBroadcastChannel<Boolean> = ConflatedBroadcastChannel()
    val contributors: ConflatedBroadcastChannel<List<GitHubContributor>?> = ConflatedBroadcastChannel()
    val errorMessage: ConflatedBroadcastChannel<String> = ConflatedBroadcastChannel()

    fun fetchContributors() {
        if (contributors.valueOrNull == null) {
            refreshContributors()
        }
    }

    fun refreshContributors() {
        vmScope.launch {
            fetchGitHubContributorsUseCase()
                .collect { res ->
                    showLoading.offer(res.status == Resource.Status.LOADING)
                    contributors.offer(res.data)
                    res.message?.let { message ->
                        errorMessage.offer(message)
                    }
                }
        }
    }
}