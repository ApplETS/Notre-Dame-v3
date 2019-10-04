package data.db.entity.mapper

import data.api.model.ApiGitHubContributor
import data.db.entity.GitHubContributorEntity
import model.GitHubContributor

/**
 * Created by Sonphil on 26-09-19.
 */

fun GitHubContributorEntity.toGitHubContributor() = GitHubContributor(
    avatarUrl = avatarUrl,
    contributions = contributions,
    eventsUrl = eventsUrl,
    followersUrl = followersUrl,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    gravatarId = gravatarId,
    htmlUrl = htmlUrl,
    id = id,
    login = login,
    nodeId = nodeId,
    organizationsUrl = organizationsUrl,
    receivedEventsUrl = receivedEventsUrl,
    reposUrl = reposUrl,
    siteAdmin = siteAdmin,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    type = type,
    url = url
)

fun List<GitHubContributorEntity>.toGitHubContributors(): List<GitHubContributor> = map {
    it.toGitHubContributor()
}

fun ApiGitHubContributor.toGitHubContributorsEntity() = GitHubContributorEntity(
    avatarUrl = avatarUrl,
    contributions = contributions,
    eventsUrl = eventsUrl,
    followersUrl = followersUrl,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    gravatarId = gravatarId,
    htmlUrl = htmlUrl,
    id = id,
    login = login,
    nodeId = nodeId,
    organizationsUrl = organizationsUrl,
    receivedEventsUrl = receivedEventsUrl,
    reposUrl = reposUrl,
    siteAdmin = siteAdmin,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    type = type,
    url = url
)

fun List<ApiGitHubContributor>.toGitHubContributorEntities(): List<GitHubContributorEntity> = map {
    it.toGitHubContributorsEntity()
}