package com.yuyakaido.alembic.domain

import android.net.Uri
import com.yuyakaido.alembic.data.GitHubLocalDataSource
import com.yuyakaido.alembic.data.GitHubRemoteDataSource

object UserRepository {

    fun getAuthUri(): Uri = GitHubRemoteDataSource.getAuthUri()

    fun isSignedIn(): Boolean = getAccessToken() != null

    fun getAccessToken(): String? = GitHubLocalDataSource.getAccessToken()

    suspend fun generateAccessToken(code: String): Result<Unit> = runCatching {
        val accessToken = GitHubRemoteDataSource.generateAccessToken(code).getOrThrow().accessToken
        GitHubLocalDataSource.updateAccessToken(accessToken)
    }

    suspend fun searchUsers(): Result<List<User>> = runCatching {
        GitHubRemoteDataSource.searchUsers(query = "android").getOrThrow().toUsers()
    }

    suspend fun getMe(): Result<Me> = runCatching {
        GitHubRemoteDataSource.getMe().getOrThrow().toMe()
    }
}
