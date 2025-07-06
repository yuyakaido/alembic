package com.yuyakaido.alembic.data

import android.net.Uri
import androidx.core.net.toUri
import com.yuyakaido.alembic.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRemoteDataSource @Inject constructor(
    private val authApi: GitHubAuthApi,
    private val api: GitHubApi,
) {

    fun getAuthUri(): Uri = "https://github.com/login/oauth/authorize".toUri()
        .buildUpon()
        .appendQueryParameter("client_id", BuildConfig.GITHUB_OAUTH_CLIENT_ID)
        .build()

    suspend fun generateAccessToken(code: String): Result<GenerateAccessTokenResponse> = runCatching {
        authApi.generateAccessToken(
            clientId = BuildConfig.GITHUB_OAUTH_CLIENT_ID,
            clientSecret = BuildConfig.GITHUB_OAUTH_CLIENT_SECRET,
            code = code,
        )
    }

    suspend fun searchRepositories(query: String): Result<SearchRepositoryResponse> = runCatching {
        api.searchRepositories(query)
    }

    suspend fun searchUsers(query: String): Result<SearchUserResponse> = runCatching {
        api.searchUsers(query)
    }

    suspend fun getMe(): Result<UserResponse> = runCatching {
        api.getMe()
    }
}
