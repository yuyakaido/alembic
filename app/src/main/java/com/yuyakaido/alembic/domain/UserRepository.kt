package com.yuyakaido.alembic.domain

import com.yuyakaido.alembic.data.GitHubRemoteDataSource

object UserRepository {
    suspend fun searchUsers(): Result<List<User>> = runCatching {
        GitHubRemoteDataSource
            .searchUsers(query = "android")
            .getOrThrow()
            .toUsers()
    }

    suspend fun getMe(): Result<Me> = runCatching {
        GitHubRemoteDataSource
            .getMe()
            .getOrThrow()
            .toMe()
    }
}
