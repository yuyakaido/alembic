package com.yuyakaido.alembic.domain

import com.yuyakaido.alembic.data.GitHubRemoteDataSource

object RepoRepository {
    suspend fun searchAndroidRepositories(): Result<List<Repo>> = runCatching {
        GitHubRemoteDataSource
            .searchRepositories(query = "android")
            .getOrThrow()
            .toRepos()
    }
}
