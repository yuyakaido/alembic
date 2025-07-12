package com.yuyakaido.alembic.domain

import android.net.Uri
import com.yuyakaido.alembic.data.local.GitHubLocalDataSource
import com.yuyakaido.alembic.data.GitHubRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remote: GitHubRemoteDataSource,
    private val local: GitHubLocalDataSource,
) {

    fun getAuthUri(): Uri = remote.getAuthUri()

    suspend fun isSignedIn(): Boolean = local.getAccessToken() != null

    suspend fun generateAccessToken(code: String): Result<Unit> = runCatching {
        val accessToken = remote.generateAccessToken(code).getOrThrow().accessToken
        local.updateAccessToken(accessToken)
    }

    suspend fun searchUsers(): Result<List<User>> = runCatching {
        remote.searchUsers(query = "android").getOrThrow().toUsers()
    }

    suspend fun getMe(): Result<Me> = runCatching {
        remote.getMe().getOrThrow().toMe()
    }
}
