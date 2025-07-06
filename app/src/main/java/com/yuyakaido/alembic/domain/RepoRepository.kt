package com.yuyakaido.alembic.domain

import com.yuyakaido.alembic.data.GitHubRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val remote: GitHubRemoteDataSource
) {
    suspend fun searchAndroidRepositories(): Result<List<Repo>> = runCatching {
        remote.searchRepositories(query = "android").getOrThrow().toRepos()
    }
}
