package com.yuyakaido.alembic.domain

import com.yuyakaido.alembic.data.local.GitHubLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenRepository @Inject constructor(
    private val local: GitHubLocalDataSource,
) {
    suspend fun getAccessToken(): String? = local.getAccessToken()
}
